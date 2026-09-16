package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_claims")
public class InsuranceClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanId;
    private Long borrowerId;
    private String borrowerName;
    private LocalDate deathDate;
    private Double claimAmount;
    private String insurerReference;
    private String status;
    private Double settlementAmount;
    private LocalDateTime settledAt;
    private String documents;
    private LocalDateTime createdAt;

    public InsuranceClaim() {}

    public InsuranceClaim(Long id, Long loanId, Long borrowerId, String borrowerName, LocalDate deathDate,
                          Double claimAmount, String insurerReference, String status, Double settlementAmount,
                          LocalDateTime settledAt, String documents, LocalDateTime createdAt) {
        this.id = id;
        this.loanId = loanId;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.deathDate = deathDate;
        this.claimAmount = claimAmount;
        this.insurerReference = insurerReference;
        this.status = status;
        this.settlementAmount = settlementAmount;
        this.settledAt = settledAt;
        this.documents = documents;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "SUBMITTED";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public LocalDate getDeathDate() { return deathDate; }
    public void setDeathDate(LocalDate deathDate) { this.deathDate = deathDate; }

    public Double getClaimAmount() { return claimAmount; }
    public void setClaimAmount(Double claimAmount) { this.claimAmount = claimAmount; }

    public String getInsurerReference() { return insurerReference; }
    public void setInsurerReference(String insurerReference) { this.insurerReference = insurerReference; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getSettlementAmount() { return settlementAmount; }
    public void setSettlementAmount(Double settlementAmount) { this.settlementAmount = settlementAmount; }

    public LocalDateTime getSettledAt() { return settledAt; }
    public void setSettledAt(LocalDateTime settledAt) { this.settledAt = settledAt; }

    public String getDocuments() { return documents; }
    public void setDocuments(String documents) { this.documents = documents; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
