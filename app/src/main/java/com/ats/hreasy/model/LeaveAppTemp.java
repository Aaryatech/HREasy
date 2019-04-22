package com.ats.hreasy.model;

public class LeaveAppTemp {

    private int id;
    private String name;
    private String type;
    private String fromDate;
    private String toDate;
    private String days;
    private String remark;

    public LeaveAppTemp(int id, String name, String type, String fromDate, String toDate, String days, String remark) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.days = days;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "LeaveAppTemp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", days='" + days + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
