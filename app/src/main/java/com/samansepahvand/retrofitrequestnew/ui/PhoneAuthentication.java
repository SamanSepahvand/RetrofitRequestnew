package com.samansepahvand.retrofitrequestnew.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.samansepahvand.retrofitrequestnew.R;
import com.samansepahvand.retrofitrequestnew.api.ApiClient;
import com.samansepahvand.retrofitrequestnew.api.ApiInterface;
import com.samansepahvand.retrofitrequestnew.model.PersonnelRegister;
import com.samansepahvand.retrofitrequestnew.model.error.ErrorLogin;
import com.samansepahvand.retrofitrequestnew.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneAuthentication extends AppCompatActivity {

    
    private static final String  TAG ="TAG";

    Button btnResend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);




        registerPhoneAuth();


        btnResend=findViewById(R.id.btn_resend);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ///fill  api/request
                String PhoneNumber="09399895811";


                    ///set argument
                    ApiInterface apiInterface= ApiClient.getRetrofitInstance().create(ApiInterface.class);
                    Call<ErrorLogin> call=apiInterface.resendCode(PhoneNumber);


                    ///call retrofit methods
                    call.enqueue(new Callback<ErrorLogin>() {
                        @Override
                        public void onResponse(Call<ErrorLogin> call, Response<ErrorLogin> response) {

                            Log.e(TAG, "onResponse: "+response.toString() );

                            if (response.isSuccessful()){
                                Log.e(TAG, "onResponse isSuccessful(): "+response.body() );
                                ErrorLogin model=response.body();
                                //  logData(model);

                            }else{

                                Log.e(TAG, "onResponse  ! isSuccessful(): "+response.body() );

                            }
                        }

                        @Override
                        public void onFailure(Call<ErrorLogin> call, Throwable t) {

                            Log.e(TAG, "onFailure: "+t.getMessage() );
                        }
                    });


            }
        });

    }

    private  void registerPhoneAuth(){

        ///fill  api/request
        final PersonnelRegister register=new PersonnelRegister();
        register.setFirst_name("string");
        register.setLast_name("string");
        register.setCellphone("09399895811");
        register.setEmail("string");
        register.setGender("male");
        
  

        ///set argument
        ApiInterface apiInterface= ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<RegisterResponse> call=apiInterface.phoneRegister(register);


        ///call retrofit methods
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                Log.e(TAG, "onResponse: "+response.toString() );

                if (response.isSuccessful()){
                    Log.e(TAG, "onResponse isSuccessful(): "+response.body() );
                    RegisterResponse model=response.body();
                  //  logData(model);

                }else{

                    Log.e(TAG, "onResponse  ! isSuccessful(): "+response.body() );

                    ErrorLogin responseError
                            =new Gson().fromJson(response.errorBody().charStream(),ErrorLogin.class);

                    showErrorLog(responseError);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }


    private void showErrorLog(ErrorLogin responseError) {
        Log.e(TAG, " ");
        Log.e(TAG, "responseError code : "+responseError.error.code );
        Log.e(TAG, "responseError detail : "+responseError.error.detail );
        Log.e(TAG, "responseError message : "+responseError.error.message );

    }
    private void logData(RegisterResponse mode){

    }

}