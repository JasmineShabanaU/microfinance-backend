package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shg_groups")
public class SHG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupName;

    private String leaderName;
    private String village;
    private String district;
    private LocalDate formationDate;
    private String bankAccountNumber;
    private String ifscCode;
    private Integer memberCount;
    private Double monthlySavingsTarget;
    private Double totalCorpus;
    private Double totalInternalLoans;
    private String grading;
    private Double linkageEligibleAmount;
    private Boolean isActive;

    public SHG() {}

    public SHG(Long id, String groupName, String leaderName, String village, String district, LocalDate formationDate,
               String bankAccountNumber, String ifscCode, Integer memberCount, Double monthlySavingsTarget,
               Double totalCorpus, Double totalInternalLoans, String grading, Double linkageEligibleAmount, Boolean isActive) {
        this.id = id;
        this.groupName = groupName;
        this.leaderName = leaderName;
        this.village = village;
        this.district = district;
        this.formationDate = formationDate;
        this.bankAccountNumber = bankAccountNumber;
        this.ifscCode = ifscCode;
        this.memberCount = memberCount;
        this.monthlySavingsTarget = monthlySavingsTarget;
        this.totalCorpus = totalCorpus;
        this.totalInternalLoans = totalInternalLoans;
        this.grading = grading;
        this.linkageEligibleAmount = linkageEligibleAmount;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public LocalDate getFormationDate() { return formationDate; }
    public void setFormationDate(LocalDate formationDate) { this.formationDate = formationDate; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public Integer getMemberCount() { return memberCount; }
    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }

    public Double getMonthlySavingsTarget() { return monthlySavingsTarget; }
    public void setMonthlySavingsTarget(Double monthlySavingsTarget) { this.monthlySavingsTarget = monthlySavingsTarget; }

    public Double getTotalCorpus() { return totalCorpus; }
    public void setTotalCorpus(Double totalCorpus) { this.totalCorpus = totalCorpus; }

    public Double getTotalInternalLoans() { return totalInternalLoans; }
    public void setTotalInternalLoans(Double totalInternalLoans) { this.totalInternalLoans = totalInternalLoans; }

    public String getGrading() { return grading; }
    public void setGrading(String grading) { this.grading = grading; }

    public Double getLinkageEligibleAmount() { return linkageEligibleAmount; }
    public void setLinkageEligibleAmount(Double linkageEligibleAmount) { this.linkageEligibleAmount = linkageEligibleAmount; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
