package com.ats.hreasy.model;

public class SaveClaimTrail {

    private int claimTrailPkey;
    private int claimId;
    private int empId;
    private String empRemarks;
    private int claimStatus;
    private int makerUserId ;
    private String makerEnterDatetime;
    private int exInt1;
    private int exInt2;
    private int exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public SaveClaimTrail(int claimTrailPkey, int claimId, int empId, String empRemarks, int claimStatus, int makerUserId, String makerEnterDatetime) {
        this.claimTrailPkey = claimTrailPkey;
        this.claimId = claimId;
        this.empId = empId;
        this.empRemarks = empRemarks;
        this.claimStatus = claimStatus;
        this.makerUserId = makerUserId;
        this.makerEnterDatetime = makerEnterDatetime;
    }

    public int getClaimTrailPkey() {
        return claimTrailPkey;
    }

    public void setClaimTrailPkey(int claimTrailPkey) {
        this.claimTrailPkey = claimTrailPkey;
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

    public String getEmpRemarks() {
        return empRemarks;
    }

    public void setEmpRemarks(String empRemarks) {
        this.empRemarks = empRemarks;
    }

    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
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
        return "SaveClaimTrail{" +
                "claimTrailPkey=" + claimTrailPkey +
                ", claimId=" + claimId +
                ", empId=" + empId +
                ", empRemarks='" + empRemarks + '\'' +
                ", claimStatus=" + claimStatus +
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
