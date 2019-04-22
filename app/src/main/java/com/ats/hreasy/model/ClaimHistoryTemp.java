package com.ats.hreasy.model;

public class ClaimHistoryTemp {
    String projectType;
    String leaveType;
    String date;
    int amt;
    String remark;
    String status;

    public ClaimHistoryTemp(String projectType, String leaveType, String date, int amt, String remark,String status) {
        this.projectType = projectType;
        this.leaveType = leaveType;
        this.date = date;
        this.amt = amt;
        this.remark = remark;
        this.status = status;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClaimHistoryTemp{" +
                "projectType='" + projectType + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", date='" + date + '\'' +
                ", amt=" + amt +
                ", remark='" + remark + '\'' +
                '}';
    }
}
