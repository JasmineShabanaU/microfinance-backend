package ad.example.microfinance.controller;

import ad.example.microfinance.entity.Borrower;
import ad.example.microfinance.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/v1/kyc", "/api/kyc"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class KycController {

    private final BorrowerService borrowerService;

    public KycController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @PostMapping("/verify")
    public ResponseEntity<Borrower> verifyKyc(@RequestBody Map<String, Object> request) {
        Long borrowerId = Long.valueOf(request.get("borrowerId").toString());
        String otp = request.get("otp").toString();
        return ResponseEntity.ok(borrowerService.verifyAadhaarKyc(borrowerId, otp));
    }
}
