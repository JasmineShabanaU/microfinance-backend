package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "emi_schedule")
public class EMI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanId;
    private Integer installmentNumber;
    private LocalDate dueDate;
    private Double principalDue;
    private Double interestDue;
    private Double totalDue;
    private Double principalPaid;
    private Double interestPaid;
    private Double penaltyCharged;
    private LocalDate paymentDate;
    private String status;

    public EMI() {}

    public EMI(Long id, Long loanId, Integer installmentNumber, LocalDate dueDate, Double principalDue,
               Double interestDue, Double totalDue, Double principalPaid, Double interestPaid,
               Double penaltyCharged, LocalDate paymentDate, String status) {
        this.id = id;
        this.loanId = loanId;
        this.installmentNumber = installmentNumber;
        this.dueDate = dueDate;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.totalDue = totalDue;
        this.principalPaid = principalPaid;
        this.interestPaid = interestPaid;
        this.penaltyCharged = penaltyCharged;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    @PrePersist
    public void onCreate() {
        if (this.principalPaid == null) this.principalPaid = 0.0;
        if (this.interestPaid == null) this.interestPaid = 0.0;
        if (this.penaltyCharged == null) this.penaltyCharged = 0.0;
        if (this.status == null) this.status = "PENDING";
        if (this.totalDue == null && this.principalDue != null && this.interestDue != null) {
            this.totalDue = this.principalDue + this.interestDue;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public Integer getInstallmentNumber() { return installmentNumber; }
    public void setInstallmentNumber(Integer installmentNumber) { this.installmentNumber = installmentNumber; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Double getPrincipalDue() { return principalDue; }
    public void setPrincipalDue(Double principalDue) { this.principalDue = principalDue; }

    public Double getInterestDue() { return interestDue; }
    public void setInterestDue(Double interestDue) { this.interestDue = interestDue; }

    public Double getTotalDue() { return totalDue; }
    public void setTotalDue(Double totalDue) { this.totalDue = totalDue; }

    public Double getPrincipalPaid() { return principalPaid; }
    public void setPrincipalPaid(Double principalPaid) { this.principalPaid = principalPaid; }

    public Double getInterestPaid() { return interestPaid; }
    public void setInterestPaid(Double interestPaid) { this.interestPaid = interestPaid; }

    public Double getPenaltyCharged() { return penaltyCharged; }
    public void setPenaltyCharged(Double penaltyCharged) { this.penaltyCharged = penaltyCharged; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
