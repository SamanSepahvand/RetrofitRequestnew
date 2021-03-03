package com.samansepahvand.retrofitrequestnew.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.samansepahvand.retrofitrequestnew.R;
import com.samansepahvand.retrofitrequestnew.api.ApiClient;
import com.samansepahvand.retrofitrequestnew.api.ApiInterface;
import com.samansepahvand.retrofitrequestnew.model.PersonnelRegister;
import com.samansepahvand.retrofitrequestnew.model.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUp extends AppCompatActivity {



    private static final String TAG="tag";



    private EditText edtUserName,edtFirstName, edtLastName, edtGender, edtCellPhone, edtEmail,edtPassword;

    private Button btnSignUp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initView();




    }

    private void initView(){

        edtCellPhone = findViewById(R.id.edt_cellphone);
        edtFirstName = findViewById(R.id.edt_first_name);
        edtGender = findViewById(R.id.edt_gender);
        edtEmail = findViewById(R.id.edt_email);
        edtLastName = findViewById(R.id.edt_last_name);
        edtPassword = findViewById(R.id.edt_password);
        edtUserName=findViewById(R.id.edt_user_name);
        btnSignUp=findViewById(R.id.btn_sign_up);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPersonnel();
            }
        });


    }

    private  void registerPersonnel(){

        ///fill  api/request
        final PersonnelRegister register=new PersonnelRegister();
        register.setFirst_name(edtFirstName.getText().toString());
        register.setLast_name(edtLastName.getText().toString());
        register.setCellphone(edtCellPhone.getText().toString());
        register.setEmail(edtEmail.getText().toString());
        register.setGender(edtGender.getText().toString());
        register.setPassword(edtPassword.getText().toString());
        register.setUsername(edtUserName.getText().toString());



        ///set argument
        ApiInterface apiInterface= ApiClient.getRetrofitInstance().create(ApiInterface.class);
        Call <RegisterResponse> call=apiInterface.registerPersonnel(register);


        ///call retrofit methods
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                Log.e(TAG, "onResponse: "+response.toString() );

                if (response.isSuccessful()){
                    Log.e(TAG, "onResponse isSuccessful(): "+response.body() );
                    RegisterResponse model=response.body();
                    showData(model);

                }else{

                    Log.e(TAG, "onResponse  ! isSuccessful(): "+response.body() );

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }

    private void showData(RegisterResponse model) {
        Log.e(TAG, "showData id: "+model.id );
        Log.e(TAG, "showData first_name: "+model.first_name );
        Log.e(TAG, "showData last_name: "+model.last_name );
        Log.e(TAG, "showData username: "+model.username );
        Log.e(TAG, "showData password: "+model.password );
        Log.e(TAG, "showData cellphone: "+model.cellphone );
        Log.e(TAG, "showData gender: "+model.gender );


    }
}