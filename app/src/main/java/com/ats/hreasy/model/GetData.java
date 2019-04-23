package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetData {

    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("empCatId")
    @Expose
    private Integer empCatId;
    @SerializedName("empCategory")
    @Expose
    private String empCategory;
    @SerializedName("empTypeId")
    @Expose
    private Integer empTypeId;
    @SerializedName("empType")
    @Expose
    private String empType;
    @SerializedName("empDeptId")
    @Expose
    private Integer empDeptId;
    @SerializedName("empDept")
    @Expose
    private String empDept;
    @SerializedName("empFname")
    @Expose
    private String empFname;
    @SerializedName("empMname")
    @Expose
    private String empMname;
    @SerializedName("empSname")
    @Expose
    private String empSname;
    @SerializedName("empMobile1")
    @Expose
    private String empMobile1;
    @SerializedName("empEmail")
    @Expose
    private String empEmail;
    @SerializedName("empPrevExpYrs")
    @Expose
    private Integer empPrevExpYrs;
    @SerializedName("empRatePerhr")
    @Expose
    private Integer empRatePerhr;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEmpCatId() {
        return empCatId;
    }

    public void setEmpCatId(Integer empCatId) {
        this.empCatId = empCatId;
    }

    public String getEmpCategory() {
        return empCategory;
    }

    public void setEmpCategory(String empCategory) {
        this.empCategory = empCategory;
    }

    public Integer getEmpTypeId() {
        return empTypeId;
    }

    public void setEmpTypeId(Integer empTypeId) {
        this.empTypeId = empTypeId;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public Integer getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(Integer empDeptId) {
        this.empDeptId = empDeptId;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
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

    public String getEmpMobile1() {
        return empMobile1;
    }

    public void setEmpMobile1(String empMobile1) {
        this.empMobile1 = empMobile1;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public Integer getEmpPrevExpYrs() {
        return empPrevExpYrs;
    }

    public void setEmpPrevExpYrs(Integer empPrevExpYrs) {
        this.empPrevExpYrs = empPrevExpYrs;
    }

    public Integer getEmpRatePerhr() {
        return empRatePerhr;
    }

    public void setEmpRatePerhr(Integer empRatePerhr) {
        this.empRatePerhr = empRatePerhr;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    @Override
    public String toString() {
        return "GetData{" +
                "empId=" + empId +
                ", empCode='" + empCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", empCatId=" + empCatId +
                ", empCategory='" + empCategory + '\'' +
                ", empTypeId=" + empTypeId +
                ", empType='" + empType + '\'' +
                ", empDeptId=" + empDeptId +
                ", empDept='" + empDept + '\'' +
                ", empFname='" + empFname + '\'' +
                ", empMname='" + empMname + '\'' +
                ", empSname='" + empSname + '\'' +
                ", empMobile1='" + empMobile1 + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empPrevExpYrs=" + empPrevExpYrs +
                ", empRatePerhr=" + empRatePerhr +
                ", exVar1='" + exVar1 + '\'' +
                '}';
    }
}
