package com.ats.hreasy.model;

public class ClaimApply {
    private int claimId;
    private int empId;
    private int projectId;
    private int claimTypeId;
    private String claimDate;
    private float claimAmount;
    private String claimRemarks;
    private int claimFinalStatus;
    private String circulatedTo;
    private int delStatus;
    private int isActive;
    private int makerUserId;
    private String makerEnterDatetime;
    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public ClaimApply(int claimId, int empId, int projectId, int claimTypeId, String claimDate, float claimAmount, String claimRemarks, int claimFinalStatus, String circulatedTo, int delStatus, int isActive, int makerUserId, String makerEnterDatetime, int exInt1, int exInt2, int exInt3, String exVar1, String exVar2, String exVar3) {
        this.claimId = claimId;
        this.empId = empId;
        this.projectId = projectId;
        this.claimTypeId = claimTypeId;
        this.claimDate = claimDate;
        this.claimAmount = claimAmount;
        this.claimRemarks = claimRemarks;
        this.claimFinalStatus = claimFinalStatus;
        this.circulatedTo = circulatedTo;
        this.delStatus = delStatus;
        this.isActive = isActive;
        this.makerUserId = makerUserId;
        this.makerEnterDatetime = makerEnterDatetime;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(int claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public float getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(float claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getClaimRemarks() {
        return claimRemarks;
    }

    public void setClaimRemarks(String claimRemarks) {
        this.claimRemarks = claimRemarks;
    }

    public int getClaimFinalStatus() {
        return claimFinalStatus;
    }

    public void setClaimFinalStatus(int claimFinalStatus) {
        this.claimFinalStatus = claimFinalStatus;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getMakerUserId() {
        return makerUserId;
    }

    public void setMakerUserId(int makerUserId) {
        this.makerUserId = makerUserId;
    }

    public String getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(String makerEnterDatetime) {
        this.makerEnterDatetime = makerEnterDatetime;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public int getExInt3() {
        return exInt3;
    }

    public void setExInt3(int exInt3) {
        this.exInt3 = exInt3;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    public String getExVar3() {
        return exVar3;
    }

    public void setExVar3(String exVar3) {
        this.exVar3 = exVar3;
    }

    @Override
    public String toString() {
        return "ClaimApply{" +
                "claimId=" + claimId +
                ", empId=" + empId +
                ", projectId=" + projectId +
                ", claimTypeId=" + claimTypeId +
                ", claimDate='" + claimDate + '\'' +
                ", claimAmount=" + claimAmount +
                ", claimRemarks='" + claimRemarks + '\'' +
                ", claimFinalStatus=" + claimFinalStatus +
                ", circulatedTo='" + circulatedTo + '\'' +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", makerUserId=" + makerUserId +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
