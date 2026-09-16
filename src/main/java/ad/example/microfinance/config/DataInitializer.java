package ad.example.microfinance.config;

import ad.example.microfinance.entity.*;
import ad.example.microfinance.repository.*;
import ad.example.microfinance.security.CryptoUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BorrowerRepository borrowerRepository;
    private final LoanProductRepository productRepository;
    private final JLGRepository jlgRepository;
    private final SHGRepository shgRepository;
    private final LoanApplicationRepository applicationRepository;
    private final LoanRepository loanRepository;
    private final EMIRepository emiRepository;
    private final RepaymentRepository repaymentRepository;
    private final DisbursementRepository disbursementRepository;
    private final BranchRepository branchRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public DataInitializer(UserRepository userRepository,
                           BorrowerRepository borrowerRepository,
                           LoanProductRepository productRepository,
                           JLGRepository jlgRepository,
                           SHGRepository shgRepository,
                           LoanApplicationRepository applicationRepository,
                           LoanRepository loanRepository,
                           EMIRepository emiRepository,
                           RepaymentRepository repaymentRepository,
                           DisbursementRepository disbursementRepository,
                           BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.borrowerRepository = borrowerRepository;
        this.productRepository = productRepository;
        this.jlgRepository = jlgRepository;
        this.shgRepository = shgRepository;
        this.applicationRepository = applicationRepository;
        this.loanRepository = loanRepository;
        this.emiRepository = emiRepository;
        this.repaymentRepository = repaymentRepository;
        this.disbursementRepository = disbursementRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return; // Data already seeded
        }

        // 1. Seed Users (Roles: ADMIN, LOAN_OFFICER, BRANCH_MANAGER, CREDIT_COMMITTEE, COMPLIANCE, BORROWER)
        User admin = new User(null, "admin", passwordEncoder.encode("admin123"), "System Administrator", "admin@mfi.org", "9876543210", "ADMIN", 1L);
        User lo = new User(null, "loan_officer", passwordEncoder.encode("lo123"), "Sunil Verma (Field Officer)", "sunil@mfi.org", "9876543211", "LOAN_OFFICER", 1L);
        User bm = new User(null, "branch_manager", passwordEncoder.encode("bm123"), "Priya Sharma (Branch Manager)", "priya@mfi.org", "9876543212", "BRANCH_MANAGER", 1L);
        User cc = new User(null, "credit_comm", passwordEncoder.encode("cc123"), "Rajesh Mehta (Credit Committee)", "rajesh@mfi.org", "9876543213", "CREDIT_COMMITTEE", 1L);
        User comp = new User(null, "compliance", passwordEncoder.encode("comp123"), "Ananya Sen (Compliance Officer)", "ananya@mfi.org", "9876543214", "COMPLIANCE", 1L);
        User borrowerUser = new User(null, "borrower", passwordEncoder.encode("bor123"), "Ramesh Kumar (Borrower)", "ramesh@gmail.com", "9876543215", "BORROWER", 1L);
        userRepository.saveAll(List.of(admin, lo, bm, cc, comp, borrowerUser));

        // 2. Seed Branches
        Branch b1 = new Branch(null, "BR-MAH-01", "Nashik Rural Branch", "Nashik", "Maharashtra", "422003", 3L, "Priya Sharma", true, LocalDateTime.now());
        Branch b2 = new Branch(null, "BR-MAH-02", "Pune Semi-Urban Branch", "Pune", "Maharashtra", "411001", 3L, "Amit Joshi", true, LocalDateTime.now());
        branchRepository.saveAll(List.of(b1, b2));

        // 3. Seed Loan Products
        LoanProduct p1 = new LoanProduct(null, "JLG-AGRI-01", "Kisan Pragati JLG Loan", "Group microloan for crop cultivation and seasonal agricultural inputs", 5000.0, 50000.0, 18.0, "REDUCING", "6,12,18", "MONTHLY", 1.5, 1.0, 1, 620, true);
        LoanProduct p2 = new LoanProduct(null, "SHG-LIV-02", "Mahila Shakti SHG Livelihood Loan", "Term loan for SHG women micro-enterprises, dairy, and poultry", 10000.0, 100000.0, 16.5, "REDUCING", "12,18,24", "MONTHLY", 1.0, 0.8, 2, 600, true);
        LoanProduct p3 = new LoanProduct(null, "INDIV-ENT-03", "Gramin Vyapar Individual Loan", "Higher ticket individual microfinance loan for rural traders and artisans", 20000.0, 200000.0, 21.0, "REDUCING", "12,24,36", "MONTHLY", 2.0, 1.2, 3, 680, true);
        productRepository.saveAll(List.of(p1, p2, p3));

        // 4. Seed Borrowers
        Borrower bRow1 = new Borrower(null, "BRN-2026-1001", CryptoUtil.hashAadhaar("987654321001"), "ABCDE1234F", "Ramesh Kumar", "9876543215", LocalDate.of(1985, 4, 12), "MALE", "Dindori", "Nashik", "Maharashtra", "422202", 18500.0, "AGRICULTURE", 742, LocalDate.now().minusDays(10), "VERIFIED", null, 45000.0, "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150", LocalDateTime.now(), LocalDateTime.now());
        Borrower bRow2 = new Borrower(null, "BRN-2026-1002", CryptoUtil.hashAadhaar("987654321002"), "BKLPY5678G", "Lakshmi Devi", "9876543216", LocalDate.of(1990, 8, 22), "FEMALE", "Niphad", "Nashik", "Maharashtra", "422303", 22000.0, "DAIRY", 715, LocalDate.now().minusDays(15), "VERIFIED", null, 30000.0, "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150", LocalDateTime.now(), LocalDateTime.now());
        Borrower bRow3 = new Borrower(null, "BRN-2026-1003", CryptoUtil.hashAadhaar("987654321003"), "CPKTR9012H", "Suresh Patil", "9876543217", LocalDate.of(1988, 11, 5), "MALE", "Sinnar", "Nashik", "Maharashtra", "422103", 16000.0, "SMALL_BUSINESS", 688, LocalDate.now().minusDays(20), "VERIFIED", null, 25000.0, "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150", LocalDateTime.now(), LocalDateTime.now());
        Borrower bRow4 = new Borrower(null, "BRN-2026-1004", CryptoUtil.hashAadhaar("987654321004"), "DXMNQ3456J", "Meena Bai", "9876543218", LocalDate.of(1992, 2, 18), "FEMALE", "Yeola", "Nashik", "Maharashtra", "423401", 14500.0, "HANDICRAFTS", 660, LocalDate.now().minusDays(5), "PENDING", null, 15000.0, "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150", LocalDateTime.now(), LocalDateTime.now());
        Borrower bRow5 = new Borrower(null, "BRN-2026-1005", CryptoUtil.hashAadhaar("987654321005"), "EZPRW7890K", "Santosh Shinde", "9876543219", LocalDate.of(1983, 7, 30), "MALE", "Trimbak", "Nashik", "Maharashtra", "422212", 28000.0, "AGRICULTURE", 695, LocalDate.now().minusDays(25), "VERIFIED", null, 50000.0, "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=150", LocalDateTime.now(), LocalDateTime.now());
        borrowerRepository.saveAll(List.of(bRow1, bRow2, bRow3, bRow4, bRow5));

        // 5. Seed JLG Group
        JLG jlg1 = new JLG(null, "JLG", "Godavari Krishi JLG 1", bRow1.getId(), "Ramesh Kumar", 1L, lo.getId(), "Sunil Verma", "Dindori", "Nashik", LocalDate.now().minusMonths(6), LocalDate.now().minusMonths(6), "PASSED", 92.5, "WED", "WEEKLY", 5, true);
        JLG jlg2 = new JLG(null, "JLG", "Sahyadri Mahila JLG 2", bRow2.getId(), "Lakshmi Devi", 1L, lo.getId(), "Sunil Verma", "Niphad", "Nashik", LocalDate.now().minusMonths(3), LocalDate.now().minusMonths(3), "PASSED", 88.0, "THU", "FORTNIGHTLY", 5, true);
        jlgRepository.saveAll(List.of(jlg1, jlg2));

        // 6. Seed SHG Group
        SHG shg1 = new SHG(null, "Pragati Mahila Bachat Gat", "Lakshmi Devi", "Niphad", "Nashik", LocalDate.of(2024, 1, 15), "501002348911", "HDFC0000214", 12, 500.0, 72000.0, 25000.0, "GRADE_A", 432000.0, true);
        shgRepository.save(shg1);

        // 7. Seed Loan Application & Approved Active Loan
        LoanApplication app1 = new LoanApplication(null, "APP-2026-101", bRow1.getId(), "Ramesh Kumar", jlg1.getId(), p1.getId(), "Kisan Pragati JLG Loan", lo.getId(), "Sunil Verma", 40000.0, 12, "Purchase of seeds, drip irrigation pipes and organic fertilizers", "DISBURSED", "Approved based on strong group cohesion and good CIBIL score", null, null, true, LocalDateTime.now().minusMonths(3), LocalDateTime.now().minusMonths(3), null);
        LoanApplication app2 = new LoanApplication(null, "APP-2026-102", bRow2.getId(), "Lakshmi Devi", jlg2.getId(), p2.getId(), "Mahila Shakti SHG Livelihood Loan", lo.getId(), "Sunil Verma", 30000.0, 12, "Purchase of high-yield milch cow and cattle feed", "DISBURSED", "Approved for SHG enterprise", null, null, true, LocalDateTime.now().minusMonths(2), LocalDateTime.now().minusMonths(2), null);
        LoanApplication app3 = new LoanApplication(null, "APP-2026-103", bRow3.getId(), "Suresh Patil", jlg1.getId(), p1.getId(), "Kisan Pragati JLG Loan", lo.getId(), "Sunil Verma", 50000.0, 12, "Grocery shop stock inventory expansion", "APPROVED", "Approved for shop inventory", null, null, true, LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(1), null);
        LoanApplication app4 = new LoanApplication(null, "APP-2026-104", bRow4.getId(), "Meena Bai", null, p3.getId(), "Gramin Vyapar Individual Loan", lo.getId(), "Sunil Verma", 75000.0, 18, "Handloom weaving machine upgrade", "UNDER_REVIEW", "Recommended by BM. Awaiting Credit Committee sign-off (> ₹50,000)", null, null, false, LocalDateTime.now().minusDays(2), null, null);
        applicationRepository.saveAll(List.of(app1, app2, app3, app4));

        // 8. Seed Disbursed Loans
        Loan loan1 = new Loan(null, app1.getId(), "LN-2026-0001", bRow1.getId(), "Ramesh Kumar", p1.getId(), "Kisan Pragati JLG Loan", 40000.0, 30800.0, 18.0, 12, LocalDate.now().minusMonths(3), LocalDate.now().plusMonths(9), "ACTIVE", 0, "STANDARD", 10200.0, "Crop Inputs");
        Loan loan2 = new Loan(null, app2.getId(), "LN-2026-0002", bRow2.getId(), "Lakshmi Devi", p2.getId(), "Mahila Shakti SHG Livelihood Loan", 30000.0, 25400.0, 16.5, 12, LocalDate.now().minusMonths(2), LocalDate.now().plusMonths(10), "ACTIVE", 0, "STANDARD", 5200.0, "Milch Cow");
        loanRepository.saveAll(List.of(loan1, loan2));

        // 9. Seed EMI Schedules for loan1
        for (int i = 1; i <= 12; i++) {
            EMI emi = new EMI();
            emi.setLoanId(loan1.getId());
            emi.setInstallmentNumber(i);
            emi.setDueDate(LocalDate.now().minusMonths(3).plusDays(30 * i));
            emi.setPrincipalDue(3100.0);
            emi.setInterestDue(300.0);
            emi.setTotalDue(3400.0);
            if (i <= 3) {
                emi.setPrincipalPaid(3100.0);
                emi.setInterestPaid(300.0);
                emi.setStatus("PAID");
                emi.setPaymentDate(LocalDate.now().minusMonths(3).plusDays(30 * i));
            } else {
                emi.setPrincipalPaid(0.0);
                emi.setInterestPaid(0.0);
                emi.setStatus("PENDING");
            }
            emiRepository.save(emi);
        }

        // 10. Seed Repayment Transactions
        Repayment r1 = new Repayment(null, loan1.getId(), 1L, bRow1.getId(), "Ramesh Kumar", 1, 3400.0, "CASH", "RCP-2026-0001", lo.getId(), "Sunil Verma", LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(2), null, "PAID", 19.9975, 73.7898, false, null, LocalDateTime.now().minusMonths(2));
        Repayment r2 = new Repayment(null, loan1.getId(), 2L, bRow1.getId(), "Ramesh Kumar", 2, 3400.0, "UPI", "RCP-2026-0002", lo.getId(), "Sunil Verma", LocalDate.now().minusMonths(1), LocalDate.now().minusMonths(1), null, "PAID", 19.9975, 73.7898, false, null, LocalDateTime.now().minusMonths(1));
        Repayment r3 = new Repayment(null, loan1.getId(), 3L, bRow1.getId(), "Ramesh Kumar", 3, 3400.0, "CASH", "RCP-2026-0003", lo.getId(), "Sunil Verma", LocalDate.now().minusDays(5), LocalDate.now().minusDays(5), null, "PAID", 19.9975, 73.7898, false, null, LocalDateTime.now().minusDays(5));
        repaymentRepository.saveAll(List.of(r1, r2, r3));

        // 11. Seed Disbursements
        Disbursement d1 = new Disbursement(null, loan1.getId(), bRow1.getId(), "Ramesh Kumar", "NEFT", "987654321098", "SBIN0001234", "UTR-2026-781923", 40000.0, 600.0, 472.0, 38928.0, LocalDate.now().minusMonths(3), "SUCCESS");
        Disbursement d2 = new Disbursement(null, loan2.getId(), bRow2.getId(), "Lakshmi Devi", "NEFT", "501002348911", "HDFC0000214", "UTR-2026-902341", 30000.0, 300.0, 283.2, 29416.8, LocalDate.now().minusMonths(2), "SUCCESS");
        disbursementRepository.saveAll(List.of(d1, d2));
    }
}
