package ad.example.microfinance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provider-neutral integration endpoints. They are intentionally MOCK mode so
 * the application is runnable without Aadhaar/CIBIL/bank/SMS provider keys.
 * Replace the provider implementation later without changing the UI contract.
 */
@RestController
@RequestMapping({"/api/v1/integrations", "/api/integrations"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081", "http://localhost:3000"})
public class IntegrationController {

    @GetMapping("/status")
    public Map<String,Object> status() {
        Map<String,Object> out = new LinkedHashMap<>();
        out.put("mode", "MOCK");
        out.put("aadhaarEkyc", "READY_FOR_PROVIDER_KEY");
        out.put("creditBureau", "READY_FOR_PROVIDER_KEY");
        out.put("paymentGateway", "READY_FOR_PROVIDER_KEY");
        out.put("nach", "READY_FOR_BANK_PROVIDER");
        out.put("sms", "READY_FOR_PROVIDER_KEY");
        out.put("email", "READY_FOR_PROVIDER_KEY");
        out.put("checkedAt", LocalDateTime.now());
        return out;
    }

    @PostMapping("/aadhaar/verify")
    public ResponseEntity<Map<String,Object>> aadhaar(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("AADHAAR_EKYC", "VERIFIED", "MOCK-AADHAAR-" + System.currentTimeMillis(), request));
    }

    @PostMapping("/credit-bureau/check")
    public ResponseEntity<Map<String,Object>> bureau(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("CREDIT_BUREAU", "COMPLETED", "MOCK-CIBIL-" + System.currentTimeMillis(), request));
    }

    @PostMapping("/payment/collect")
    public ResponseEntity<Map<String,Object>> payment(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("PAYMENT_GATEWAY", "SUCCESS", "MOCK-PAY-" + System.currentTimeMillis(), request));
    }

    @PostMapping("/nach/mandate")
    public ResponseEntity<Map<String,Object>> nach(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("NACH", "ACCEPTED", "MOCK-NACH-" + System.currentTimeMillis(), request));
    }

    @PostMapping("/notify/sms")
    public ResponseEntity<Map<String,Object>> sms(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("SMS", "QUEUED", "MOCK-SMS-" + System.currentTimeMillis(), request));
    }

    @PostMapping("/notify/email")
    public ResponseEntity<Map<String,Object>> email(@RequestBody Map<String,Object> request) {
        return ResponseEntity.ok(result("EMAIL", "QUEUED", "MOCK-EMAIL-" + System.currentTimeMillis(), request));
    }

    private Map<String,Object> result(String service, String status, String reference, Map<String,Object> request) {
        Map<String,Object> out = new LinkedHashMap<>();
        out.put("service", service); out.put("status", status); out.put("reference", reference);
        out.put("mock", true); out.put("received", request); out.put("timestamp", LocalDateTime.now());
        return out;
    }
}
