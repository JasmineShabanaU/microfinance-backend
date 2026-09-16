package ad.example.microfinance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_products")
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String productCode;

    @Column(nullable = false)
    private String productName;

    private String description;
    private Double minAmount;
    private Double maxAmount;
    private Double interestRate;
    private String interestMethod;
    private String tenureMonthsOptions;
    private String repaymentFrequency;
    private Double processingFeePct;
    private Double insurancePremiumPct;
    private Integer cycleNumber;
    private Integer minCibilScore;
    private Boolean isActive;

    public LoanProduct() {}

    public LoanProduct(Long id, String productCode, String productName, String description, Double minAmount,
                       Double maxAmount, Double interestRate, String interestMethod, String tenureMonthsOptions,
                       String repaymentFrequency, Double processingFeePct, Double insurancePremiumPct,
                       Integer cycleNumber, Integer minCibilScore, Boolean isActive) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.interestRate = interestRate;
        this.interestMethod = interestMethod;
        this.tenureMonthsOptions = tenureMonthsOptions;
        this.repaymentFrequency = repaymentFrequency;
        this.processingFeePct = processingFeePct;
        this.insurancePremiumPct = insurancePremiumPct;
        this.cycleNumber = cycleNumber;
        this.minCibilScore = minCibilScore;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getMinAmount() { return minAmount; }
    public void setMinAmount(Double minAmount) { this.minAmount = minAmount; }

    public Double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(Double maxAmount) { this.maxAmount = maxAmount; }

    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }

    public String getInterestMethod() { return interestMethod; }
    public void setInterestMethod(String interestMethod) { this.interestMethod = interestMethod; }

    public String getTenureMonthsOptions() { return tenureMonthsOptions; }
    public void setTenureMonthsOptions(String tenureMonthsOptions) { this.tenureMonthsOptions = tenureMonthsOptions; }

    public String getRepaymentFrequency() { return repaymentFrequency; }
    public void setRepaymentFrequency(String repaymentFrequency) { this.repaymentFrequency = repaymentFrequency; }

    public Double getProcessingFeePct() { return processingFeePct; }
    public void setProcessingFeePct(Double processingFeePct) { this.processingFeePct = processingFeePct; }

    public Double getInsurancePremiumPct() { return insurancePremiumPct; }
    public void setInsurancePremiumPct(Double insurancePremiumPct) { this.insurancePremiumPct = insurancePremiumPct; }

    public Integer getCycleNumber() { return cycleNumber; }
    public void setCycleNumber(Integer cycleNumber) { this.cycleNumber = cycleNumber; }

    public Integer getMinCibilScore() { return minCibilScore; }
    public void setMinCibilScore(Integer minCibilScore) { this.minCibilScore = minCibilScore; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
