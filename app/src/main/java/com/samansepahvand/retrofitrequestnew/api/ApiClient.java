package com.samansepahvand.retrofitrequestnew.api;




import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static final String BASE_URL="https://ghanadiaylar.ir/api/";
    private  static  Retrofit retrofit=null;

    public static Retrofit getRetrofitInstance(){

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


         retrofit=new Retrofit.Builder()
                 .client(okHttpClient)
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

        return retrofit;

    }


}
