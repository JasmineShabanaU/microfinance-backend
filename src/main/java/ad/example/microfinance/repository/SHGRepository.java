package ad.example.microfinance.repository;

import ad.example.microfinance.entity.SHG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SHGRepository extends JpaRepository<SHG, Long> {
}
