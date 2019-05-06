package com.ats.hreasy.model;

import java.util.List;

public class MyLeaveData {

    private Integer leaveId;
    private Integer calYrId;
    private Integer empId;
    private Integer lvTypeId;
    private String leaveDuration;
    private String leaveFromdt;
    private String leaveTodt;
    private float leaveNumDays;
    private String leaveEmpReason;
    private Integer finalStatus;
    private String circulatedTo;
    private Integer delStatus;
    private Integer isActive;
    private Integer makerUserId;
    private String makerEnterDatetime;
    private Integer exInt1;
    private Integer exInt2;
    private Integer exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;
    private String empFname;
    private String empMname;
    private String empSname;
    private String empCode;
    private String empPhoto;
    private String empDeptName;
    private String lvTitle;
    private List<MyLeaveTrailData> getLeaveStatusList = null;


    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getCalYrId() {
        return calYrId;
    }

    public void setCalYrId(Integer calYrId) {
        this.calYrId = calYrId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getLvTypeId() {
        return lvTypeId;
    }

    public void setLvTypeId(Integer lvTypeId) {
        this.lvTypeId = lvTypeId;
    }

    public String getLeaveDuration() {
        return leaveDuration;
    }

    public void setLeaveDuration(String leaveDuration) {
        this.leaveDuration = leaveDuration;
    }

    public String getLeaveFromdt() {
        return leaveFromdt;
    }

    public void setLeaveFromdt(String leaveFromdt) {
        this.leaveFromdt = leaveFromdt;
    }

    public String getLeaveTodt() {
        return leaveTodt;
    }

    public void setLeaveTodt(String leaveTodt) {
        this.leaveTodt = leaveTodt;
    }

    public float getLeaveNumDays() {
        return leaveNumDays;
    }

    public void setLeaveNumDays(float leaveNumDays) {
        this.leaveNumDays = leaveNumDays;
    }

    public String getLeaveEmpReason() {
        return leaveEmpReason;
    }

    public void setLeaveEmpReason(String leaveEmpReason) {
        this.leaveEmpReason = leaveEmpReason;
    }

    public Integer getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(Integer finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
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

    public String getLvTitle() {
        return lvTitle;
    }

    public void setLvTitle(String lvTitle) {
        this.lvTitle = lvTitle;
    }

    public List<MyLeaveTrailData> getGetLeaveStatusList() {
        return getLeaveStatusList;
    }

    public void setGetLeaveStatusList(List<MyLeaveTrailData> getLeaveStatusList) {
        this.getLeaveStatusList = getLeaveStatusList;
    }

    @Override
    public String toString() {
        return "MyLeaveData{" +
                "leaveId=" + leaveId +
                ", calYrId=" + calYrId +
                ", empId=" + empId +
                ", lvTypeId=" + lvTypeId +
                ", leaveDuration='" + leaveDuration + '\'' +
                ", leaveFromdt='" + leaveFromdt + '\'' +
                ", leaveTodt='" + leaveTodt + '\'' +
                ", leaveNumDays=" + leaveNumDays +
                ", leaveEmpReason='" + leaveEmpReason + '\'' +
                ", finalStatus=" + finalStatus +
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
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empPhoto='" + empPhoto + '\'' +
                ", empDeptName='" + empDeptName + '\'' +
                ", lvTitle='" + lvTitle + '\'' +
                ", getLeaveStatusList=" + getLeaveStatusList +
                '}';
    }
}
