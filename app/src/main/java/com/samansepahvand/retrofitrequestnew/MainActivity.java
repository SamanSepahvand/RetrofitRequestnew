package com.samansepahvand.retrofitrequestnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.samansepahvand.retrofitrequestnew.api.ApiClient;
import com.samansepahvand.retrofitrequestnew.api.ApiInterface;
import com.samansepahvand.retrofitrequestnew.model.response.LoginResponse;
import com.samansepahvand.retrofitrequestnew.service.CustomNotificationBroadcast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("PREF",MODE_PRIVATE);



        RequestBanner(handleToken(preferences));


        TextView textView=findViewById(R.id.txt_main);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CustomNotificationBroadcast.class);
                intent.putExtra("Title","test1 Title" );
                intent.putExtra("Body","test1 Body");
                sendBroadcast(intent);
            }
        });

    }

    private void RequestBanner(String handleToken) {

        if (handleToken!=null){
            ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);
            Call<String> call = apiInterface.testToken(handleToken,1);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });


        }else{
            return;
        }
    }

    private String handleToken(SharedPreferences preferences){
       if(preferences.contains("Token")){

           String tempToken=preferences.getString("",null);

           return  tempToken;
       }else{
           return  null;
       }


    };

}