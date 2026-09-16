package ad.example.microfinance.controller;

import ad.example.microfinance.entity.Disbursement;
import ad.example.microfinance.entity.Loan;
import ad.example.microfinance.service.LoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping({"/api/v1/disbursements", "/api/disbursements"})
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081"})
public class DisbursementController {


    private final LoanService loanService;


    // Manual constructor (replaces Lombok @RequiredArgsConstructor)
    public DisbursementController(LoanService loanService) {
        this.loanService = loanService;
    }



    @PostMapping
    public ResponseEntity<Loan> initiateDisbursement(
            @RequestBody Map<String, Object> request
    ) {

        Long applicationId =
                Long.valueOf(request.get("applicationId").toString());


        String mode =
                request.get("mode") != null
                        ? request.get("mode").toString()
                        : "NEFT";


        String bankAccount =
                request.get("bankAccount") != null
                        ? request.get("bankAccount").toString()
                        : "XXXX-XXXX-9901";


        String ifsc =
                request.get("ifsc") != null
                        ? request.get("ifsc").toString()
                        : "SBIN0001234";


        return ResponseEntity.ok(
                loanService.disburseLoan(
                        applicationId,
                        mode,
                        bankAccount,
                        ifsc
                )
        );

    }




    @GetMapping
    public ResponseEntity<List<Disbursement>> getAllDisbursements() {

        return ResponseEntity.ok(
                loanService.findAllDisbursements()
        );

    }





    @GetMapping("/loan/{loanId}")
    public ResponseEntity<Disbursement> getByLoanId(
            @PathVariable Long loanId
    ) {

        return ResponseEntity.ok(
                loanService.findDisbursementByLoanId(loanId)
        );

    }





    @PostMapping("/penny-drop")
    public ResponseEntity<Map<String,Object>> verifyPennyDrop(
            @RequestBody Map<String,String> request
    ) {

        String account = request.get("bankAccount");
        String ifsc = request.get("ifsc");


        return ResponseEntity.ok(
                loanService.verifyPennyDrop(
                        account,
                        ifsc
                )
        );

    }

}