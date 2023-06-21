package com.shakticonsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class SignInActivity extends AppCompatActivity {
    String firebaseToken;
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
        cd = new ConnectionDetector(SignInActivity.this);

        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
        });

        binding.tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        binding.btnSignIn.setOnClickListener(v -> {

            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            } else if (binding.edtEmail.getText().toString().equals("")) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The Email is Required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            } else if (!binding.edtEmail.getText().toString().matches(emailPattern)) {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The valid email is required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
            } else if (binding.edtPassword.getText().toString().trim().equals("")) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "The password is required.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            } else {

                loginAPicall();
                // loginAPicall(binding.edtEmail.getText().toString().trim(),binding.edtPassword.getText().toString().trim());
            }
            // startActivity(new Intent(SignInActivity.this, MainActivity.class));
        });
    }

    public void loginAPicall() {
        // binding.idLoadingPB.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        Map<String, String> map = new HashMap<>();

        map.put("email", binding.edtEmail.getText().toString().trim());
        map.put("password", binding.edtPassword.getText().toString().trim());
        map.put("fcm_token", AppPrefrences.getUserToken(SignInActivity.this));
        Log.d("Print Responsse", AppPrefrences.getUserToken(SignInActivity.this));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> resultCall = apiInterface.callSignInApi(map);
        resultCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    binding.idLoadingPB.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        Log.d("Print Responsse", response.body().toString());
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        String otp_verify = response.body().getData().getOtp_verify();
                        String personal = response.body().getData().getPersonal();
                        String acedemic = response.body().getData().getAcademic();
                        String employee = response.body().getData().getEmployee();
                        String experience = response.body().getData().getExperience();
                        if (otp_verify.equals("0") && personal.equals("0") && acedemic.equals("0") && employee.equals("0")) {
                            Intent i = new Intent(SignInActivity.this, OTPActivity.class);
                            i.putExtra("userid", response.body().getData().getId());
                            i.putExtra("name", response.body().getData().getName());
                            i.putExtra("mobile", response.body().getData().getMobile());
                            // AppPrefrences.setLocation(SignUpActivity.this,response.body().getData().getLocation());
                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        } else if (personal.equals("0") && acedemic.equals("0") && employee.equals("0")) {
                            Intent i = new Intent(SignInActivity.this, PersonalInfoActivity.class);
                            i.putExtra("userid", response.body().getData().getId());
                            i.putExtra("name", response.body().getData().getName());
                            i.putExtra("mobile", response.body().getData().getMobile());

                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);

                        } else if (employee.equals("0") && acedemic.equals("0")) {

                            Intent i = new Intent(SignInActivity.this, AcademicDetailsActivity.class);
                            i.putExtra("userid", response.body().getData().getId());
                            i.putExtra("experience", response.body().getData().getExperience());

                            startActivity(i);
                            finish();

                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        } else if (employee.equals("0")) {

                            if (experience.equals("Fresher")) {

                                AppPrefrences.setUserid(SignInActivity.this, response.body().getData().getId());
                                AppPrefrences.setExperience(SignInActivity.this, response.body().getData().getExperience());

                                AppPrefrences.setLogin_status(SignInActivity.this, true);
                                AppPrefrences.setLocation(SignInActivity.this, response.body().getData().getLocation());
                                AppPrefrences.setName(SignInActivity.this, response.body().getData().getName());
                                AppPrefrences.setMobile(SignInActivity.this, response.body().getData().getMobile());
                                AppPrefrences.setMail(SignInActivity.this, response.body().getData().getEmail());
                                AppPrefrences.setProfileImg(SignInActivity.this, response.body().getData().getProfile_img());
                                AppPrefrences.setSkillId(SignInActivity.this, response.body().getData().getSkill_id());
                                AppPrefrences.setCategoryId(SignInActivity.this, response.body().getData().getCategory_id());

                                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                                i.putExtra("userid", response.body().getData().getId());

                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            } else {

                                Intent i = new Intent(SignInActivity.this, EmployeeHistoryActivity.class);
                                i.putExtra("userid", response.body().getData().getId());

                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                            }
                        } else {
                            AppPrefrences.setUserid(SignInActivity.this, response.body().getData().getId());

                            AppPrefrences.setLogin_status(SignInActivity.this, true);
                            AppPrefrences.setLocation(SignInActivity.this, response.body().getData().getLocation());
                            AppPrefrences.setName(SignInActivity.this, response.body().getData().getName());
                            AppPrefrences.setMobile(SignInActivity.this, response.body().getData().getMobile());
                            AppPrefrences.setMail(SignInActivity.this, response.body().getData().getEmail());
                            AppPrefrences.setProfileImg(SignInActivity.this, response.body().getData().getProfile_img());
                            AppPrefrences.setSkillId(SignInActivity.this, response.body().getData().getSkill_id());
                            AppPrefrences.setCategoryId(SignInActivity.this, response.body().getData().getCategory_id());

                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            i.putExtra("userid", response.body().getData().getId());

                            startActivity(i);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                            // startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                    } else {
                        //  binding.idLoadingPB.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                    }
                } else {
                    //   binding.idLoadingPB.setVisibility(View.GONE);
                    progress_spinner.dismiss();

                    Utils.showFailureDialog(SignInActivity.this, "Please enter valid email & password");

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //  binding.idLoadingPB.setVisibility(View.GONE);
                progress_spinner.dismiss();

                Utils.showFailureDialog(SignInActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (AppPrefrences.getLogin_status(SignInActivity.this).equals(false)) {

            finishAffinity();
            System.exit(0);
        }
    }
}