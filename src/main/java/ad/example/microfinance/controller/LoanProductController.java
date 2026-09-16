package ad.example.microfinance.controller;

import ad.example.microfinance.entity.LoanProduct;
import ad.example.microfinance.service.LoanProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping({"/api/v1/loan-products", "/api/loan-product"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class LoanProductController {


    private final LoanProductService service;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public LoanProductController(LoanProductService service) {
        this.service = service;
    }



    @PostMapping
    public ResponseEntity<LoanProduct> create(
            @RequestBody LoanProduct obj
    ) {

        return ResponseEntity.ok(
                service.save(obj)
        );

    }



    @GetMapping
    public ResponseEntity<List<LoanProduct>> all() {

        return ResponseEntity.ok(
                service.findAll()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<LoanProduct> get(
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

}