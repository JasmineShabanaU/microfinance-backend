package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicationNumber;
    private Long borrowerId;
    private String borrowerName;
    private Long groupId;
    private Long productId;
    private String productName;
    private Long loanOfficerId;
    private String loanOfficerName;
    private Double appliedAmount;
    private Integer tenureMonths;
    private String purpose;
    private String status;
    private String branchManagerRemarks;
    private String creditCommitteeRemarks;
    private String rejectionReason;
    private Boolean isESigned;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;

    public LoanApplication() {}

    public LoanApplication(Long id, String applicationNumber, Long borrowerId, String borrowerName, Long groupId,
                           Long productId, String productName, Long loanOfficerId, String loanOfficerName,
                           Double appliedAmount, Integer tenureMonths, String purpose, String status,
                           String branchManagerRemarks, String creditCommitteeRemarks, String rejectionReason,
                           Boolean isESigned, LocalDateTime createdAt, LocalDateTime approvedAt, LocalDateTime rejectedAt) {
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.groupId = groupId;
        this.productId = productId;
        this.productName = productName;
        this.loanOfficerId = loanOfficerId;
        this.loanOfficerName = loanOfficerName;
        this.appliedAmount = appliedAmount;
        this.tenureMonths = tenureMonths;
        this.purpose = purpose;
        this.status = status;
        this.branchManagerRemarks = branchManagerRemarks;
        this.creditCommitteeRemarks = creditCommitteeRemarks;
        this.rejectionReason = rejectionReason;
        this.isESigned = isESigned;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
        this.rejectedAt = rejectedAt;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "SUBMITTED";
        if (this.isESigned == null) this.isESigned = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getApplicationNumber() { return applicationNumber; }
    public void setApplicationNumber(String applicationNumber) { this.applicationNumber = applicationNumber; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Long getLoanOfficerId() { return loanOfficerId; }
    public void setLoanOfficerId(Long loanOfficerId) { this.loanOfficerId = loanOfficerId; }

    public String getLoanOfficerName() { return loanOfficerName; }
    public void setLoanOfficerName(String loanOfficerName) { this.loanOfficerName = loanOfficerName; }

    public Double getAppliedAmount() { return appliedAmount; }
    public void setAppliedAmount(Double appliedAmount) { this.appliedAmount = appliedAmount; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBranchManagerRemarks() { return branchManagerRemarks; }
    public void setBranchManagerRemarks(String branchManagerRemarks) { this.branchManagerRemarks = branchManagerRemarks; }

    public String getCreditCommitteeRemarks() { return creditCommitteeRemarks; }
    public void setCreditCommitteeRemarks(String creditCommitteeRemarks) { this.creditCommitteeRemarks = creditCommitteeRemarks; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Boolean getIsESigned() { return isESigned; }
    public void setIsESigned(Boolean isESigned) { this.isESigned = isESigned; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getApprovedAt() { return approvedAt; }
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; }

    public LocalDateTime getRejectedAt() { return rejectedAt; }
    public void setRejectedAt(LocalDateTime rejectedAt) { this.rejectedAt = rejectedAt; }

    // Backward-compatibility aliases
    public Double getAmount() { return appliedAmount; }
    public void setAmount(Double amount) { this.appliedAmount = amount; }
    public Long getLoanProductId() { return productId; }
    public void setLoanProductId(Long loanProductId) { this.productId = loanProductId; }
}
