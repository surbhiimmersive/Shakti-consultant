package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityPasswordResetBinding;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.ForgotResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordResetActivity extends AppCompatActivity {

    ActivityPasswordResetBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(PasswordResetActivity.this);

        userid=getIntent().getStringExtra("userid");
        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        binding.btnResetPassword.setOnClickListener(v -> {

            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
            else
            if(binding.otpView.getOtp().equals("")){

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter otp.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else if(binding.edtPassword.getText().toString().trim().equals("")){

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            }else if(binding.edtConfirmPassword.getText().toString().trim().equals("")){

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter confirm password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else if(!(binding.edtPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim())))
            {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please re enter the password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else {
                changePasswordApi();
            }
        });

    }




    public void changePasswordApi() {
      //  binding.progressBar.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("password", binding.edtPassword.getText().toString().trim());
        map.put("conf_password", binding.edtConfirmPassword.getText().toString().trim());
        map.put("user_id",userid);
        map.put("otp", binding.otpView.getOtp());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ChangePasswordResponse> resultCall = apiInterface.callChangePassword(map);

        resultCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar4.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        Intent i=new Intent(PasswordResetActivity.this,SignInActivity.class);
                        startActivity(i);
                        finish();
                        // startActivity(new Intent(ForgotPasswordActivity.this, PasswordResetActivity.class));
                    }  else {
                        Utils.showFailureDialog(PasswordResetActivity.this, response.body().getMessage());


                    }
                }else{

                    binding.progressBar4.setVisibility(View.GONE);

                    Utils.showFailureDialog(PasswordResetActivity.this, "Please try sometime later");


                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                binding.progressBar4.setVisibility(View.GONE);
                Utils.showFailureDialog(PasswordResetActivity.this, "Something went wrong!");
            }
        });
    }

}