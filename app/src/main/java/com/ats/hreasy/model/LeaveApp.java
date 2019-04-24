package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveApp {
    @SerializedName("leaveId")
    @Expose
    private Integer leaveId;
    @SerializedName("calYrId")
    @Expose
    private Integer calYrId;
    @SerializedName("leaveTitle")
    @Expose
    private String leaveTitle;
    @SerializedName("empId")
    @Expose
    private Integer empId;
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
    @SerializedName("lvTypeId")
    @Expose
    private Integer lvTypeId;
    @SerializedName("leaveTypeName")
    @Expose
    private String leaveTypeName;
    @SerializedName("leaveDuration")
    @Expose
    private String leaveDuration;
    @SerializedName("leaveFromdt")
    @Expose
    private String leaveFromdt;
    @SerializedName("leaveTodt")
    @Expose
    private String leaveTodt;
    @SerializedName("leaveNumDays")
    @Expose
    private Integer leaveNumDays;
    @SerializedName("leaveEmpReason")
    @Expose
    private String leaveEmpReason;
    @SerializedName("circulatedTo")
    @Expose
    private String circulatedTo;

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

    public String getLeaveTitle() {
        return leaveTitle;
    }

    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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

    public Integer getLvTypeId() {
        return lvTypeId;
    }

    public void setLvTypeId(Integer lvTypeId) {
        this.lvTypeId = lvTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
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

    public Integer getLeaveNumDays() {
        return leaveNumDays;
    }

    public void setLeaveNumDays(Integer leaveNumDays) {
        this.leaveNumDays = leaveNumDays;
    }

    public String getLeaveEmpReason() {
        return leaveEmpReason;
    }

    public void setLeaveEmpReason(String leaveEmpReason) {
        this.leaveEmpReason = leaveEmpReason;
    }

    public String getCirculatedTo() {
        return circulatedTo;
    }

    public void setCirculatedTo(String circulatedTo) {
        this.circulatedTo = circulatedTo;
    }

    @Override
    public String toString() {
        return "LeaveApp{" +
                "leaveId=" + leaveId +
                ", calYrId=" + calYrId +
                ", leaveTitle='" + leaveTitle + '\'' +
                ", empId=" + empId +
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empCode='" + empCode + '\'' +
                ", lvTypeId=" + lvTypeId +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                ", leaveDuration='" + leaveDuration + '\'' +
                ", leaveFromdt='" + leaveFromdt + '\'' +
                ", leaveTodt='" + leaveTodt + '\'' +
                ", leaveNumDays=" + leaveNumDays +
                ", leaveEmpReason='" + leaveEmpReason + '\'' +
                ", circulatedTo='" + circulatedTo + '\'' +
                '}';
    }
}
