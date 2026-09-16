package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "repayment_transactions")
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanId;
    private Long scheduleId;
    private Long borrowerId;
    private String borrowerName;
    private Integer installmentNo;
    private Double amount;
    private String paymentMode;

    @Column(unique = true, nullable = false)
    private String receiptNumber;

    private Long collectedBy;
    private String collectorName;
    private LocalDate collectionDate;
    private LocalDate paidDate;
    private LocalDate dueDate;
    private String status;
    private Double gpsLat;
    private Double gpsLng;
    private Boolean isOfflineSync;
    private LocalDateTime syncedAt;
    private LocalDateTime createdAt;

    public Repayment() {}

    public Repayment(Long id, Long loanId, Long scheduleId, Long borrowerId, String borrowerName, Integer installmentNo,
                     Double amount, String paymentMode, String receiptNumber, Long collectedBy, String collectorName,
                     LocalDate collectionDate, LocalDate paidDate, LocalDate dueDate, String status, Double gpsLat,
                     Double gpsLng, Boolean isOfflineSync, LocalDateTime syncedAt, LocalDateTime createdAt) {
        this.id = id;
        this.loanId = loanId;
        this.scheduleId = scheduleId;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.installmentNo = installmentNo;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.receiptNumber = receiptNumber;
        this.collectedBy = collectedBy;
        this.collectorName = collectorName;
        this.collectionDate = collectionDate;
        this.paidDate = paidDate;
        this.dueDate = dueDate;
        this.status = status;
        this.gpsLat = gpsLat;
        this.gpsLng = gpsLng;
        this.isOfflineSync = isOfflineSync;
        this.syncedAt = syncedAt;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.collectionDate == null) this.collectionDate = LocalDate.now();
        if (this.paidDate == null) this.paidDate = LocalDate.now();
        if (this.isOfflineSync == null) this.isOfflineSync = false;
        if (this.status == null) this.status = "PAID";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public Integer getInstallmentNo() { return installmentNo; }
    public void setInstallmentNo(Integer installmentNo) { this.installmentNo = installmentNo; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public Long getCollectedBy() { return collectedBy; }
    public void setCollectedBy(Long collectedBy) { this.collectedBy = collectedBy; }

    public String getCollectorName() { return collectorName; }
    public void setCollectorName(String collectorName) { this.collectorName = collectorName; }

    public LocalDate getCollectionDate() { return collectionDate; }
    public void setCollectionDate(LocalDate collectionDate) { this.collectionDate = collectionDate; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getGpsLat() { return gpsLat; }
    public void setGpsLat(Double gpsLat) { this.gpsLat = gpsLat; }

    public Double getGpsLng() { return gpsLng; }
    public void setGpsLng(Double gpsLng) { this.gpsLng = gpsLng; }

    public Boolean getIsOfflineSync() { return isOfflineSync; }
    public void setIsOfflineSync(Boolean isOfflineSync) { this.isOfflineSync = isOfflineSync; }

    public LocalDateTime getSyncedAt() { return syncedAt; }
    public void setSyncedAt(LocalDateTime syncedAt) { this.syncedAt = syncedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}