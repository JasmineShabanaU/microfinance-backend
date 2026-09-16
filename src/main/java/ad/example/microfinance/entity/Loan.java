package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long applicationId;

    @Column(unique = true, nullable = false)
    private String loanNumber;

    private Long borrowerId;
    private String borrowerName;

    private Long productId;
    private String productName;

    private Double principalAmount;
    private Double outstandingPrincipal;
    private Double interestRate;
    private Integer tenureMonths;

    private LocalDate disbursementDate;
    private LocalDate maturityDate;

    private String status;
    private Integer dpd;
    private String parClassification;
    private Double totalRepaid;
    private String purpose;

    public Loan() {}

    public Loan(Long id, Long applicationId, String loanNumber, Long borrowerId, String borrowerName, Long productId,
                String productName, Double principalAmount, Double outstandingPrincipal, Double interestRate,
                Integer tenureMonths, LocalDate disbursementDate, LocalDate maturityDate, String status, Integer dpd,
                String parClassification, Double totalRepaid, String purpose) {
        this.id = id;
        this.applicationId = applicationId;
        this.loanNumber = loanNumber;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.productId = productId;
        this.productName = productName;
        this.principalAmount = principalAmount;
        this.outstandingPrincipal = outstandingPrincipal;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.disbursementDate = disbursementDate;
        this.maturityDate = maturityDate;
        this.status = status;
        this.dpd = dpd;
        this.parClassification = parClassification;
        this.totalRepaid = totalRepaid;
        this.purpose = purpose;
    }

    @PrePersist
    public void onCreate() {
        if (this.dpd == null) this.dpd = 0;
        if (this.status == null) this.status = "ACTIVE";
        if (this.parClassification == null) this.parClassification = "STANDARD";
        if (this.outstandingPrincipal == null) this.outstandingPrincipal = this.principalAmount;
        if (this.totalRepaid == null) this.totalRepaid = 0.0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public String getLoanNumber() { return loanNumber; }
    public void setLoanNumber(String loanNumber) { this.loanNumber = loanNumber; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(Double principalAmount) { this.principalAmount = principalAmount; }

    public Double getOutstandingPrincipal() { return outstandingPrincipal; }
    public void setOutstandingPrincipal(Double outstandingPrincipal) { this.outstandingPrincipal = outstandingPrincipal; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public LocalDate getDisbursementDate() { return disbursementDate; }
    public void setDisbursementDate(LocalDate disbursementDate) { this.disbursementDate = disbursementDate; }

    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getDpd() { return dpd; }
    public void setDpd(Integer dpd) { this.dpd = dpd; }

    public String getParClassification() { return parClassification; }
    public void setParClassification(String parClassification) { this.parClassification = parClassification; }

    public Double getTotalRepaid() { return totalRepaid; }
    public void setTotalRepaid(Double totalRepaid) { this.totalRepaid = totalRepaid; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    // Backward-compatibility aliases
    public Double getAmount() { return principalAmount; }
    public void setAmount(Double amount) { this.principalAmount = amount; }
    public Integer getTenure() { return tenureMonths; }
    public void setTenure(Integer tenure) { this.tenureMonths = tenure; }
}