package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClaimHistoryModel {

    @SerializedName("claimId")
    @Expose
    private int claimId;
    @SerializedName("empId")
    @Expose
    private int empId;
    @SerializedName("projectId")
    @Expose
    private int projectId;
    @SerializedName("claimTypeId")
    @Expose
    private int claimTypeId;
    @SerializedName("claimDate")
    @Expose
    private String claimDate;
    @SerializedName("claimAmount")
    @Expose
    private int claimAmount;
    @SerializedName("claimRemarks")
    @Expose
    private String claimRemarks;
    @SerializedName("claimFinalStatus")
    @Expose
    private int claimFinalStatus;
    @SerializedName("circulatedTo")
    @Expose
    private String circulatedTo;
    @SerializedName("delStatus")
    @Expose
    private int delStatus;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("makerUserId")
    @Expose
    private int makerUserId;
    @SerializedName("makerEnterDatetime")
    @Expose
    private String makerEnterDatetime;
    @SerializedName("exInt1")
    @Expose
    private int exInt1;
    @SerializedName("exInt2")
    @Expose
    private int exInt2;
    @SerializedName("exInt3")
    @Expose
    private int exInt3;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;
    @SerializedName("empFname")
    @Expose
    private String empFname;
    @SerializedName("empMname")
    @Expose
    private String empMname;
    @SerializedName("empSname")
    @Expose
    private String empSname;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("empPhoto")
    @Expose
    private String empPhoto;
    @SerializedName("empDeptName")
    @Expose
    private String empDeptName;
    @SerializedName("projectTypeTitle")
    @Expose
    private String projectTypeTitle;
    @SerializedName("claimTypeTitle")
    @Expose
    private String claimTypeTitle;
    @SerializedName("getClaimTrailStatus")
    @Expose
    private List<ClaimTrailstatus> getClaimTrailStatus = null;

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

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
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

    public String getEmpFname() {
        return empFname;
    }

    public void setEmpFname(String empFname) {
        this.empFname = empFname;
    }

    public String getEmpMname() {
        return empMname;
    }

    public void setEmpMname(String empMname) {
        this.empMname = empMname;
    }

    public String getEmpSname() {
        return empSname;
    }

    public void setEmpSname(String empSname) {
        this.empSname = empSname;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getEmpDeptName() {
        return empDeptName;
    }

    public void setEmpDeptName(String empDeptName) {
        this.empDeptName = empDeptName;
    }

    public String getProjectTypeTitle() {
        return projectTypeTitle;
    }

    public void setProjectTypeTitle(String projectTypeTitle) {
        this.projectTypeTitle = projectTypeTitle;
    }

    public String getClaimTypeTitle() {
        return claimTypeTitle;
    }

    public void setClaimTypeTitle(String claimTypeTitle) {
        this.claimTypeTitle = claimTypeTitle;
    }

    public List<ClaimTrailstatus> getGetClaimTrailStatus() {
        return getClaimTrailStatus;
    }

    public void setGetClaimTrailStatus(List<ClaimTrailstatus> getClaimTrailStatus) {
        this.getClaimTrailStatus = getClaimTrailStatus;
    }



}
