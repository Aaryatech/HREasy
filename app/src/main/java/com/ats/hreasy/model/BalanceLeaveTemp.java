package com.ats.hreasy.model;

public class BalanceLeaveTemp {
    String leaveName;
    int count;

    public BalanceLeaveTemp(String leaveName, int count) {
        this.leaveName = leaveName;
        this.count = count;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BalanceLeaveTemp{" +
                "leaveName='" + leaveName + '\'' +
                ", count=" + count +
                '}';
    }
}
