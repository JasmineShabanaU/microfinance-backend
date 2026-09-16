package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String branchCode;

    @Column(nullable = false)
    private String branchName;

    private String district;
    private String state;
    private String pincode;
    private Long managerId;
    private String managerName;
    private Boolean isActive;
    private LocalDateTime createdAt;

    public Branch() {}

    public Branch(Long id, String branchCode, String branchName, String district, String state, String pincode,
                  Long managerId, String managerName, Boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.managerId = managerId;
        this.managerName = managerName;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDateTime.now();
        if (this.isActive == null) this.isActive = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }

    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
