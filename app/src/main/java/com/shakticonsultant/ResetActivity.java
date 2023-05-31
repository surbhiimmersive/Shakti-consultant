package com.shakticonsultant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityPasswordResetBinding;
import com.shakticonsultant.databinding.ActivityResetPwdBinding;
import com.shakticonsultant.responsemodel.ChangePasswordResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
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

public class ResetActivity extends AppCompatActivity {

    ActivityResetPwdBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(ResetActivity.this);

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

            else if(binding.edtPassword.getText().toString().trim().equals("")){

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter the Password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            }
            else  if (binding.edtPassword.getText().toString().length() < 8) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Password minimum contain 8 character.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else if(binding.edtConfirmPassword.getText().toString().trim().equals("")){

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter the Confirm Password.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else  if (binding.edtConfirmPassword.getText().toString().length() < 8) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Re-Password minimum contain 8 character.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else if(!(binding.edtPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim())))
            {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The both password does not match", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            }
            else {
                resetpasswordapi();
            }
        });

    }




    public void resetpasswordapi() {
          binding.progressBar4.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("new_password", binding.edtPassword.getText().toString().trim());
        map.put("conf_password", binding.edtConfirmPassword.getText().toString().trim());
        map.put("user_id", AppPrefrences.getUserid(ResetActivity.this));
       // map.put("otp", binding.otpView.getOtp());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callResetPassword(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar4.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(ResetActivity.this,R.style.AlertDialogTheme)
                                .setTitle(R.string.app_name)
                                .setMessage("Password has been change successfully.")
                                .setIcon(R.drawable.shakti_consultant_logo)

                                .setPositiveButton(Html.fromHtml("<font color='#BB274D'>Ok</font>"), new DialogInterface.OnClickListener() {

                                    //  .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent i=new Intent(ResetActivity.this,MainActivity.class);
                                        startActivity(i);
                                    }
                                });
                        logoutDialog.show();

                        //finish();
                        // startActivity(new Intent(ForgotPasswordActivity.this, PasswordResetActivity.class));
                    }  else {
                     //   Utils.showFailureDialog(ResetActivity.this, response.body().getMessage());


                    }
                }else{

                    binding.progressBar4.setVisibility(View.GONE);

                  //  Utils.showFailureDialog(ResetActivity.this, "Please try sometime later");


                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressBar4.setVisibility(View.GONE);
               // Utils.showFailureDialog(ResetActivity.this, "Something went wrong!");
            }
        });
    }

}