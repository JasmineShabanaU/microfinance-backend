package ad.example.microfinance.service;

import ad.example.microfinance.entity.JLG;
import java.util.List;

public interface JLGService {
    JLG save(JLG jlg);
    List<JLG> findAll();
    JLG findById(Long id);
    void delete(Long id);
    JLG scheduleGrt(Long groupId, String grtDate);
    JLG recordGrtResult(Long groupId, String status, Double cohesionScore);
}
