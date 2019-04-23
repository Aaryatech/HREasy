package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("locationIds")
    @Expose
    private Object locationIds;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("userPwd")
    @Expose
    private Object userPwd;
    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empCode")
    @Expose
    private Object empCode;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("empCatId")
    @Expose
    private Integer empCatId;
    @SerializedName("empTypeId")
    @Expose
    private Integer empTypeId;
    @SerializedName("empDeptId")
    @Expose
    private Integer empDeptId;
    @SerializedName("locId")
    @Expose
    private Integer locId;
    @SerializedName("empFname")
    @Expose
    private Object empFname;
    @SerializedName("empMname")
    @Expose
    private Object empMname;
    @SerializedName("empSname")
    @Expose
    private Object empSname;
    @SerializedName("empPhoto")
    @Expose
    private Object empPhoto;
    @SerializedName("empMobile1")
    @Expose
    private Object empMobile1;
    @SerializedName("empMobile2")
    @Expose
    private Object empMobile2;
    @SerializedName("empEmail")
    @Expose
    private Object empEmail;
    @SerializedName("empAddressTemp")
    @Expose
    private Object empAddressTemp;
    @SerializedName("empAddressPerm")
    @Expose
    private Object empAddressPerm;
    @SerializedName("empBloodgrp")
    @Expose
    private Object empBloodgrp;
    @SerializedName("empEmergencyPerson1")
    @Expose
    private Object empEmergencyPerson1;
    @SerializedName("empEmergencyNo1")
    @Expose
    private Object empEmergencyNo1;
    @SerializedName("empEmergencyPerson2")
    @Expose
    private Object empEmergencyPerson2;
    @SerializedName("empEmergencyNo2")
    @Expose
    private Object empEmergencyNo2;
    @SerializedName("empRatePerhr")
    @Expose
    private Integer empRatePerhr;
    @SerializedName("empJoiningDate")
    @Expose
    private Object empJoiningDate;
    @SerializedName("empPrevExpYrs")
    @Expose
    private Integer empPrevExpYrs;
    @SerializedName("empPrevExpMonths")
    @Expose
    private Integer empPrevExpMonths;
    @SerializedName("empLeavingDate")
    @Expose
    private Object empLeavingDate;
    @SerializedName("empLeavingReason")
    @Expose
    private Object empLeavingReason;
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
    private Object makerEnterDatetime;
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
    @SerializedName("companyName")
    @Expose
    private Object companyName;
    @SerializedName("empCatName")
    @Expose
    private Object empCatName;
    @SerializedName("empTypeName")
    @Expose
    private Object empTypeName;
    @SerializedName("empTypeAccess")
    @Expose
    private Object empTypeAccess;
    @SerializedName("empDeptName")
    @Expose
    private Object empDeptName;
    @SerializedName("locName")
    @Expose
    private Object locName;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(Object locationIds) {
        this.locationIds = locationIds;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(Object userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Object getEmpCode() {
        return empCode;
    }

    public void setEmpCode(Object empCode) {
        this.empCode = empCode;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmpCatId() {
        return empCatId;
    }

    public void setEmpCatId(Integer empCatId) {
        this.empCatId = empCatId;
    }

    public Integer getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(Integer empTypeId) {
        this.empTypeId = empTypeId;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Object getEmpFname() {
        return empFname;
    }

    public void setEmpFname(Object empFname) {
        this.empFname = empFname;
    }

    public Object getEmpMname() {
        return empMname;
    }

    public void setEmpMname(Object empMname) {
        this.empMname = empMname;
    }

    public Object getEmpSname() {
        return empSname;
    }

    public void setEmpSname(Object empSname) {
        this.empSname = empSname;
    }

    public Object getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(Object empPhoto) {
        this.empPhoto = empPhoto;
    }

    public Object getEmpMobile1() {
        return empMobile1;
    }

    public void setEmpMobile1(Object empMobile1) {
        this.empMobile1 = empMobile1;
    }

    public Object getEmpMobile2() {
        return empMobile2;
    }

    public void setEmpMobile2(Object empMobile2) {
        this.empMobile2 = empMobile2;
    }

    public Object getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(Object empEmail) {
        this.empEmail = empEmail;
    }

    public Object getEmpAddressTemp() {
        return empAddressTemp;
    }

    public void setEmpAddressTemp(Object empAddressTemp) {
        this.empAddressTemp = empAddressTemp;
    }

    public Object getEmpAddressPerm() {
        return empAddressPerm;
    }

    public void setEmpAddressPerm(Object empAddressPerm) {
        this.empAddressPerm = empAddressPerm;
    }

    public Object getEmpBloodgrp() {
        return empBloodgrp;
    }

    public void setEmpBloodgrp(Object empBloodgrp) {
        this.empBloodgrp = empBloodgrp;
    }

    public Object getEmpEmergencyPerson1() {
        return empEmergencyPerson1;
    }

    public void setEmpEmergencyPerson1(Object empEmergencyPerson1) {
        this.empEmergencyPerson1 = empEmergencyPerson1;
    }

    public Object getEmpEmergencyNo1() {
        return empEmergencyNo1;
    }

    public void setEmpEmergencyNo1(Object empEmergencyNo1) {
        this.empEmergencyNo1 = empEmergencyNo1;
    }

    public Object getEmpEmergencyPerson2() {
        return empEmergencyPerson2;
    }

    public void setEmpEmergencyPerson2(Object empEmergencyPerson2) {
        this.empEmergencyPerson2 = empEmergencyPerson2;
    }

    public Object getEmpEmergencyNo2() {
        return empEmergencyNo2;
    }

    public void setEmpEmergencyNo2(Object empEmergencyNo2) {
        this.empEmergencyNo2 = empEmergencyNo2;
    }

    public Integer getEmpRatePerhr() {
        return empRatePerhr;
    }

    public void setEmpRatePerhr(Integer empRatePerhr) {
        this.empRatePerhr = empRatePerhr;
    }

    public Object getEmpJoiningDate() {
        return empJoiningDate;
    }

    public void setEmpJoiningDate(Object empJoiningDate) {
        this.empJoiningDate = empJoiningDate;
    }

    public Integer getEmpPrevExpYrs() {
        return empPrevExpYrs;
    }

    public void setEmpPrevExpYrs(Integer empPrevExpYrs) {
        this.empPrevExpYrs = empPrevExpYrs;
    }

    public Integer getEmpPrevExpMonths() {
        return empPrevExpMonths;
    }

    public void setEmpPrevExpMonths(Integer empPrevExpMonths) {
        this.empPrevExpMonths = empPrevExpMonths;
    }

    public Object getEmpLeavingDate() {
        return empLeavingDate;
    }

    public void setEmpLeavingDate(Object empLeavingDate) {
        this.empLeavingDate = empLeavingDate;
    }

    public Object getEmpLeavingReason() {
        return empLeavingReason;
    }

    public void setEmpLeavingReason(Object empLeavingReason) {
        this.empLeavingReason = empLeavingReason;
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

    public Object getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(Object makerEnterDatetime) {
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

    public Object getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Object companyName) {
        this.companyName = companyName;
    }

    public Object getEmpCatName() {
        return empCatName;
    }

    public void setEmpCatName(Object empCatName) {
        this.empCatName = empCatName;
    }

    public Object getEmpTypeName() {
        return empTypeName;
    }

    public void setEmpTypeName(Object empTypeName) {
        this.empTypeName = empTypeName;
    }

    public Object getEmpTypeAccess() {
        return empTypeAccess;
    }

    public void setEmpTypeAccess(Object empTypeAccess) {
        this.empTypeAccess = empTypeAccess;
    }

    public Object getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(Object empDeptName) {
        this.empDeptName = empDeptName;
    }

    public Object getLocName() {
        return locName;
    }

    public void setLocName(Object locName) {
        this.locName = locName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userId=" + userId +
                ", locationIds=" + locationIds +
                ", userName=" + userName +
                ", userPwd=" + userPwd +
                ", empId=" + empId +
                ", empCode=" + empCode +
                ", companyId=" + companyId +
                ", empCatId=" + empCatId +
                ", empTypeId=" + empTypeId +
                ", empDeptId=" + empDeptId +
                ", locId=" + locId +
                ", empFname=" + empFname +
                ", empMname=" + empMname +
                ", empSname=" + empSname +
                ", empPhoto=" + empPhoto +
                ", empMobile1=" + empMobile1 +
                ", empMobile2=" + empMobile2 +
                ", empEmail=" + empEmail +
                ", empAddressTemp=" + empAddressTemp +
                ", empAddressPerm=" + empAddressPerm +
                ", empBloodgrp=" + empBloodgrp +
                ", empEmergencyPerson1=" + empEmergencyPerson1 +
                ", empEmergencyNo1=" + empEmergencyNo1 +
                ", empEmergencyPerson2=" + empEmergencyPerson2 +
                ", empEmergencyNo2=" + empEmergencyNo2 +
                ", empRatePerhr=" + empRatePerhr +
                ", empJoiningDate=" + empJoiningDate +
                ", empPrevExpYrs=" + empPrevExpYrs +
                ", empPrevExpMonths=" + empPrevExpMonths +
                ", empLeavingDate=" + empLeavingDate +
                ", empLeavingReason=" + empLeavingReason +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", makerUserId=" + makerUserId +
                ", makerEnterDatetime=" + makerEnterDatetime +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1=" + exVar1 +
                ", exVar2=" + exVar2 +
                ", exVar3=" + exVar3 +
                ", companyName=" + companyName +
                ", empCatName=" + empCatName +
                ", empTypeName=" + empTypeName +
                ", empTypeAccess=" + empTypeAccess +
                ", empDeptName=" + empDeptName +
                ", locName=" + locName +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}
