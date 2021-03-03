package com.samansepahvand.retrofitrequestnew.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.samansepahvand.retrofitrequestnew.R;
import com.samansepahvand.retrofitrequestnew.api.ApiClient;
import com.samansepahvand.retrofitrequestnew.api.ApiInterface;
import com.samansepahvand.retrofitrequestnew.model.LoginMethod;
import com.samansepahvand.retrofitrequestnew.model.error.ErrorLogin;
import com.samansepahvand.retrofitrequestnew.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {


    private  static final String TAG="tag";


    TextView txtSignUp,txtSignUpPhone;

    EditText edtUserName,edtPassword;

    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();




    }

    private void loginAuth(String UserName,String Password){



        ApiInterface apiInterface= ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginResponse> call=apiInterface.loginAuth(login(UserName,Password));

      ///  Call<String> call1=apiInterface.loginAuth1(1,1);


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG, "Login onResponse: "+response.toString() );


                if (response.isSuccessful()){
                    LoginResponse model=response.body();
                    showLog(model);
                }

                if (!response.isSuccessful()){
                    ErrorLogin responseError
                                    =new Gson().fromJson(response.errorBody().charStream(),ErrorLogin.class);

                    showErrorLog(responseError);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    private void showErrorLog(ErrorLogin responseError) {
        Log.e(TAG, " ");
        Log.e(TAG, "responseError code : "+responseError.error.code );
        Log.e(TAG, "responseError detail : "+responseError.error.detail );
        Log.e(TAG, "responseError message : "+responseError.error.message );

    }

    private  LoginMethod login(String username,String password){

        LoginMethod loginMethod=new LoginMethod();

        loginMethod.setUsername(username);
        loginMethod.setPassword(password);

        return  loginMethod!=null?loginMethod:null;

    }

    private void showLog(LoginResponse model) {


        Log.e(TAG, "showLog name: "+model.name);
        Log.e(TAG, "showLog token: "+model.token);
        Log.e(TAG, "showLog email: "+model.email);
        Log.e(TAG, "showLog cellphone: "+model.cellphone);
        Log.e(TAG, "showLog avatar: "+model.avatar);
        Log.e(TAG, "showLog gender: "+model.gender);
        for (LoginResponse.UserRole role:model.user_role) {
            Log.e(TAG, "showLog title: "+role.role.title);
        }

    }


    private void initView(){
        txtSignUp=findViewById(R.id.txt_signup);
        edtUserName=findViewById(R.id.edt_username);
        edtPassword=findViewById(R.id.edt_passowrd);
        txtSignUpPhone=findViewById(R.id.txt_signup_phone);

        btnLogin=findViewById(R.id.btn_login);

        txtSignUpPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this,PhoneAuthentication.class));
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this,ActivitySignUp.class));
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TempUserName=edtUserName.getText().toString();
                String TempPassword=edtPassword.getText().toString();
                loginAuth(TempUserName,TempPassword);

            }
        });

    }
}