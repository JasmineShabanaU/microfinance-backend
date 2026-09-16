package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan_groups")
public class JLG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupType;
    private String groupName;
    private Long groupLeaderId;
    private String leaderName;
    private Long branchId;
    private Long loanOfficerId;
    private String loanOfficerName;
    private String village;
    private String district;
    private LocalDate formationDate;
    private LocalDate grtDate;
    private String grtStatus;
    private Double cohesionScore;
    private String meetingDay;
    private String meetingFrequency;
    private Integer memberCount;
    private Boolean isActive;

    public JLG() {}

    public JLG(Long id, String groupType, String groupName, Long groupLeaderId, String leaderName, Long branchId,
               Long loanOfficerId, String loanOfficerName, String village, String district, LocalDate formationDate,
               LocalDate grtDate, String grtStatus, Double cohesionScore, String meetingDay, String meetingFrequency,
               Integer memberCount, Boolean isActive) {
        this.id = id;
        this.groupType = groupType;
        this.groupName = groupName;
        this.groupLeaderId = groupLeaderId;
        this.leaderName = leaderName;
        this.branchId = branchId;
        this.loanOfficerId = loanOfficerId;
        this.loanOfficerName = loanOfficerName;
        this.village = village;
        this.district = district;
        this.formationDate = formationDate;
        this.grtDate = grtDate;
        this.grtStatus = grtStatus;
        this.cohesionScore = cohesionScore;
        this.meetingDay = meetingDay;
        this.meetingFrequency = meetingFrequency;
        this.memberCount = memberCount;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGroupType() { return groupType; }
    public void setGroupType(String groupType) { this.groupType = groupType; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public Long getGroupLeaderId() { return groupLeaderId; }
    public void setGroupLeaderId(Long groupLeaderId) { this.groupLeaderId = groupLeaderId; }

    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public Long getLoanOfficerId() { return loanOfficerId; }
    public void setLoanOfficerId(Long loanOfficerId) { this.loanOfficerId = loanOfficerId; }

    public String getLoanOfficerName() { return loanOfficerName; }
    public void setLoanOfficerName(String loanOfficerName) { this.loanOfficerName = loanOfficerName; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public LocalDate getFormationDate() { return formationDate; }
    public void setFormationDate(LocalDate formationDate) { this.formationDate = formationDate; }

    public LocalDate getGrtDate() { return grtDate; }
    public void setGrtDate(LocalDate grtDate) { this.grtDate = grtDate; }

    public String getGrtStatus() { return grtStatus; }
    public void setGrtStatus(String grtStatus) { this.grtStatus = grtStatus; }

    public Double getCohesionScore() { return cohesionScore; }
    public void setCohesionScore(Double cohesionScore) { this.cohesionScore = cohesionScore; }

    public String getMeetingDay() { return meetingDay; }
    public void setMeetingDay(String meetingDay) { this.meetingDay = meetingDay; }

    public String getMeetingFrequency() { return meetingFrequency; }
    public void setMeetingFrequency(String meetingFrequency) { this.meetingFrequency = meetingFrequency; }

    public Integer getMemberCount() { return memberCount; }
    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
