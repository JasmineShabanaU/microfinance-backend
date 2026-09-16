package ad.example.microfinance.controller;

import ad.example.microfinance.entity.EMI;
import ad.example.microfinance.entity.Loan;
import ad.example.microfinance.service.LoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/v1/loans", "/api/loan"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class LoanController {


    private final LoanService loanService;


    // Manual constructor (replaces @RequiredArgsConstructor)
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }



    @GetMapping
    public ResponseEntity<List<Loan>> all() {

        return ResponseEntity.ok(
                loanService.findAllLoans()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<Loan> get(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                loanService.findLoanById(id)
        );

    }



    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<EMI>> getSchedule(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                loanService.getLoanSchedule(id)
        );

    }




    @GetMapping("/{id}/foreclosure")
    public ResponseEntity<Map<String,Object>> getForeclosureCalculation(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                loanService.calculateForeclosure(id)
        );

    }





    @PutMapping("/{id}/npa-classify")
    public ResponseEntity<Loan> classifyNpa(
            @PathVariable Long id,
            @RequestBody Map<String,String> body
    ) {


        String classification =
                body.get("classification");


        return ResponseEntity.ok(
                loanService.classifyNpa(
                        id,
                        classification
                )
        );

    }

}