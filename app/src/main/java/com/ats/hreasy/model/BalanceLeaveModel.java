package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceLeaveModel {

    @SerializedName("lvTypeId")
    @Expose
    private Integer lvTypeId;
    @SerializedName("lvTitleShort")
    @Expose
    private String lvTitleShort;
    @SerializedName("lvTitle")
    @Expose
    private String lvTitle;
    @SerializedName("lvsAllotedLeaves")
    @Expose
    private Integer lvsAllotedLeaves;
    @SerializedName("balLeave")
    @Expose
    private Integer balLeave;
    @SerializedName("aplliedLeaeve")
    @Expose
    private Integer aplliedLeaeve;
    @SerializedName("sactionLeave")
    @Expose
    private Integer sactionLeave;

    public Integer getLvTypeId() {
        return lvTypeId;
    }

    public void setLvTypeId(Integer lvTypeId) {
        this.lvTypeId = lvTypeId;
    }

    public String getLvTitleShort() {
        return lvTitleShort;
    }

    public void setLvTitleShort(String lvTitleShort) {
        this.lvTitleShort = lvTitleShort;
    }

    public String getLvTitle() {
        return lvTitle;
    }

    public void setLvTitle(String lvTitle) {
        this.lvTitle = lvTitle;
    }

    public Integer getLvsAllotedLeaves() {
        return lvsAllotedLeaves;
    }

    public void setLvsAllotedLeaves(Integer lvsAllotedLeaves) {
        this.lvsAllotedLeaves = lvsAllotedLeaves;
    }

    public Integer getBalLeave() {
        return balLeave;
    }

    public void setBalLeave(Integer balLeave) {
        this.balLeave = balLeave;
    }

    public Integer getAplliedLeaeve() {
        return aplliedLeaeve;
    }

    public void setAplliedLeaeve(Integer aplliedLeaeve) {
        this.aplliedLeaeve = aplliedLeaeve;
    }

    public Integer getSactionLeave() {
        return sactionLeave;
    }

    public void setSactionLeave(Integer sactionLeave) {
        this.sactionLeave = sactionLeave;
    }

    @Override
    public String toString() {
        return "BalanceLeaveModel{" +
                "lvTypeId=" + lvTypeId +
                ", lvTitleShort='" + lvTitleShort + '\'' +
                ", lvTitle='" + lvTitle + '\'' +
                ", lvsAllotedLeaves=" + lvsAllotedLeaves +
                ", balLeave=" + balLeave +
                ", aplliedLeaeve=" + aplliedLeaeve +
                ", sactionLeave=" + sactionLeave +
                '}';
    }
}
