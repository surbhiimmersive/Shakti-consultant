package com.shakticonsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivitySignInBinding;
import com.shakticonsultant.responsemodel.LoginResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivityResetPassword extends AppCompatActivity {
    String  firebaseToken;
    ActivitySignInBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
    String emailPattern = "[a-zA-Z0-9+._%-+]{1,256}" +
            "@" +
            "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
            "(" +
            "." +
            "com" +
            ")+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(SignInActivityResetPassword.this);


        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivityResetPassword.this, ForgotPasswordActivity.class));
        });

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivityResetPassword.this, SignUpActivity.class));
        });

        binding.btnSignIn.setOnClickListener(v -> {

            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
            else
            if (binding.edtEmail.getText().toString().equals("") || !binding.edtEmail.getText().toString().matches(emailPattern)) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The valid email is required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            }

 else
    if(binding.edtPassword.getText().toString().trim().equals("")) {

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "The password is required.", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getColor(R.color.purple_200));

        snackbar.show();

    }

            else{

        loginAPicall();
               // loginAPicall(binding.edtEmail.getText().toString().trim(),binding.edtPassword.getText().toString().trim());
            }
                // startActivity(new Intent(SignInActivity.this, MainActivity.class));
        });

    }


    public void loginAPicall() {
        binding.idLoadingPB.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("email", binding.edtEmail.getText().toString().trim());
        map.put("password", binding.edtPassword.getText().toString().trim());
        map.put("fcm_token", AppPrefrences.getUserToken(SignInActivityResetPassword.this));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> resultCall = apiInterface.callSignInApi(map);

        resultCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    binding.idLoadingPB.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                               // startActivity(new Intent(SignInActivity.this, MainActivity.class));

                        AppPrefrences.setUserid(SignInActivityResetPassword.this,response.body().getData().getId());

                        AppPrefrences.setLogin_status(SignInActivityResetPassword.this,true);
                        AppPrefrences.setLocation(SignInActivityResetPassword.this,response.body().getData().getLocation());
                        AppPrefrences.setName(SignInActivityResetPassword.this,response.body().getData().getName());
                        AppPrefrences.setMobile(SignInActivityResetPassword.this,response.body().getData().getMobile());
                        AppPrefrences.setMail(SignInActivityResetPassword.this,response.body().getData().getEmail());
                        AppPrefrences.setProfileImg(SignInActivityResetPassword.this,response.body().getData().getProfile_img());
                        AppPrefrences.setSkillId(SignInActivityResetPassword.this,response.body().getData().getSkill_id());
                        AppPrefrences.setCategoryId(SignInActivityResetPassword.this,response.body().getData().getCategory_id());

                        Intent i = new Intent(SignInActivityResetPassword.this, ResetPasswordActivity.class);
                       // i.putExtra("userid",response.body().getData().getId());

                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                    } else {
                        binding.idLoadingPB.setVisibility(View.GONE);

                        Utils.showFailureDialog(SignInActivityResetPassword.this, response.body().getMessage());


                    }
                }else{
                    binding.idLoadingPB.setVisibility(View.GONE);

                    Utils.showFailureDialog(SignInActivityResetPassword.this, "Please enter valid email & password");

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.idLoadingPB.setVisibility(View.GONE);
                Utils.showFailureDialog(SignInActivityResetPassword.this, "Something went wrong!");
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}