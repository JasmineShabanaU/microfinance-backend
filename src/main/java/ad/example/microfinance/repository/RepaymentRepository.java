package ad.example.microfinance.repository;

import ad.example.microfinance.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    Optional<Repayment> findByReceiptNumber(String receiptNumber);
    List<Repayment> findByLoanId(Long loanId);
    List<Repayment> findByCollectionDate(LocalDate date);
    List<Repayment> findByCollectedBy(Long userId);
}
