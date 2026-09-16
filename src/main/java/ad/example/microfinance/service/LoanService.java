package ad.example.microfinance.service;

import ad.example.microfinance.entity.*;

import java.util.List;
import java.util.Map;

public interface LoanService {
    // Applications
    LoanApplication submitApplication(LoanApplication application);
    List<LoanApplication> findAllApplications();
    LoanApplication findApplicationById(Long id);
    LoanApplication approveApplication(Long id, String remarks, String role);
    LoanApplication rejectApplication(Long id, String reason, String remarks);

    // Loans
    Loan disburseLoan(Long applicationId, String mode, String bankAccount, String ifsc);
    List<Loan> findAllLoans();
    Loan findLoanById(Long id);
    List<EMI> getLoanSchedule(Long loanId);
    Map<String, Object> calculateForeclosure(Long loanId);
    Loan classifyNpa(Long loanId, String classification);

    // Disbursements & Claims
    List<Disbursement> findAllDisbursements();
    Disbursement findDisbursementByLoanId(Long loanId);
    Map<String, Object> verifyPennyDrop(String bankAccount, String ifsc);
}