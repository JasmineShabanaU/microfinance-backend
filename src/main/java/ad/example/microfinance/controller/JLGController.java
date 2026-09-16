package ad.example.microfinance.controller;

import ad.example.microfinance.entity.JLG;
import ad.example.microfinance.service.JLGService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/v1/jlg-groups", "/api/jlg"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class JLGController {


    private final JLGService service;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public JLGController(JLGService service) {
        this.service = service;
    }



    @PostMapping
    public ResponseEntity<JLG> create(
            @RequestBody JLG obj
    ) {

        return ResponseEntity.ok(
                service.save(obj)
        );

    }



    @GetMapping
    public ResponseEntity<List<JLG>> all() {

        return ResponseEntity.ok(
                service.findAll()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<JLG> get(
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



    @PostMapping("/{id}/grt-schedule")
    public ResponseEntity<JLG> scheduleGrt(
            @PathVariable Long id,
            @RequestBody Map<String,String> body
    ) {

        String date = body.get("grtDate");

        return ResponseEntity.ok(
                service.scheduleGrt(id, date)
        );

    }



    @PostMapping("/{id}/grt-result")
    public ResponseEntity<JLG> recordGrtResult(
            @PathVariable Long id,
            @RequestBody Map<String,Object> body
    ) {

        String status = (String) body.get("status");


        Double cohesion =
                body.get("cohesionScore") != null
                        ? Double.valueOf(body.get("cohesionScore").toString())
                        : null;


        return ResponseEntity.ok(
                service.recordGrtResult(
                        id,
                        status,
                        cohesion
                )
        );

    }

}