package com.ats.hreasy.interfaces;

import com.ats.hreasy.model.AuthorityIds;
import com.ats.hreasy.model.BalanceLeaveModel;
import com.ats.hreasy.model.ClaimApp;
import com.ats.hreasy.model.ClaimApply;
import com.ats.hreasy.model.ClaimHistoryModel;
import com.ats.hreasy.model.ClaimType;
import com.ats.hreasy.model.CurrentYearModel;
import com.ats.hreasy.model.DashboardCount;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.LeaveApp;
import com.ats.hreasy.model.LeaveApply;
import com.ats.hreasy.model.LeaveEmployeeModel;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.model.MyLeaveData;
import com.ats.hreasy.model.MyLeaveTrailData;
import com.ats.hreasy.model.ProjectList;
import com.ats.hreasy.model.SaveClaimTrail;
import com.ats.hreasy.model.SaveLeaveTrail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("login")
    Call<Login> doLogin(@Header("Authorization") String authHeader, @Query("username") String username, @Query("userPass") String userPass);

//    @Header("Authorization") String authHeader,

    @POST("getDashboardCount")
    Call<DashboardCount> getDashboardCount(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getLeaveApplyListForPending")
    Call<ArrayList<LeaveApp>> getLeaveApplyListForPending(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @POST("getLeaveApplyListForInformation")
    Call<ArrayList<LeaveApp>> getLeaveApplyListForInfo(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @POST("getLeaveStatusList")
    Call<ArrayList<MyLeaveData>> getLeaveStatusList(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("status") ArrayList<Integer> status);

    @POST("getClaimStatusList")
    Call<ArrayList<ClaimHistoryModel>> getClaimStatusList(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("status") ArrayList<Integer> status);

    @POST("getEmpListForClaimAuthByEmpId")
    Call<ArrayList<LeaveEmployeeModel>> getEmpListForClaimAuthByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getEmployeeListByEmpId")
    Call<ArrayList<LeaveEmployeeModel>> getEmployeeListByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);


    @POST("getLeaveHistoryList")
    Call<ArrayList<BalanceLeaveModel>> getBalanceLeave(@Header("Authorization") String authHeader, @Query("empId") int empId, @Query("currYrId") int currYrId);

    @GET("getCalculateYearListIsCurrent")
    Call<CurrentYearModel> getCurrentYear(@Header("Authorization") String authHeader);

    @GET("getClaimList")
    Call<ArrayList<ClaimType>> getClaimList(@Header("Authorization") String authHeader);

    @POST("getProjectsListByCompanyId")
    Call<ArrayList<ProjectList>> getProjectsListByCompanyId(@Header("Authorization") String authHeader, @Query("companyId") int companyId);

    @POST("getLeaveTrailList")
    Call<ArrayList<MyLeaveTrailData>> getLeaveTrail(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId);

    @POST("getAuthIdByEmpId")
    Call<AuthorityIds> getAuthIdByEmpId(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimAuthIds")
    Call<AuthorityIds> getClaimAuthIds(@Header("Authorization") String authHeader, @Query("empId") int empId,@Query("companyId") int companyId);



    @POST("updateLeaveStatus")
    Call<Info> updateLeaveStatus(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId, @Query("status") int status);

    @POST("saveLeaveTrail")
    Call<SaveLeaveTrail> saveLeaveTrail(@Header("Authorization") String authHeader, @Body SaveLeaveTrail saveLeaveTrail);

    @POST("updateTrailId")
    Call<Info> updateLeaveTrailId(@Header("Authorization") String authHeader, @Query("leaveId") int leaveId, @Query("trailId") int trailId);

    @POST("updateClaimTrailId")
    Call<Info> updateClaimTrailId(@Header("Authorization") String authHeader, @Query("claimId") int claimId, @Query("trailId") int trailId);


    @POST("saveLeaveApply")
    Call<LeaveApply> saveLeaveApply(@Header("Authorization") String authHeader,@Body LeaveApply leaveApply);

    @POST("saveClaimApply")
    Call<ClaimApply> saveClaimApply(@Header("Authorization") String authHeader, @Body ClaimApply claimApply);

    @POST("saveClaimTrail")
    Call<SaveClaimTrail> saveClaimTrail(@Header("Authorization") String authHeader, @Body SaveClaimTrail saveClaimTrail);

    @POST("updateClaimStatus")
    Call<Info> updateClaimStatus(@Header("Authorization") String authHeader, @Query("claimId") int claimId, @Query("status") int status);

    @POST("getClaimApplyListForPending")
    Call<ArrayList<ClaimApp>> getClaimApplyListForPending(@Header("Authorization") String authHeader, @Query("empId") int empId);

    @POST("getClaimApplyListForInformation")
    Call<ArrayList<ClaimApp>> getClaimApplyListForInfo(@Header("Authorization") String authHeader, @Query("empId") int empId);


}
