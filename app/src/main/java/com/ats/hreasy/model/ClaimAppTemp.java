package com.ats.hreasy.model;

public class ClaimAppTemp {

    private int id;
    private String empName;
    private String project;
    private String claimType;
    private String date;
    private Float amount;
    private String remark;

    public ClaimAppTemp(int id, String empName, String project, String claimType, String date, Float amount, String remark) {
        this.id = id;
        this.empName = empName;
        this.project = project;
        this.claimType = claimType;
        this.date = date;
        this.amount = amount;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "ClaimAppTemp{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", project='" + project + '\'' +
                ", claimType='" + claimType + '\'' +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
