package ad.example.microfinance.controller;

import ad.example.microfinance.entity.LoanApplication;
import ad.example.microfinance.service.LoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/v1/loan-applications", "/api/loan-applications"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class LoanApplicationController {


    private final LoanService loanService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public LoanApplicationController(LoanService loanService) {
        this.loanService = loanService;
    }



    @PostMapping
    public ResponseEntity<LoanApplication> submit(
            @RequestBody LoanApplication application
    ) {

        return ResponseEntity.ok(
                loanService.submitApplication(application)
        );

    }



    @GetMapping
    public ResponseEntity<List<LoanApplication>> all() {

        return ResponseEntity.ok(
                loanService.findAllApplications()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> get(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                loanService.findApplicationById(id)
        );

    }



    @PutMapping("/{id}/approve")
    public ResponseEntity<LoanApplication> approve(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String,String> body
    ) {

        String remarks =
                body != null && body.get("remarks") != null
                        ? body.get("remarks")
                        : "Approved";


        String role =
                body != null && body.get("role") != null
                        ? body.get("role")
                        : "BRANCH_MANAGER";


        return ResponseEntity.ok(
                loanService.approveApplication(
                        id,
                        remarks,
                        role
                )
        );

    }



    @PutMapping("/{id}/reject")
    public ResponseEntity<LoanApplication> reject(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String,String> body
    ) {

        String reason =
                body != null && body.get("reason") != null
                        ? body.get("reason")
                        : "Does not meet criteria";


        String remarks =
                body != null && body.get("remarks") != null
                        ? body.get("remarks")
                        : "Rejected upon credit review";


        return ResponseEntity.ok(
                loanService.rejectApplication(
                        id,
                        reason,
                        remarks
                )
        );

    }

}