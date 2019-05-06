package com.ats.hreasy.model;

public class MyLeaveTrailData {

    private Integer trailPkey;
    private Integer leaveId;
    private Integer empId;
    private String empRemarks;
    private Integer leaveStatus;
    private String makerEnterDatetime;
    private String empFname;
    private String empMname;
    private String empSname;
    private String empPhoto;
    private String userName;

    public Integer getTrailPkey() {
        return trailPkey;
    }

    public void setTrailPkey(Integer trailPkey) {
        this.trailPkey = trailPkey;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpRemarks() {
        return empRemarks;
    }

    public void setEmpRemarks(String empRemarks) {
        this.empRemarks = empRemarks;
    }

    public Integer getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public String getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(String makerEnterDatetime) {
        this.makerEnterDatetime = makerEnterDatetime;
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

    public String getEmpPhoto() {
        return empPhoto;
    }

    public void setEmpPhoto(String empPhoto) {
        this.empPhoto = empPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "MyLeaveTrailData{" +
                "trailPkey=" + trailPkey +
                ", leaveId=" + leaveId +
                ", empId=" + empId +
                ", empRemarks='" + empRemarks + '\'' +
                ", leaveStatus=" + leaveStatus +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empPhoto='" + empPhoto + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
