package ad.example.microfinance.repository;

import ad.example.microfinance.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Optional<Borrower> findByBrn(String brn);
    Optional<Borrower> findByMobile(String mobile);
    Optional<Borrower> findByAadhaarHash(String aadhaarHash);
    Optional<Borrower> findByPanNumber(String panNumber);
}
