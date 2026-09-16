package ad.example.microfinance.repository;

import ad.example.microfinance.entity.EMI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EMIRepository extends JpaRepository<EMI, Long> {
    List<EMI> findByLoanIdOrderByInstallmentNumberAsc(Long loanId);
    List<EMI> findByDueDate(LocalDate dueDate);
    List<EMI> findByDueDateLessThanEqualAndStatus(LocalDate date, String status);
}
