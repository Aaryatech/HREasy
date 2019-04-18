package com.ats.hreasy.model;

public class LeaveHistoryTemp {
    String leaveType;
    String dayType;
    String date;
    String dayes;
    String status;

    public LeaveHistoryTemp(String leaveType, String dayType, String date, String dayes, String status) {
        this.leaveType = leaveType;
        this.dayType = dayType;
        this.date = date;
        this.dayes = dayes;
        this.status = status;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayes() {
        return dayes;
    }

    public void setDayes(String dayes) {
        this.dayes = dayes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeaveHistory{" +
                "leaveType='" + leaveType + '\'' +
                ", dayType='" + dayType + '\'' +
                ", date='" + date + '\'' +
                ", dayes='" + dayes + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
