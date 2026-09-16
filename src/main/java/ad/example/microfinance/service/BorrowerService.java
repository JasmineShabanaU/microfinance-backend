package ad.example.microfinance.service;

import ad.example.microfinance.entity.Borrower;
import java.util.List;
import java.util.Map;

public interface BorrowerService {
    Borrower save(Borrower borrower);
    List<Borrower> findAll();
    Borrower findById(Long id);
    void delete(Long id);
    Borrower verifyAadhaarKyc(Long borrowerId, String otp);
    Map<String, Object> queryCreditBureau(Long borrowerId);
}
