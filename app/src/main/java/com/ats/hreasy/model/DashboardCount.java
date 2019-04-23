package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCount {

    @SerializedName("pendingRequest")
    @Expose
    private Integer pendingRequest;
    @SerializedName("myLeave")
    @Expose
    private Integer myLeave;
    @SerializedName("info")
    @Expose
    private Integer info;
    @SerializedName("isAuthorized")
    @Expose
    private Integer isAuthorized;

    public Integer getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(Integer pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

    public Integer getMyLeave() {
        return myLeave;
    }

    public void setMyLeave(Integer myLeave) {
        this.myLeave = myLeave;
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public Integer getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(Integer isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    @Override
    public String toString() {
        return "DashboardCount{" +
                "pendingRequest=" + pendingRequest +
                ", myLeave=" + myLeave +
                ", info=" + info +
                ", isAuthorized=" + isAuthorized +
                '}';
    }
}
