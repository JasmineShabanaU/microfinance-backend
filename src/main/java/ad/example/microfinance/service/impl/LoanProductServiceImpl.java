package ad.example.microfinance.service.impl;

import ad.example.microfinance.entity.LoanProduct;
import ad.example.microfinance.exception.ResourceNotFoundException;
import ad.example.microfinance.repository.LoanProductRepository;
import ad.example.microfinance.service.LoanProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanProductServiceImpl implements LoanProductService {

    private final LoanProductRepository repository;

    public LoanProductServiceImpl(LoanProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public LoanProduct save(LoanProduct product) {
        if (product.getIsActive() == null) product.setIsActive(true);
        if (product.getInterestMethod() == null) product.setInterestMethod("REDUCING");
        return repository.save(product);
    }

    @Override
    public List<LoanProduct> findAll() {
        return repository.findAll();
    }

    @Override
    public LoanProduct findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan product not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
