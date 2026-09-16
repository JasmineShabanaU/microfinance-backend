package ad.example.microfinance.repository;

import ad.example.microfinance.entity.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim, Long> {
    List<InsuranceClaim> findByBorrowerId(Long borrowerId);
    List<InsuranceClaim> findByLoanId(Long loanId);
}
