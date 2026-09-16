package ad.example.microfinance.controller;

import ad.example.microfinance.entity.EMI;
import ad.example.microfinance.entity.Repayment;
import ad.example.microfinance.service.RepaymentService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/v1/repayments", "/api/repayment"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class RepaymentController {


    private final RepaymentService repaymentService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public RepaymentController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }



    @PostMapping
    public ResponseEntity<Repayment> recordRepayment(
            @RequestBody Repayment repayment
    ) {

        return ResponseEntity.ok(
                repaymentService.recordRepayment(repayment)
        );

    }



    @GetMapping
    public ResponseEntity<List<Repayment>> all() {

        return ResponseEntity.ok(
                repaymentService.findAll()
        );

    }



    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<Repayment>> getByLoanId(
            @PathVariable Long loanId
    ) {

        return ResponseEntity.ok(
                repaymentService.findByLoanId(loanId)
        );

    }



    @GetMapping("/daily-sheet")
    public ResponseEntity<List<EMI>> getDailyCollectionSheet(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {

        return ResponseEntity.ok(
                repaymentService.getDailyCollectionSheet(date)
        );

    }



    @PostMapping("/offline-sync")
    public ResponseEntity<Map<String,Object>> offlineSync(
            @RequestBody List<Repayment> offlineList
    ) {

        return ResponseEntity.ok(
                repaymentService.syncOfflineRepayments(offlineList)
        );

    }

}