package com.ats.hreasy.model;

public class ClaimApp {

    private Integer claimId;
    private Integer empId;
    private String empCode;
    private String empName;
    private Integer projectId;
    private String projectTitle;
    private Integer claimTypeId;
    private String claimTypeName;
    private Integer claimAmount;
    private String claimRemarks;
    private String claimDate;
    private String circulatedTo;
    private Integer exInt1;
    private Integer caIniAuthEmpId;
    private Integer caFinAuthEmpId;
    private String empPhoto;


    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public Integer getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(Integer claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public String getClaimTypeName() {
        return claimTypeName;
    }

    public void setClaimTypeName(String claimTypeName) {
        this.claimTypeName = claimTypeName;
    }

    public Integer getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Integer claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimRemarks() {
        return claimRemarks;
    }

    public void setClaimRemarks(String claimRemarks) {
        this.claimRemarks = claimRemarks;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getCaIniAuthEmpId() {
        return caIniAuthEmpId;
    }

    public void setCaIniAuthEmpId(Integer caIniAuthEmpId) {
        this.caIniAuthEmpId = caIniAuthEmpId;
    }

    public Integer getCaFinAuthEmpId() {
        return caFinAuthEmpId;
    }

    public void setCaFinAuthEmpId(Integer caFinAuthEmpId) {
        this.caFinAuthEmpId = caFinAuthEmpId;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    @Override
    public String toString() {
        return "ClaimApp{" +
                "claimId=" + claimId +
                ", empId=" + empId +
                ", empCode='" + empCode + '\'' +
                ", empName='" + empName + '\'' +
                ", projectId=" + projectId +
                ", projectTitle='" + projectTitle + '\'' +
                ", claimTypeId=" + claimTypeId +
                ", claimTypeName='" + claimTypeName + '\'' +
                ", claimAmount=" + claimAmount +
                ", claimRemarks='" + claimRemarks + '\'' +
                ", claimDate='" + claimDate + '\'' +
                ", circulatedTo='" + circulatedTo + '\'' +
                ", exInt1=" + exInt1 +
                ", caIniAuthEmpId=" + caIniAuthEmpId +
                ", caFinAuthEmpId=" + caFinAuthEmpId +
                ", empPhoto='" + empPhoto + '\'' +
                '}';
    }
}
