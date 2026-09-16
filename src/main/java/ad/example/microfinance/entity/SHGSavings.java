package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shg_savings")
public class SHGSavings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId;
    private Long borrowerId;
    private String borrowerName;
    private Double amount;
    private String transactionType;
    private LocalDate transactionDate;
    private Double runningBalance;
    private Long recordedBy;
    private Long meetingId;

    public SHGSavings() {}

    public SHGSavings(Long id, Long groupId, Long borrowerId, String borrowerName, Double amount,
                      String transactionType, LocalDate transactionDate, Double runningBalance, Long recordedBy, Long meetingId) {
        this.id = id;
        this.groupId = groupId;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.runningBalance = runningBalance;
        this.recordedBy = recordedBy;
        this.meetingId = meetingId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getBorrowerId() { return borrowerId; }
    public void setBorrowerId(Long borrowerId) { this.borrowerId = borrowerId; }

    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public Double getRunningBalance() { return runningBalance; }
    public void setRunningBalance(Double runningBalance) { this.runningBalance = runningBalance; }

    public Long getRecordedBy() { return recordedBy; }
    public void setRecordedBy(Long recordedBy) { this.recordedBy = recordedBy; }

    public Long getMeetingId() { return meetingId; }
    public void setMeetingId(Long meetingId) { this.meetingId = meetingId; }
}
