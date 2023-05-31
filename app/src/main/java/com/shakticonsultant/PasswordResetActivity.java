package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityPasswordResetBinding;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
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

public class PasswordResetActivity extends AppCompatActivity implements TextWatcher {

    ActivityPasswordResetBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
    String userid;
    String stredt1, stredt2, stredt3, stredt4;
    String strConcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd = new ConnectionDetector(PasswordResetActivity.this);

        userid = getIntent().getStringExtra("userid");
        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        ShowHideKeyboard.showKeyboard(binding.otpView);

        binding.edt1.addTextChangedListener(this);
        binding.edt2.addTextChangedListener(PasswordResetActivity.this);
        binding.edt3.addTextChangedListener(PasswordResetActivity.this);
        binding.edt4.addTextChangedListener(PasswordResetActivity.this);
        binding.edt1.requestFocus();

        binding.edt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edt1.getText().toString().trim().equals("")) {

                    binding.edt2.setCursorVisible(false);
                    binding.edt3.setCursorVisible(false);
                    binding.edt4.setCursorVisible(false);

                }
            }
        });
        binding.txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                binding.edt1.setText("");
                binding.edt2.setText("");
                binding.edt3.setText("");
                binding.edt4.setText("");
                stredt1 = binding.edt1.getText().toString().trim();
                stredt2 = binding.edt2.getText().toString().trim();
                stredt3 = binding.edt3.getText().toString().trim();
                stredt4 = binding.edt4.getText().toString().trim();

                /*edt1.getText().toString().equals("");
                edt2.getText().toString().equals("");
                edt3.getText().toString().equals("");
                edt4.getText().toString().equals("");*/


                if (!stredt1.equals("") && !stredt2.equals("") && !stredt3.equals("") && !stredt4.equals("")) {
                    // Utils.showFailureDialog(OtpVerificationActivity.this, "Please enter OTP");
           /* Snackbar.make(findViewById(android.R.id.content), "The otp is required.", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();*/

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "The otp is required.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else {
                    resendOtpApi();
                }


            }
        });
        binding.btnResetPassword.setOnClickListener(v -> {
            stredt1 = binding.edt1.getText().toString().trim();
            stredt2 = binding.edt2.getText().toString().trim();
            stredt3 = binding.edt3.getText().toString().trim();
            stredt4 = binding.edt4.getText().toString().trim();
            strConcat = stredt1 + stredt2 + stredt3 + stredt4;
            strConcat = stredt1 + stredt2 + stredt3 + stredt4;
            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
            if (stredt1.equals("") && stredt2.equals("") && stredt3.equals("") && stredt4.equals("")) {
                // Utils.showFailureDialog(OtpVerificationActivity.this, "Please enter OTP");
                  /*  Snackbar.make(findViewById(android.R.id.content), "The otp is required.", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();*/
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The otp is required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();


            } else if (binding.edtPassword.getText().toString().trim().equals("")) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            } else if (binding.edtConfirmPassword.getText().toString().trim().equals("")) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter confirm password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            } else if (!(binding.edtPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim()))) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please re enter the password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            } else {
                changePasswordApi();
            }
        });

    }


    public void changePasswordApi() {
        //  binding.progressBar.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("password", binding.edtPassword.getText().toString().trim());
        map.put("conf_password", binding.edtConfirmPassword.getText().toString().trim());
        map.put("user_id", userid);
        map.put("otp", strConcat);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ChangePasswordResponse> resultCall = apiInterface.callChangePassword(map);

        resultCall.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar4.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        Intent i = new Intent(PasswordResetActivity.this, SignInActivity.class);
                        startActivity(i);
                        finish();
                        // startActivity(new Intent(ForgotPasswordActivity.this, PasswordResetActivity.class));
                    } else {
                        Utils.showFailureDialog(PasswordResetActivity.this, response.body().getMessage());


                    }
                } else {

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

    public void resendOtpApi() {
        binding.progressBar4.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        //  map.put("otp", strConcat);
        map.put("user_id", userid);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callResendOTP(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar4.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        //  startActivity(new Intent(OTPActivity.this, PersonalInfoActivity.class));                    }  else {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Resend Otp send successfully.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));
                        snackbar.show();

                    } else {
                        binding.progressBar4.setVisibility(View.GONE);

                    }
                } else {

                    binding.progressBar4.setVisibility(View.GONE);
                    //   Utils.showFailureDialog(OTPActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressBar4.setVisibility(View.GONE);
                //Utils.showFailureDialog(OTPActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 1) {

            if (binding.edt1.length() == 1) {
                binding.edt2.requestFocus();
            }

            if (binding.edt2.length() == 1) {
                binding.edt3.requestFocus();
            }
            if (binding.edt3.length() == 1) {
                binding.edt4.requestFocus();
            }
        } else if (editable.length() == 0) {
            if (binding.edt4.length() == 0) {
                binding.edt3.requestFocus();
            }
            if (binding.edt3.length() == 0) {
                binding.edt2.requestFocus();
            }
            if (binding.edt2.length() == 0) {
                binding.edt1.requestFocus();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

}