package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.SHG;
import ad.example.microfinance.entity.SHGSavings;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.SHGRepository;
import ad.example.microfinance.repository.SHGSavingsRepository;
import ad.example.microfinance.service.SHGService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SHGServiceImpl implements SHGService {

    private final SHGRepository shgRepository;
    private final SHGSavingsRepository savingsRepository;

    public SHGServiceImpl(SHGRepository shgRepository, SHGSavingsRepository savingsRepository) {
        this.shgRepository = shgRepository;
        this.savingsRepository = savingsRepository;
    }

    @Override
    public SHG save(SHG shg) {
        if (shg.getMemberCount() == null) shg.setMemberCount(10);
        if (shg.getMonthlySavingsTarget() == null) shg.setMonthlySavingsTarget(500.0);
        if (shg.getTotalCorpus() == null) shg.setTotalCorpus(shg.getMemberCount() * shg.getMonthlySavingsTarget() * 12);
        if (shg.getTotalInternalLoans() == null) shg.setTotalInternalLoans(shg.getTotalCorpus() * 0.4);
        if (shg.getGrading() == null) shg.setGrading("GRADE_A");

        // Bank linkage loan eligibility based on corpus multiple (typically 4-8x corpus per FR8)
        double multiplier = "GRADE_A".equalsIgnoreCase(shg.getGrading()) ? 6.0 : ("GRADE_B".equalsIgnoreCase(shg.getGrading()) ? 4.0 : 2.0);
        shg.setLinkageEligibleAmount(shg.getTotalCorpus() * multiplier);
        if (shg.getIsActive() == null) shg.setIsActive(true);

        return shgRepository.save(shg);
    }

    @Override
    public List<SHG> findAll() {
        return shgRepository.findAll();
    }

    @Override
    public SHG findById(Long id) {
        return shgRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SHG not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        shgRepository.deleteById(id);
    }

    @Override
    public SHGSavings recordSavings(SHGSavings savings) {
        if (savings.getTransactionDate() == null) savings.setTransactionDate(LocalDate.now());
        if (savings.getTransactionType() == null) savings.setTransactionType("CREDIT");

        SHG shg = findById(savings.getGroupId());
        double currentCorpus = shg.getTotalCorpus() != null ? shg.getTotalCorpus() : 0.0;
        double newCorpus = "CREDIT".equalsIgnoreCase(savings.getTransactionType())
                ? currentCorpus + savings.getAmount()
                : currentCorpus - savings.getAmount();

        savings.setRunningBalance(newCorpus);
        shg.setTotalCorpus(newCorpus);

        // Update linkage loan eligibility
        double multiplier = "GRADE_A".equalsIgnoreCase(shg.getGrading()) ? 6.0 : 4.0;
        shg.setLinkageEligibleAmount(newCorpus * multiplier);
        shgRepository.save(shg);

        return savingsRepository.save(savings);
    }

    @Override
    public List<SHGSavings> getSavingsByGroupId(Long groupId) {
        return savingsRepository.findByGroupId(groupId);
    }
}
