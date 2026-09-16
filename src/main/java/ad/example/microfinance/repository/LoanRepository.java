package ad.example.microfinance.repository;

import ad.example.microfinance.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByLoanNumber(String loanNumber);
    Optional<Loan> findByApplicationId(Long applicationId);
    List<Loan> findByBorrowerId(Long borrowerId);
    List<Loan> findByStatus(String status);
}