package ad.example.microfinance.service;

import ad.example.microfinance.entity.LoanProduct;
import java.util.List;

public interface LoanProductService {
    LoanProduct save(LoanProduct product);
    List<LoanProduct> findAll();
    LoanProduct findById(Long id);
    void delete(Long id);
}
