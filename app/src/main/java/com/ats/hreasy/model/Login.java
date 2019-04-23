package com.ats.hreasy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("locId")
    @Expose
    private String locId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("errMsg")
    @Expose
    private String errMsg;
//    @SerializedName("getData")
//    @Expose
//    private GetData getData;
//    @SerializedName("empType")
//    @Expose
//    private EmpType empType;

    List<GetData> getData ;
    List<EmpType> empType ;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<GetData> getGetData() {
        return getData;
    }

    public void setGetData(List<GetData> getData) {
        this.getData = getData;
    }

    public List<EmpType> getEmpType() {
        return empType;
    }

    public void setEmpType(List<EmpType> empType) {
        this.empType = empType;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userId=" + userId +
                ", locId='" + locId + '\'' +
                ", userName='" + userName + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", getData=" + getData +
                ", empType=" + empType +
                '}';
    }
}
