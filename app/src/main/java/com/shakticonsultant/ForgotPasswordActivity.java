package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityForgotPasswordBinding;
import com.shakticonsultant.responsemodel.ForgotResponse;
import com.shakticonsultant.responsemodel.SignupResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    String MobilePattern = "[0-9]{10}";

    ActivityForgotPasswordBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd=new ConnectionDetector(ForgotPasswordActivity.this);

        /* show keyboard */
        ShowHideKeyboard.showKeyboard(binding.edittextMobileNumber);

        binding.imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            }
        });

        binding.btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                }
                else
                if (binding.edittextMobileNumber.getText().toString().trim().equals("")){
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter mobile number.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }

                else if(!binding.edittextMobileNumber.getText().toString().matches(MobilePattern)) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid 10 digit phone number.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }
                else{

                    forgetApi();

                }


                //startActivity(new Intent(ForgotPasswordActivity.this, PasswordResetActivity.class));
            }
        });




    }




    public void forgetApi() {
        binding.progressBar.setVisibility(View.GONE);

        Map<String, String> map = new HashMap<>();

        map.put("mobile", binding.edittextMobileNumber.getText().toString().trim());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ForgotResponse> resultCall = apiInterface.callForgotpasswordApi(map);

        resultCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    if (response.body().getMessage().equals("OTP sent successfully")) {
Intent i=new Intent(ForgotPasswordActivity.this,PasswordResetActivity.class);
i.putExtra("userid",response.body().getData().getUser_id());
startActivity(i);
                       // startActivity(new Intent(ForgotPasswordActivity.this, PasswordResetActivity.class));
                    }  else {
                        Utils.showFailureDialog(ForgotPasswordActivity.this, response.body().getMessage());


                    }
                }else{

                    binding.progressBar.setVisibility(View.GONE);

                    Utils.showFailureDialog(ForgotPasswordActivity.this, "Record not found");


                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Utils.showFailureDialog(ForgotPasswordActivity.this, "Something went wrong!");
            }
        });
    }


}