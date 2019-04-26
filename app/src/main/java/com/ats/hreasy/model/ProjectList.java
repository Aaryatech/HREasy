package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectList {
    @SerializedName("projectId")
    @Expose
    private Integer projectId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("locId")
    @Expose
    private Integer locId;
    @SerializedName("projectTypeId")
    @Expose
    private Integer projectTypeId;
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("projectTitle")
    @Expose
    private String projectTitle;
    @SerializedName("projectDesc")
    @Expose
    private String projectDesc;
    @SerializedName("projectEstStartdt")
    @Expose
    private String projectEstStartdt;
    @SerializedName("projectEstEnddt")
    @Expose
    private String projectEstEnddt;
    @SerializedName("projectEstManhrs")
    @Expose
    private Integer projectEstManhrs;
    @SerializedName("projectEstBudget")
    @Expose
    private Integer projectEstBudget;
    @SerializedName("projectActStartdt")
    @Expose
    private String projectActStartdt;
    @SerializedName("projectActenddt")
    @Expose
    private String projectActenddt;
    @SerializedName("projectStatus")
    @Expose
    private String projectStatus;
    @SerializedName("projectCompletion")
    @Expose
    private Integer projectCompletion;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("makerUserId")
    @Expose
    private Integer makerUserId;
    @SerializedName("makerEnterDatetime")
    @Expose
    private String makerEnterDatetime;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exInt3")
    @Expose
    private Integer exInt3;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getProjectEstStartdt() {
        return projectEstStartdt;
    }

    public void setProjectEstStartdt(String projectEstStartdt) {
        this.projectEstStartdt = projectEstStartdt;
    }

    public String getProjectEstEnddt() {
        return projectEstEnddt;
    }

    public void setProjectEstEnddt(String projectEstEnddt) {
        this.projectEstEnddt = projectEstEnddt;
    }

    public Integer getProjectEstManhrs() {
        return projectEstManhrs;
    }

    public void setProjectEstManhrs(Integer projectEstManhrs) {
        this.projectEstManhrs = projectEstManhrs;
    }

    public Integer getProjectEstBudget() {
        return projectEstBudget;
    }

    public void setProjectEstBudget(Integer projectEstBudget) {
        this.projectEstBudget = projectEstBudget;
    }

    public String getProjectActStartdt() {
        return projectActStartdt;
    }

    public void setProjectActStartdt(String projectActStartdt) {
        this.projectActStartdt = projectActStartdt;
    }

    public String getProjectActenddt() {
        return projectActenddt;
    }

    public void setProjectActenddt(String projectActenddt) {
        this.projectActenddt = projectActenddt;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getProjectCompletion() {
        return projectCompletion;
    }

    public void setProjectCompletion(Integer projectCompletion) {
        this.projectCompletion = projectCompletion;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getMakerUserId() {
        return makerUserId;
    }

    public void setMakerUserId(Integer makerUserId) {
        this.makerUserId = makerUserId;
    }

    public String getMakerEnterDatetime() {
        return makerEnterDatetime;
    }

    public void setMakerEnterDatetime(String makerEnterDatetime) {
        this.makerEnterDatetime = makerEnterDatetime;
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
        return "ProjectList{" +
                "projectId=" + projectId +
                ", companyId=" + companyId +
                ", locId=" + locId +
                ", projectTypeId=" + projectTypeId +
                ", custId=" + custId +
                ", projectTitle='" + projectTitle + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", projectEstStartdt='" + projectEstStartdt + '\'' +
                ", projectEstEnddt='" + projectEstEnddt + '\'' +
                ", projectEstManhrs=" + projectEstManhrs +
                ", projectEstBudget=" + projectEstBudget +
                ", projectActStartdt='" + projectActStartdt + '\'' +
                ", projectActenddt='" + projectActenddt + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", projectCompletion=" + projectCompletion +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", makerUserId=" + makerUserId +
                ", makerEnterDatetime='" + makerEnterDatetime + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
