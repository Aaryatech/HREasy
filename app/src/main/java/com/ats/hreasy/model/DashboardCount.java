package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCount {

    private Integer pendingRequest;
    private Integer myLeave;
    private Integer info;
    private Integer isAuthorized;
    private Integer pendingClaim;
    private Integer myClaim;
    private Integer infoClaim;
    private Integer isAuthorizedClaim;

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

    public Integer getPendingClaim() {
        return pendingClaim;
    }

    public void setPendingClaim(Integer pendingClaim) {
        this.pendingClaim = pendingClaim;
    }

    public Integer getMyClaim() {
        return myClaim;
    }

    public void setMyClaim(Integer myClaim) {
        this.myClaim = myClaim;
    }

    public Integer getInfoClaim() {
        return infoClaim;
    }

    public void setInfoClaim(Integer infoClaim) {
        this.infoClaim = infoClaim;
    }

    public Integer getIsAuthorizedClaim() {
        return isAuthorizedClaim;
    }

    public void setIsAuthorizedClaim(Integer isAuthorizedClaim) {
        this.isAuthorizedClaim = isAuthorizedClaim;
    }

    @Override
    public String toString() {
        return "DashboardCount{" +
                "pendingRequest=" + pendingRequest +
                ", myLeave=" + myLeave +
                ", info=" + info +
                ", isAuthorized=" + isAuthorized +
                ", pendingClaim=" + pendingClaim +
                ", myClaim=" + myClaim +
                ", infoClaim=" + infoClaim +
                ", isAuthorizedClaim=" + isAuthorizedClaim +
                '}';
    }
}
