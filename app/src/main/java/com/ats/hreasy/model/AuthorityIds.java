package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorityIds {

    @SerializedName("empId")
    @Expose
    private Integer empId;
    @SerializedName("iniAuthEmpId")
    @Expose
    private Integer iniAuthEmpId;
    @SerializedName("finAuthEmpId")
    @Expose
    private Integer finAuthEmpId;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getIniAuthEmpId() {
        return iniAuthEmpId;
    }

    public void setIniAuthEmpId(Integer iniAuthEmpId) {
        this.iniAuthEmpId = iniAuthEmpId;
    }

    public Integer getFinAuthEmpId() {
        return finAuthEmpId;
    }

    public void setFinAuthEmpId(Integer finAuthEmpId) {
        this.finAuthEmpId = finAuthEmpId;
    }

    @Override
    public String toString() {
        return "AuthorityIds{" +
                "empId=" + empId +
                ", iniAuthEmpId=" + iniAuthEmpId +
                ", finAuthEmpId=" + finAuthEmpId +
                '}';
    }
}
