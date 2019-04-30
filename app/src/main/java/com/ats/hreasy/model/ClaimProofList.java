package com.ats.hreasy.model;

public class ClaimProofList {

    private Integer cpId;
    private Integer claimId;
    private String cpDocPath;
    private String cpDocRemark;
    private Integer delStatus;
    private Integer isActive;
    private Integer makerUserId;
    private String makerEnterDatetime;
    private Integer exInt1;
    private Integer exInt2;
    private Integer exInt3;
    private Object exVar1;
    private Object exVar2;
    private Object exVar3;
    private Boolean error;

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer cpId) {
        this.cpId = cpId;
    }

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public String getCpDocPath() {
        return cpDocPath;
    }

    public void setCpDocPath(String cpDocPath) {
        this.cpDocPath = cpDocPath;
    }

    public String getCpDocRemark() {
        return cpDocRemark;
    }

    public void setCpDocRemark(String cpDocRemark) {
        this.cpDocRemark = cpDocRemark;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getMakerUserId() {
        return makerUserId;
    }

    public void setMakerUserId(Integer makerUserId) {
        this.makerUserId = makerUserId;
    }

    public String getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(String makerEnterDatetime) {
        this.makerEnterDatetime = makerEnterDatetime;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public Integer getExInt3() {
        return exInt3;
    }

    public void setExInt3(Integer exInt3) {
        this.exInt3 = exInt3;
    }

    public Object getExVar1() {
        return exVar1;
    }

    public void setExVar1(Object exVar1) {
        this.exVar1 = exVar1;
    }

    public Object getExVar2() {
        return exVar2;
    }

    public void setExVar2(Object exVar2) {
        this.exVar2 = exVar2;
    }

    public Object getExVar3() {
        return exVar3;
    }

    public void setExVar3(Object exVar3) {
        this.exVar3 = exVar3;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ClaimProofList{" +
                "cpId=" + cpId +
                ", claimId=" + claimId +
                ", cpDocPath='" + cpDocPath + '\'' +
                ", cpDocRemark='" + cpDocRemark + '\'' +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", makerUserId=" + makerUserId +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1=" + exVar1 +
                ", exVar2=" + exVar2 +
                ", exVar3=" + exVar3 +
                ", error=" + error +
                '}';
    }
}
