package com.ats.hreasy.model;

public class LeaveTrailTemp {

    private int id;
    private String empName;
    private String remark;
    private String status;
    private String date;

    public LeaveTrailTemp(int id, String empName, String remark, String status, String date) {
        this.id = id;
        this.empName = empName;
        this.remark = remark;
        this.status = status;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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
        return "LeaveTrailTemp{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", remark='" + remark + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
