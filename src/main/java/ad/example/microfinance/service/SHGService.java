package ad.example.microfinance.service;

import ad.example.microfinance.entity.SHG;
import ad.example.microfinance.entity.SHGSavings;

import java.util.List;

public interface SHGService {
    SHG save(SHG shg);
    List<SHG> findAll();
    SHG findById(Long id);
    void delete(Long id);
    SHGSavings recordSavings(SHGSavings savings);
    List<SHGSavings> getSavingsByGroupId(Long groupId);
}
