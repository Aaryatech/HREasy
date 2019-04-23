package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpType {

    @SerializedName("empTypeId")
    @Expose
    private Integer empTypeId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("empTypeName")
    @Expose
    private String empTypeName;
    @SerializedName("empTypeShortName")
    @Expose
    private String empTypeShortName;
    @SerializedName("compOffRequestAllowed")
    @Expose
    private Integer compOffRequestAllowed;
    @SerializedName("setting1")
    @Expose
    private Integer setting1;
    @SerializedName("setting2")
    @Expose
    private Integer setting2;
    @SerializedName("empTypeRemarks")
    @Expose
    private String empTypeRemarks;
    @SerializedName("empTypeAccess")
    @Expose
    private String empTypeAccess;
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

    public Integer getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(Integer empTypeId) {
        this.empTypeId = empTypeId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEmpTypeName() {
        return empTypeName;
    }

    public void setEmpTypeName(String empTypeName) {
        this.empTypeName = empTypeName;
    }

    public String getEmpTypeShortName() {
        return empTypeShortName;
    }

    public void setEmpTypeShortName(String empTypeShortName) {
        this.empTypeShortName = empTypeShortName;
    }

    public Integer getCompOffRequestAllowed() {
        return compOffRequestAllowed;
    }

    public void setCompOffRequestAllowed(Integer compOffRequestAllowed) {
        this.compOffRequestAllowed = compOffRequestAllowed;
    }

    public Integer getSetting1() {
        return setting1;
    }

    public void setSetting1(Integer setting1) {
        this.setting1 = setting1;
    }

    public Integer getSetting2() {
        return setting2;
    }

    public void setSetting2(Integer setting2) {
        this.setting2 = setting2;
    }

    public String getEmpTypeRemarks() {
        return empTypeRemarks;
    }

    public void setEmpTypeRemarks(String empTypeRemarks) {
        this.empTypeRemarks = empTypeRemarks;
    }

    public String getEmpTypeAccess() {
        return empTypeAccess;
    }

    public void setEmpTypeAccess(String empTypeAccess) {
        this.empTypeAccess = empTypeAccess;
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
        return "EmpType{" +
                "empTypeId=" + empTypeId +
                ", companyId=" + companyId +
                ", empTypeName='" + empTypeName + '\'' +
                ", empTypeShortName='" + empTypeShortName + '\'' +
                ", compOffRequestAllowed=" + compOffRequestAllowed +
                ", setting1=" + setting1 +
                ", setting2=" + setting2 +
                ", empTypeRemarks='" + empTypeRemarks + '\'' +
                ", empTypeAccess='" + empTypeAccess + '\'' +
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
