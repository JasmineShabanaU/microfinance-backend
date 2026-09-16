package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "disbursements")
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long loanId;

    private Long borrowerId;
    private String borrowerName;
    private String mode;
    private String bankAccountNumber;
    private String ifscCode;
    private String utrNumber;
    private Double disbursedAmount;
    private Double processingFee;
    private Double insurancePremium;
    private Double netDisbursed;
    private LocalDate disbursementDate;
    private String status;

    public Disbursement() {}

    public Disbursement(Long id, Long loanId, Long borrowerId, String borrowerName, String mode,
                        String bankAccountNumber, String ifscCode, String utrNumber, Double disbursedAmount,
                        Double processingFee, Double insurancePremium, Double netDisbursed, LocalDate disbursementDate,
                        String status) {
        this.id = id;
        this.loanId = loanId;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.mode = mode;
        this.bankAccountNumber = bankAccountNumber;
        this.ifscCode = ifscCode;
        this.utrNumber = utrNumber;
        this.disbursedAmount = disbursedAmount;
        this.processingFee = processingFee;
        this.insurancePremium = insurancePremium;
        this.netDisbursed = netDisbursed;
        this.disbursementDate = disbursementDate;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        if (this.disbursementDate == null) this.disbursementDate = LocalDate.now();
        if (this.status == null) this.status = "SUCCESS";
        if (this.netDisbursed == null && this.disbursedAmount != null) {
            double pf = this.processingFee != null ? this.processingFee : 0.0;
            double ins = this.insurancePremium != null ? this.insurancePremium : 0.0;
            this.netDisbursed = this.disbursedAmount - pf - ins;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getUtrNumber() { return utrNumber; }
    public void setUtrNumber(String utrNumber) { this.utrNumber = utrNumber; }

    public Double getDisbursedAmount() { return disbursedAmount; }
    public void setDisbursedAmount(Double disbursedAmount) { this.disbursedAmount = disbursedAmount; }

    public Double getProcessingFee() { return processingFee; }
    public void setProcessingFee(Double processingFee) { this.processingFee = processingFee; }

    public Double getInsurancePremium() { return insurancePremium; }
    public void setInsurancePremium(Double insurancePremium) { this.insurancePremium = insurancePremium; }

    public Double getNetDisbursed() { return netDisbursed; }
    public void setNetDisbursed(Double netDisbursed) { this.netDisbursed = netDisbursed; }

    public LocalDate getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(LocalDate disbursementDate) { this.disbursementDate = disbursementDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
