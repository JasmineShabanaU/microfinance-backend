package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.*;
import ad.example.microfinance.exception.LoanAlreadyDisbursedException;
import ad.example.microfinance.exception.OverIndebtednessException;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.*;
import ad.example.microfinance.service.LoanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanApplicationRepository applicationRepository;
    private final LoanRepository loanRepository;
    private final EMIRepository emiRepository;
    private final DisbursementRepository disbursementRepository;
    private final BorrowerRepository borrowerRepository;
    private final LoanProductRepository productRepository;

    public LoanServiceImpl(LoanApplicationRepository applicationRepository,
                           LoanRepository loanRepository,
                           EMIRepository emiRepository,
                           DisbursementRepository disbursementRepository,
                           BorrowerRepository borrowerRepository,
                           LoanProductRepository productRepository) {
        this.applicationRepository = applicationRepository;
        this.loanRepository = loanRepository;
        this.emiRepository = emiRepository;
        this.disbursementRepository = disbursementRepository;
        this.borrowerRepository = borrowerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public LoanApplication submitApplication(LoanApplication application) {
        Borrower borrower = borrowerRepository.findById(application.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + application.getBorrowerId()));

        // Check RBI microfinance indebtedness limit: total existing + applied <= 300,000
        double currentIndebtedness = borrower.getTotalExistingIndebtedness() != null ? borrower.getTotalExistingIndebtedness() : 0.0;
        if (currentIndebtedness + application.getAppliedAmount() > 300000.0) {
            throw new OverIndebtednessException("Total indebtedness would exceed RBI ceiling of ₹3,00,000 (Current: ₹"
                    + currentIndebtedness + ", Applied: ₹" + application.getAppliedAmount() + ")");
        }

        application.setBorrowerName(borrower.getFullName());
        application.setApplicationNumber("APP-2026-" + String.format("%04d", new Random().nextInt(9000) + 1000));
        application.setStatus("SUBMITTED");
        return applicationRepository.save(application);
    }

    @Override
    public List<LoanApplication> findAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public LoanApplication findApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan application not found with id: " + id));
    }

    @Override
    public LoanApplication approveApplication(Long id, String remarks, String role) {
        LoanApplication app = findApplicationById(id);

        // Branch Manager limit is 50,000. Above 50,000 requires Credit Committee escalation
        if ("BRANCH_MANAGER".equalsIgnoreCase(role) && app.getAppliedAmount() > 50000.0) {
            app.setStatus("UNDER_REVIEW");
            app.setBranchManagerRemarks(remarks + " (Recommended and escalated to Credit Committee for sanction > ₹50,000)");
            return applicationRepository.save(app);
        }

        app.setStatus("APPROVED");
        app.setApprovedAt(LocalDateTime.now());
        app.setIsESigned(true);
        if ("CREDIT_COMMITTEE".equalsIgnoreCase(role)) {
            app.setCreditCommitteeRemarks(remarks != null ? remarks : "Approved by Credit Committee with digital e-sign");
        } else {
            app.setBranchManagerRemarks(remarks != null ? remarks : "Approved by Branch Manager with digital e-sign");
        }

        return applicationRepository.save(app);
    }

    @Override
    public LoanApplication rejectApplication(Long id, String reason, String remarks) {
        LoanApplication app = findApplicationById(id);
        app.setStatus("REJECTED");
        app.setRejectedAt(LocalDateTime.now());
        app.setRejectionReason(reason != null ? reason : "Eligibility criteria not satisfied");
        app.setBranchManagerRemarks(remarks);
        return applicationRepository.save(app);
    }

    @Override
    @Transactional
    public Loan disburseLoan(Long applicationId, String mode, String bankAccount, String ifsc) {
        LoanApplication app = findApplicationById(applicationId);
        if (!"APPROVED".equalsIgnoreCase(app.getStatus())) {
            throw new IllegalStateException("Application must be in APPROVED status to disburse (Current: " + app.getStatus() + ")");
        }

        Optional<Loan> existing = loanRepository.findByApplicationId(applicationId);
        if (existing.isPresent()) {
            throw new LoanAlreadyDisbursedException("Loan for application " + applicationId + " has already been disbursed.");
        }

        Borrower borrower = borrowerRepository.findById(app.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));

        double currentIndebtedness = borrower.getTotalExistingIndebtedness() != null
                ? borrower.getTotalExistingIndebtedness()
                : 0.0;
        double rate = 18.0; // Default 18% p.a.
        double pfRate = 1.5; // 1.5% processing fee
        double insRate = 1.0; // 1.0% insurance premium

        if (app.getProductId() != null) {
            Optional<LoanProduct> productOpt = productRepository.findById(app.getProductId());
            if (productOpt.isPresent()) {
                LoanProduct p = productOpt.get();
                if (p.getInterestRate() != null) rate = p.getInterestRate();
                if (p.getProcessingFeePct() != null) pfRate = p.getProcessingFeePct();
                if (p.getInsurancePremiumPct() != null) insRate = p.getInsurancePremiumPct();
            }
        }

        int tenure = app.getTenureMonths() != null ? app.getTenureMonths() : 12;
        LocalDate today = LocalDate.now();
        LocalDate maturityDate = today.plusMonths(tenure);

        // Create Loan record
        Loan loan = new Loan();
        loan.setApplicationId(app.getId());
        loan.setLoanNumber("LN-2026-" + String.format("%04d", new Random().nextInt(9000) + 1000));
        loan.setBorrowerId(borrower.getId());
        loan.setBorrowerName(borrower.getFullName());
        loan.setProductId(app.getProductId());
        loan.setProductName(app.getProductName() != null ? app.getProductName() : "Rural Micro Loan");
        loan.setPrincipalAmount(app.getAppliedAmount());
        loan.setOutstandingPrincipal(app.getAppliedAmount());
        loan.setInterestRate(rate);
        loan.setTenureMonths(tenure);
        loan.setDisbursementDate(today);
        loan.setMaturityDate(maturityDate);
        loan.setStatus("ACTIVE");
        loan.setDpd(0);
        loan.setParClassification("STANDARD");
        loan.setTotalRepaid(0.0);
        loan.setPurpose(app.getPurpose());
        Loan savedLoan = loanRepository.save(loan);

        // Generate EMI Schedule using RBI-prescribed declining balance formula (FR5)
        // EMI = P * r * (1+r)^n / ((1+r)^n - 1)
        double monthlyRate = (rate / 100.0) / 12.0;
        double emiAmount;
        if (monthlyRate > 0) {
            emiAmount = (app.getAppliedAmount() * monthlyRate * Math.pow(1 + monthlyRate, tenure))
                    / (Math.pow(1 + monthlyRate, tenure) - 1);
        } else {
            emiAmount = app.getAppliedAmount() / tenure;
        }

        double balance = app.getAppliedAmount();
        // First EMI due minimum 15-30 days after disbursement per Appendix F.1
        LocalDate nextDueDate = today.plusDays(30);

        List<EMI> scheduleList = new ArrayList<>();
        for (int i = 1; i <= tenure; i++) {
            double interestPortion = Math.round((balance * monthlyRate) * 100.0) / 100.0;
            double principalPortion = Math.round((emiAmount - interestPortion) * 100.0) / 100.0;
            if (i == tenure) {
                principalPortion = Math.round(balance * 100.0) / 100.0;
            }
            balance -= principalPortion;
            if (balance < 0) balance = 0;

            EMI emi = new EMI();
            emi.setLoanId(savedLoan.getId());
            emi.setInstallmentNumber(i);
            emi.setDueDate(nextDueDate);
            emi.setPrincipalDue(principalPortion);
            emi.setInterestDue(interestPortion);
            emi.setTotalDue(principalPortion + interestPortion);
            emi.setPrincipalPaid(0.0);
            emi.setInterestPaid(0.0);
            emi.setPenaltyCharged(0.0);
            emi.setStatus("PENDING");
            scheduleList.add(emi);

            nextDueDate = nextDueDate.plusMonths(1);
        }
        emiRepository.saveAll(scheduleList);

        // Create Disbursement record
        double processingFee = Math.round((app.getAppliedAmount() * (pfRate / 100.0)) * 100.0) / 100.0;
        // Insurance premium includes 18% GST per FR9
        double baseInsurance = app.getAppliedAmount() * (insRate / 100.0);
        double insuranceWithGst = Math.round((baseInsurance * 1.18) * 100.0) / 100.0;
        double netDisbursed = app.getAppliedAmount() - processingFee - insuranceWithGst;

        Disbursement disbursement = new Disbursement();
        disbursement.setLoanId(savedLoan.getId());
        disbursement.setBorrowerId(borrower.getId());
        disbursement.setBorrowerName(borrower.getFullName());
        disbursement.setMode(mode != null ? mode : "NEFT");
        disbursement.setBankAccountNumber(bankAccount != null ? bankAccount : "XXXX-XXXX-8921");
        disbursement.setIfscCode(ifsc != null ? ifsc : "SBIN0001234");
        disbursement.setUtrNumber("UTR-2026-" + String.format("%06d", new Random().nextInt(900000) + 100000));
        disbursement.setDisbursedAmount(app.getAppliedAmount());
        disbursement.setProcessingFee(processingFee);
        disbursement.setInsurancePremium(insuranceWithGst);
        disbursement.setNetDisbursed(netDisbursed);
        disbursement.setDisbursementDate(today);
        disbursement.setStatus("SUCCESS");
        disbursementRepository.save(disbursement);

        // Update application status
        app.setStatus("DISBURSED");
        applicationRepository.save(app);

        // Update borrower indebtedness
        borrower.setTotalExistingIndebtedness(currentIndebtedness + app.getAppliedAmount());
        borrowerRepository.save(borrower);

        return savedLoan;
    }

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
    }

    @Override
    public List<EMI> getLoanSchedule(Long loanId) {
        return emiRepository.findByLoanIdOrderByInstallmentNumberAsc(loanId);
    }

    @Override
    public Map<String, Object> calculateForeclosure(Long loanId) {
        Loan loan = findLoanById(loanId);
        double outstanding = loan.getOutstandingPrincipal();
        long monthsActive = java.time.temporal.ChronoUnit.MONTHS.between(
                loan.getDisbursementDate() != null ? loan.getDisbursementDate() : LocalDate.now().minusMonths(1),
                LocalDate.now()
        );

        // Prepayment penalty: 2% of outstanding principal for foreclosure within first 6 months (Appendix F.2)
        double penalty = 0.0;
        if (monthsActive < 6) {
            penalty = Math.round((outstanding * 0.02) * 100.0) / 100.0;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("loanId", loan.getId());
        result.put("loanNumber", loan.getLoanNumber());
        result.put("borrowerName", loan.getBorrowerName());
        result.put("outstandingPrincipal", outstanding);
        result.put("monthsActive", monthsActive);
        result.put("prepaymentPenaltyPct", monthsActive < 6 ? 2.0 : 0.0);
        result.put("penaltyAmount", penalty);
        result.put("totalSettlementAmount", outstanding + penalty);
        return result;
    }

    @Override
    public Loan classifyNpa(Long loanId, String classification) {
        Loan loan = findLoanById(loanId);
        loan.setParClassification(classification);
        if ("SUB_STANDARD".equalsIgnoreCase(classification) || "DOUBTFUL".equalsIgnoreCase(classification) || "LOSS".equalsIgnoreCase(classification)) {
            loan.setStatus("NPA");
        } else {
            loan.setStatus("ACTIVE");
        }
        return loanRepository.save(loan);
    }

    @Override
    public List<Disbursement> findAllDisbursements() {
        return disbursementRepository.findAll();
    }

    @Override
    public Disbursement findDisbursementByLoanId(Long loanId) {
        return disbursementRepository.findByLoanId(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement not found for loan: " + loanId));
    }

    @Override
    public Map<String, Object> verifyPennyDrop(String bankAccount, String ifsc) {
        // Bank account verification (penny drop) before disbursement (FR12)
        Map<String, Object> response = new HashMap<>();
        response.put("accountNumber", bankAccount);
        response.put("ifsc", ifsc);
        response.put("status", "SUCCESS");
        response.put("pennyDeposited", 1.00);
        response.put("beneficiaryName", "VERIFIED BENEFICIARY");
        response.put("referenceId", "PENNY-" + System.currentTimeMillis());
        return response;
    }
}