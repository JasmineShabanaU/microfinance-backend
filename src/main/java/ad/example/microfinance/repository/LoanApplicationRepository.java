package ad.example.microfinance.repository;

import ad.example.microfinance.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByStatus(String status);
    List<LoanApplication> findByBorrowerId(Long borrowerId);
}
