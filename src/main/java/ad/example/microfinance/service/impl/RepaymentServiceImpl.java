package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.EMI;
import ad.example.microfinance.entity.Loan;
import ad.example.microfinance.entity.Repayment;
import ad.example.microfinance.exception.OfflineSyncConflictException;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.EMIRepository;
import ad.example.microfinance.repository.LoanRepository;
import ad.example.microfinance.repository.RepaymentRepository;
import ad.example.microfinance.service.RepaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final EMIRepository emiRepository;
    private final LoanRepository loanRepository;

    public RepaymentServiceImpl(RepaymentRepository repaymentRepository,
                                EMIRepository emiRepository,
                                LoanRepository loanRepository) {
        this.repaymentRepository = repaymentRepository;
        this.emiRepository = emiRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    @Transactional
    public Repayment recordRepayment(Repayment repayment) {
        Loan loan = loanRepository.findById(repayment.getLoanId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + repayment.getLoanId()));

        if (repayment.getReceiptNumber() == null || repayment.getReceiptNumber().isBlank()) {
            repayment.setReceiptNumber("RCP-2026-" + String.format("%05d", new Random().nextInt(90000) + 10000));
        }

        // Allocate payment: interest first, then principal per FR5
        double paymentAmount = repayment.getAmount();
        List<EMI> emis = emiRepository.findByLoanIdOrderByInstallmentNumberAsc(loan.getId());

        double remainingToAllocate = paymentAmount;
        for (EMI emi : emis) {
            if ("PAID".equalsIgnoreCase(emi.getStatus())) continue;

            double interestRemaining = emi.getInterestDue() - (emi.getInterestPaid() != null ? emi.getInterestPaid() : 0.0);
            double principalRemaining = emi.getPrincipalDue() - (emi.getPrincipalPaid() != null ? emi.getPrincipalPaid() : 0.0);

            if (remainingToAllocate <= 0) break;

            // Interest allocation first
            double interestAllocated = Math.min(remainingToAllocate, interestRemaining);
            emi.setInterestPaid((emi.getInterestPaid() != null ? emi.getInterestPaid() : 0.0) + interestAllocated);
            remainingToAllocate -= interestAllocated;

            // Principal allocation next
            if (remainingToAllocate > 0) {
                double principalAllocated = Math.min(remainingToAllocate, principalRemaining);
                emi.setPrincipalPaid((emi.getPrincipalPaid() != null ? emi.getPrincipalPaid() : 0.0) + principalAllocated);
                remainingToAllocate -= principalAllocated;
            }

            // Determine EMI status
            double totalPaid = emi.getInterestPaid() + emi.getPrincipalPaid();
            if (totalPaid >= emi.getTotalDue()) {
                emi.setStatus("PAID");
                emi.setPaymentDate(repayment.getCollectionDate() != null ? repayment.getCollectionDate() : LocalDate.now());
            } else {
                emi.setStatus("PARTIAL");
            }
            emiRepository.save(emi);

            repayment.setScheduleId(emi.getId());
            repayment.setInstallmentNo(emi.getInstallmentNumber());
        }

        // Update Loan balance
        double currentOutstanding = loan.getOutstandingPrincipal() != null ? loan.getOutstandingPrincipal() : 0.0;
        double newOutstanding = Math.max(0.0, currentOutstanding - paymentAmount);
        loan.setOutstandingPrincipal(newOutstanding);
        loan.setTotalRepaid((loan.getTotalRepaid() != null ? loan.getTotalRepaid() : 0.0) + paymentAmount);

        if (newOutstanding <= 0.0) {
            loan.setStatus("CLOSED");
        }
        loanRepository.save(loan);

        repayment.setBorrowerId(loan.getBorrowerId());
        repayment.setBorrowerName(loan.getBorrowerName());
        return repaymentRepository.save(repayment);
    }

    @Override
    public List<Repayment> findAll() {
        return repaymentRepository.findAll();
    }

    @Override
    public List<Repayment> findByLoanId(Long loanId) {
        return repaymentRepository.findByLoanId(loanId);
    }

    @Override
    public List<EMI> getDailyCollectionSheet(LocalDate date) {
        LocalDate queryDate = date != null ? date : LocalDate.now();
        return emiRepository.findByDueDate(queryDate);
    }

    @Override
    @Transactional
    public Map<String, Object> syncOfflineRepayments(List<Repayment> offlineList) {
        int successfulCount = 0;
        List<String> processedReceipts = new ArrayList<>();

        for (Repayment item : offlineList) {
            if (item.getReceiptNumber() != null) {
                Optional<Repayment> conflict = repaymentRepository.findByReceiptNumber(item.getReceiptNumber());
                if (conflict.isPresent()) {
                    throw new OfflineSyncConflictException("Receipt number '" + item.getReceiptNumber() + "' already exists in central database.");
                }
            }
            item.setIsOfflineSync(true);
            item.setSyncedAt(LocalDateTime.now());
            recordRepayment(item);
            processedReceipts.add(item.getReceiptNumber());
            successfulCount++;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("syncedCount", successfulCount);
        result.put("receipts", processedReceipts);
        result.put("syncedAt", LocalDateTime.now());
        return result;
    }
}
