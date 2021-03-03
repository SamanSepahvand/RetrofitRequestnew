package com.samansepahvand.retrofitrequestnew.model.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorLogin {

    @SerializedName("error")
    @Expose
    public Error error;

    public class Error{
        @SerializedName("code")
        @Expose
        public int code;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("detail")
        @Expose
        public String detail;
    }

}
