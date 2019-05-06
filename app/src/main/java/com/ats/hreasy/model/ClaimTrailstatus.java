package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClaimTrailstatus {

    private int claimTrailPkey;
    private int claimId;
    private int empId;
    private String empRemarks;
    private int claimStatus;
    private String makerEnterDatetime;
    private String empFname;
    private String empMname;
    private String empSname;
    private String empPhoto;
    private String userName;

    public int getClaimTrailPkey() {
        return claimTrailPkey;
    }

    public void setClaimTrailPkey(int claimTrailPkey) {
        this.claimTrailPkey = claimTrailPkey;
    }

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

    public String getEmpRemarks() {
        return empRemarks;
    }

    public void setEmpRemarks(String empRemarks) {
        this.empRemarks = empRemarks;
    }

    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
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
        return "ClaimTrailstatus{" +
                "claimTrailPkey=" + claimTrailPkey +
                ", claimId=" + claimId +
                ", empId=" + empId +
                ", empRemarks='" + empRemarks + '\'' +
                ", claimStatus=" + claimStatus +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empPhoto='" + empPhoto + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
