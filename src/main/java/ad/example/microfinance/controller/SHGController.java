package ad.example.microfinance.controller;

import ad.example.microfinance.entity.SHG;
import ad.example.microfinance.entity.SHGSavings;
import ad.example.microfinance.service.SHGService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping({"/api/v1/shg-groups", "/api/shg"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class SHGController {


    private final SHGService service;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public SHGController(SHGService service) {
        this.service = service;
    }



    @PostMapping
    public ResponseEntity<SHG> create(
            @RequestBody SHG obj
    ) {

        return ResponseEntity.ok(
                service.save(obj)
        );

    }



    @GetMapping
    public ResponseEntity<List<SHG>> all() {

        return ResponseEntity.ok(
                service.findAll()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<SHG> get(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.findById(id)
        );

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return ResponseEntity.noContent().build();

    }



    @PostMapping("/savings")
    public ResponseEntity<SHGSavings> recordSavings(
            @RequestBody SHGSavings savings
    ) {

        return ResponseEntity.ok(
                service.recordSavings(savings)
        );

    }



    @GetMapping("/{id}/savings")
    public ResponseEntity<List<SHGSavings>> getSavings(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.getSavingsByGroupId(id)
        );

    }

}