package ad.example.microfinance.repository;

import ad.example.microfinance.entity.SHGSavings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SHGSavingsRepository extends JpaRepository<SHGSavings, Long> {
    List<SHGSavings> findByGroupId(Long groupId);
    List<SHGSavings> findByBorrowerId(Long borrowerId);
}
