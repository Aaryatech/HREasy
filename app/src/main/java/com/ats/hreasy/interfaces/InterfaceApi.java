package com.ats.hreasy.interfaces;

import com.ats.hreasy.model.Login;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceApi {

    @POST("login")
    Call<Login> doLogin(@Header("Authorization") String authHeader, @Query("username") String username, @Query("userPass") String userPass);

}
