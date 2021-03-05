package com.samansepahvand.retrofitrequestnew.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {


    @SerializedName("token")
    @Expose
    public String token;


    @SerializedName("username")
    @Expose
    public String username;



    @SerializedName("password")
    @Expose
    public String password;



    @SerializedName("avatar")
    @Expose
    public Object avatar;
    @SerializedName("cellphone")
    @Expose

    public String cellphone;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("gender")
    @Expose
    public String gender;

    @SerializedName("user_role")
    @Expose
    public List<UserRole> user_role;


    public class UserRole{

        @SerializedName("role")
        @Expose
        public Role role;

    }

    public class Role{

        @SerializedName("title")
        @Expose
        public String title;
    }






}
