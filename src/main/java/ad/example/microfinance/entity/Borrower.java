package ad.example.microfinance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrowers")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String brn;

    @Column(name = "aadhaar_hash", length = 64, nullable = false)
    private String aadhaarHash;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String mobile;

    private LocalDate dateOfBirth;
    private String gender;

    private String addressVillage;
    private String addressDistrict;
    private String addressState;
    private String pincode;

    private Double incomeMonthly;
    private String occupationType;

    private Integer cibilScore;
    private LocalDate creditBureauDate;

    private String kycStatus;
    private String rejectionReason;

    private Double totalExistingIndebtedness;
    private String photoUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Borrower() {}

    public Borrower(Long id, String brn, String aadhaarHash, String panNumber, String fullName, String mobile,
                    LocalDate dateOfBirth, String gender, String addressVillage, String addressDistrict,
                    String addressState, String pincode, Double incomeMonthly, String occupationType,
                    Integer cibilScore, LocalDate creditBureauDate, String kycStatus, String rejectionReason,
                    Double totalExistingIndebtedness, String photoUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.brn = brn;
        this.aadhaarHash = aadhaarHash;
        this.panNumber = panNumber;
        this.fullName = fullName;
        this.mobile = mobile;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addressVillage = addressVillage;
        this.addressDistrict = addressDistrict;
        this.addressState = addressState;
        this.pincode = pincode;
        this.incomeMonthly = incomeMonthly;
        this.occupationType = occupationType;
        this.cibilScore = cibilScore;
        this.creditBureauDate = creditBureauDate;
        this.kycStatus = kycStatus;
        this.rejectionReason = rejectionReason;
        this.totalExistingIndebtedness = totalExistingIndebtedness;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.kycStatus == null) this.kycStatus = "PENDING";
        if (this.totalExistingIndebtedness == null) this.totalExistingIndebtedness = 0.0;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrn() { return brn; }
    public void setBrn(String brn) { this.brn = brn; }

    public String getAadhaarHash() { return aadhaarHash; }
    public void setAadhaarHash(String aadhaarHash) { this.aadhaarHash = aadhaarHash; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddressVillage() { return addressVillage; }
    public void setAddressVillage(String addressVillage) { this.addressVillage = addressVillage; }

    public String getAddressDistrict() { return addressDistrict; }
    public void setAddressDistrict(String addressDistrict) { this.addressDistrict = addressDistrict; }

    public String getAddressState() { return addressState; }
    public void setAddressState(String addressState) { this.addressState = addressState; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public Double getIncomeMonthly() { return incomeMonthly; }
    public void setIncomeMonthly(Double incomeMonthly) { this.incomeMonthly = incomeMonthly; }

    public String getOccupationType() { return occupationType; }
    public void setOccupationType(String occupationType) { this.occupationType = occupationType; }

    public Integer getCibilScore() { return cibilScore; }
    public void setCibilScore(Integer cibilScore) { this.cibilScore = cibilScore; }

    public LocalDate getCreditBureauDate() { return creditBureauDate; }
    public void setCreditBureauDate(LocalDate creditBureauDate) { this.creditBureauDate = creditBureauDate; }

    public String getKycStatus() { return kycStatus; }
    public void setKycStatus(String kycStatus) { this.kycStatus = kycStatus; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Double getTotalExistingIndebtedness() { return totalExistingIndebtedness; }
    public void setTotalExistingIndebtedness(Double totalExistingIndebtedness) { this.totalExistingIndebtedness = totalExistingIndebtedness; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Backward-compatibility aliases
    public String getName() { return fullName != null ? fullName : ""; }
    public void setName(String name) { this.fullName = name; }

    public String getAadhaar() { return aadhaarHash; }
    public void setAadhaar(String aadhaar) { this.aadhaarHash = aadhaar; }

    public String getAddress() {
        if (addressVillage != null && addressDistrict != null) {
            return addressVillage + ", " + addressDistrict + (addressState != null ? ", " + addressState : "");
        }
        return addressVillage != null ? addressVillage : "";
    }
    public void setAddress(String address) { this.addressVillage = address; }
}