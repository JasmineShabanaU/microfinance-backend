package ad.example.microfinance.controller;

import ad.example.microfinance.entity.Borrower;
import ad.example.microfinance.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/v1/borrowers", "/api/borrower"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class BorrowerController {

    private final BorrowerService service;

    public BorrowerController(BorrowerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Borrower> create(@RequestBody Borrower obj) {
        return ResponseEntity.ok(service.save(obj));
    }

    @GetMapping
    public ResponseEntity<List<Borrower>> all() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrower> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/verify-kyc")
    public ResponseEntity<Borrower> verifyKyc(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String otp = body.get("otp");
        return ResponseEntity.ok(service.verifyAadhaarKyc(id, otp));
    }

    @GetMapping("/{id}/cibil")
    public ResponseEntity<Map<String, Object>> getCibilReport(@PathVariable Long id) {
        return ResponseEntity.ok(service.queryCreditBureau(id));
    }
}