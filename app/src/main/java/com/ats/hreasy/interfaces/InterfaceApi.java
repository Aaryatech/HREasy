package com.ats.hreasy.interfaces;

import com.ats.hreasy.model.DashboardCount;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.LeaveEmployeeModel;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("login")
    Call<Login> doLogin(@Header("Authorization") String authHeader,@Query("username") String username, @Query("userPass") String userPass);

//    @Header("Authorization") String authHeader,

    @POST("getDashboardCount")
    Call<DashboardCount> getDashboardCount(@Header("Authorization") String authHeader,@Query("empId") int empId);

    @POST("getLeaveApplyListForAuth")
    Call<ArrayList<LeaveApp>> getLeaveApplyListForAuth(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("statusList") List<Integer> statusList, @Query("authTypeId") int authTypeId, @Query("currYrId") int currYrId);

    @POST("getLeaveStatusList")
    Call<ArrayList<MyLeaveData>> getLeaveStatusList(@Header("Authorization") String authHeader, @Query("empId") int empId,@Query("status") int status);

    @POST("getEmpListForClaimAuthByEmpId")
    Call<ArrayList<LeaveEmployeeModel>> getEmpListForClaimAuthByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

}
