package com.ats.hreasy.model;

public class BalanceLeaveModel {

    private Integer lvTypeId;
    private String lvTitleShort;
    private Integer lvsAllotedLeaves;
    private Integer balLeave;
    private Integer aplliedLeaeve;
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
                ", lvsAllotedLeaves=" + lvsAllotedLeaves +
                ", balLeave=" + balLeave +
                ", aplliedLeaeve=" + aplliedLeaeve +
                ", sactionLeave=" + sactionLeave +
                '}';
    }
}
