package com.ats.hreasy.model;

public class CurrentYearModel {

    private Integer calYrId;
    private String calYrFromDate;
    private String calYrToDate;
    private Integer isCurrent;
    private Integer exInt1;
    private Integer exInt2;
    private Integer exInt3;
    private String exVar1;
    private String exVar2;
    private String exVar3;

    public Integer getCalYrId() {
        return calYrId;
    }

    public void setCalYrId(Integer calYrId) {
        this.calYrId = calYrId;
    }

    public String getCalYrFromDate() {
        return calYrFromDate;
    }

    public void setCalYrFromDate(String calYrFromDate) {
        this.calYrFromDate = calYrFromDate;
    }

    public String getCalYrToDate() {
        return calYrToDate;
    }

    public void setCalYrToDate(String calYrToDate) {
        this.calYrToDate = calYrToDate;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
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

    @Override
    public String toString() {
        return "CurrentYearModel{" +
                "calYrId=" + calYrId +
                ", calYrFromDate='" + calYrFromDate + '\'' +
                ", calYrToDate='" + calYrToDate + '\'' +
                ", isCurrent=" + isCurrent +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
