package ad.example.microfinance.service;

import ad.example.microfinance.entity.EMI;
import ad.example.microfinance.entity.Repayment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RepaymentService {
    Repayment recordRepayment(Repayment repayment);
    List<Repayment> findAll();
    List<Repayment> findByLoanId(Long loanId);
    List<EMI> getDailyCollectionSheet(LocalDate date);
    Map<String, Object> syncOfflineRepayments(List<Repayment> offlineList);
}
