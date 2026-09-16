package ad.example.microfinance.controller;

import ad.example.microfinance.service.ReportService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping({"/api/v1", "/api"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class ReportController {


    private final ReportService reportService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }



    @GetMapping("/par/report")
    public ResponseEntity<Map<String, Object>> getParReport() {

        return ResponseEntity.ok(
                reportService.getParReport()
        );

    }



    @GetMapping("/reports/mfin")
    public ResponseEntity<Map<String, Object>> getMfinReport() {

        return ResponseEntity.ok(
                reportService.getMfinQuarterlyReport()
        );

    }



    @GetMapping("/reports/rbi")
    public ResponseEntity<Map<String, Object>> getRbiReport() {

        return ResponseEntity.ok(
                reportService.getRbiPrioritySectorReport()
        );

    }



    @GetMapping(value = "/reports/bureau-file", produces = "text/csv")
    public ResponseEntity<String> getBureauFile() {

        String csv = reportService.generateBureauSubmissionFile();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=CIBIL_BUREAU_REPORT_2026.csv"
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);

    }



    @GetMapping("/reports/portfolio-summary")
    public ResponseEntity<Map<String,Object>> getSummary() {

        return ResponseEntity.ok(
                reportService.getDailyPortfolioSummary()
        );

    }

}