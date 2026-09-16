package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.JLG;
import ad.example.microfinance.exception.InsufficientGroupSizeException;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.JLGRepository;
import ad.example.microfinance.service.JLGService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JLGServiceImpl implements JLGService {

    private final JLGRepository repository;

    public JLGServiceImpl(JLGRepository repository) {
        this.repository = repository;
    }

    @Override
    public JLG save(JLG jlg) {
        // Enforce 4-10 members per group rule per FR3 & Appendix C
        if (jlg.getMemberCount() != null && (jlg.getMemberCount() < 4 || jlg.getMemberCount() > 10)) {
            throw new InsufficientGroupSizeException("Joint Liability Group (JLG) must have between 4 and 10 members (Current count: " + jlg.getMemberCount() + ")");
        }
        if (jlg.getMemberCount() == null) jlg.setMemberCount(5);
        if (jlg.getGroupType() == null) jlg.setGroupType("JLG");
        if (jlg.getFormationDate() == null) jlg.setFormationDate(LocalDate.now());
        if (jlg.getCohesionScore() == null) jlg.setCohesionScore(85.0);
        if (jlg.getGrtStatus() == null) jlg.setGrtStatus("PASSED");
        if (jlg.getIsActive() == null) jlg.setIsActive(true);

        return repository.save(jlg);
    }

    @Override
    public List<JLG> findAll() {
        return repository.findAll();
    }

    @Override
    public JLG findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JLG Group not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public JLG scheduleGrt(Long groupId, String grtDate) {
        JLG jlg = findById(groupId);
        jlg.setGrtDate(LocalDate.parse(grtDate));
        jlg.setGrtStatus("SCHEDULED");
        return repository.save(jlg);
    }

    @Override
    public JLG recordGrtResult(Long groupId, String status, Double cohesionScore) {
        JLG jlg = findById(groupId);
        jlg.setGrtStatus(status);
        if (cohesionScore != null) {
            jlg.setCohesionScore(cohesionScore);
        }
        return repository.save(jlg);
    }
}
