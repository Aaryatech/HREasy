package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimType {

    @SerializedName("claimTypeId")
    @Expose
    private Integer claimTypeId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("claimTypeTitle")
    @Expose
    private String claimTypeTitle;
    @SerializedName("claimTypeTitleShort")
    @Expose
    private String claimTypeTitleShort;
    @SerializedName("claimTypeRemarks")
    @Expose
    private String claimTypeRemarks;
    @SerializedName("claimTypeColor")
    @Expose
    private String claimTypeColor;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("makerUserId")
    @Expose
    private Integer makerUserId;
    @SerializedName("makerEnterDatetime")
    @Expose
    private String makerEnterDatetime;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exInt3")
    @Expose
    private Integer exInt3;
    @SerializedName("exVar1")
    @Expose
    private Object exVar1;
    @SerializedName("exVar2")
    @Expose
    private Object exVar2;
    @SerializedName("exVar3")
    @Expose
    private Object exVar3;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Integer getClaimTypeId() {
        return claimTypeId;
    }

    public void setClaimTypeId(Integer claimTypeId) {
        this.claimTypeId = claimTypeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getClaimTypeTitle() {
        return claimTypeTitle;
    }

    public void setClaimTypeTitle(String claimTypeTitle) {
        this.claimTypeTitle = claimTypeTitle;
    }

    public String getClaimTypeTitleShort() {
        return claimTypeTitleShort;
    }

    public void setClaimTypeTitleShort(String claimTypeTitleShort) {
        this.claimTypeTitleShort = claimTypeTitleShort;
    }

    public String getClaimTypeRemarks() {
        return claimTypeRemarks;
    }

    public void setClaimTypeRemarks(String claimTypeRemarks) {
        this.claimTypeRemarks = claimTypeRemarks;
    }

    public String getClaimTypeColor() {
        return claimTypeColor;
    }

    public void setClaimTypeColor(String claimTypeColor) {
        this.claimTypeColor = claimTypeColor;
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
        return "ClaimType{" +
                "claimTypeId=" + claimTypeId +
                ", companyId=" + companyId +
                ", claimTypeTitle='" + claimTypeTitle + '\'' +
                ", claimTypeTitleShort='" + claimTypeTitleShort + '\'' +
                ", claimTypeRemarks='" + claimTypeRemarks + '\'' +
                ", claimTypeColor='" + claimTypeColor + '\'' +
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
