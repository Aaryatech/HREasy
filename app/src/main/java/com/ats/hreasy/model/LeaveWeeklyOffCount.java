package com.ats.hreasy.model;

public class LeaveWeeklyOffCount {

    private float leavecount;
    private float holidaycount;

    public float getLeavecount() {
        return leavecount;
    }

    public void setLeavecount(float leavecount) {
        this.leavecount = leavecount;
    }

    public float getHolidaycount() {
        return holidaycount;
    }

    public void setHolidaycount(float holidaycount) {
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
