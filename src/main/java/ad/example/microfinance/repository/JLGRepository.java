package ad.example.microfinance.repository;

import ad.example.microfinance.entity.JLG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JLGRepository extends JpaRepository<JLG, Long> {
    List<JLG> findByGroupType(String groupType);
    List<JLG> findByBranchId(Long branchId);
}
