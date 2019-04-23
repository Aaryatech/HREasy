package com.ats.hreasy.model;

public class ClaimPendingTemp {
    String project;
    String claimType;
    String date;
    int amt;
    String status;

    public ClaimPendingTemp(String project, String claimType, String date, int amt, String status) {
        this.project = project;
        this.claimType = claimType;
        this.date = date;
        this.amt = amt;
        this.status = status;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClaimPendingTemp{" +
                "project='" + project + '\'' +
                ", claimType='" + claimType + '\'' +
                ", date='" + date + '\'' +
                ", amt=" + amt +
                ", status='" + status + '\'' +
                '}';
    }
}
