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
@RequestMapping({"/api/v1", "/api"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class CollectionController {


    private final RepaymentService repaymentService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public CollectionController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }



    @GetMapping("/collection/daily-sheet")
    public ResponseEntity<List<EMI>> getDailySheet(
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
            @RequestBody List<Repayment> repayments
    ) {

        return ResponseEntity.ok(
                repaymentService.syncOfflineRepayments(repayments)
        );

    }

}