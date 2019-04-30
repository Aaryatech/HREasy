package com.ats.hreasy.model;

public class LeaveWeeklyOffCount {

    private Integer leavecount;
    private Integer holidaycount;

    public Integer getLeavecount() {
        return leavecount;
    }

    public void setLeavecount(Integer leavecount) {
        this.leavecount = leavecount;
    }

    public Integer getHolidaycount() {
        return holidaycount;
    }

    public void setHolidaycount(Integer holidaycount) {
        this.holidaycount = holidaycount;
    }

    @Override
    public String toString() {
        return "LeaveWeeklyOffCount{" +
                "leavecount=" + leavecount +
                ", holidaycount=" + holidaycount +
                '}';
    }
}
