package ad.example.microfinance.controller;

import ad.example.microfinance.entity.SHGSavings;
import ad.example.microfinance.service.SHGService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping({"/api/v1/shg-savings", "/api/shg-savings"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class SHGSavingsController {


    private final SHGService shgService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public SHGSavingsController(SHGService shgService) {
        this.shgService = shgService;
    }



    @PostMapping
    public ResponseEntity<SHGSavings> recordSavings(
            @RequestBody SHGSavings savings
    ) {

        return ResponseEntity.ok(
                shgService.recordSavings(savings)
        );

    }

}