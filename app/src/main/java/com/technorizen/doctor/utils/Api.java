package com.technorizen.doctor.utils;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @POST("login")
    Call<ResponseBody> Login(@Query("mobile") String mobile,
                             @Query("password") String password,
                             @Query("andriod_register_id") String android_register_id,
                             @Query("ios_register_id") String ios_register_id);

    @POST("login")
    Call<ResponseBody> sendOtp(@Query("mobile") String mobile);

    @POST("login")
    Call<ResponseBody> getOtp(@Query("mobile") String mobile);

    @Multipart
    @POST("signup")
    Call<ResponseBody> signup(@Query("name") String name,
                              @Query("email") String email,
                              @Query("password") String password,
                              @Query("mobile") String mobile,
                              @Query("lat") String lat,
                              @Query("lon") String lon,
                              @Query("register_id") String register_id,
                              @Query("type") String type,
                              @Part MultipartBody.Part file);

}
