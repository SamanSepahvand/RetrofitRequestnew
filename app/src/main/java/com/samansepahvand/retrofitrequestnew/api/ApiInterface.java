package com.samansepahvand.retrofitrequestnew.api;

import android.content.SharedPreferences;

import com.samansepahvand.retrofitrequestnew.model.LoginMethod;
import com.samansepahvand.retrofitrequestnew.model.error.ErrorLogin;
import com.samansepahvand.retrofitrequestnew.model.response.LoginResponse;
import com.samansepahvand.retrofitrequestnew.model.PersonnelRegister;
import com.samansepahvand.retrofitrequestnew.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;




public interface ApiInterface {


    @Headers({"Content-Type:application/json",
                "X-CSRFToken:rE9Qgh2d3ZITe9kTvow75dwbG9M8qzgbEPqnlBjPlH4JJMUTZPLe0IFarhHvxV96"})
    @POST("personnel/register/")
    Call<RegisterResponse> registerPersonnel(@Header("Content-Type") String type,@Body PersonnelRegister model);




    @Headers({"Content-Type:application/json",
            "X-CSRFToken:rE9Qgh2d3ZITe9kTvow75dwbG9M8qzgbEPqnlBjPlH4JJMUTZPLe0IFarhHvxV96"})
    @POST("login/")
    Call<LoginResponse> loginAuth(@Body LoginMethod model);

    @Headers({"Content-Type:application/json"})
    @POST("client/register/")
    Call<RegisterResponse> phoneRegister(@Body PersonnelRegister model);

    @Headers({"Content-Type:application/json"})
    @FormUrlEncoded
    @POST("/client/resend/")
    Call<ErrorLogin> resendCode(@Field("cellphone") String cellphone);


    @POST("client/register/")
    Call<String> testToken(@Header("CustomeToken") String token, @Field("get") int id );







}
