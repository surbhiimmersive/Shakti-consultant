package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivitySignUpBinding;
import com.shakticonsultant.responsemodel.LoginResponse;
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

public class SignUpActivity extends AppCompatActivity {
    String MobilePattern = "[0-9]{10}";

    ActivitySignUpBinding binding;
    ApiInterface apiInterface;
    ConnectionDetector cd;
 //   String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd = new ConnectionDetector(SignUpActivity.this);


        binding.tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!cd.isConnectingToInternet()) {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else if (binding.edtName.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (binding.edtName.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (binding.edtMobile.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter name.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if(!binding.edtMobile.getText().toString().matches(MobilePattern)) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid 10 digit phone number.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }else if (binding.edtEmail.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter email.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }/*else if(!binding.edtEmail.getText().toString().matches(emailPattern)) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid email address.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();


                }*/
                else if (binding.edtPassword.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter password.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else if (binding.edtRePassword.getText().toString().trim().equals("")) {

                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter re password.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }
                else if(!(binding.edtPassword.getText().toString().equals(binding.edtRePassword.getText().toString()))){
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please check password mismatch", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                    binding.edtPassword.setText("");
                    binding.edtRePassword.setText("");
                }

                else if (!binding.checkBox.isChecked()) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please check terms and conditions.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                } else {
                   //signupApi(binding.edtName.getText().toString().trim(),binding.edtMobile.getText().toString().trim(),binding.edtEmail.getText().toString().trim(),binding.edtPassword.getText().toString().trim(),binding.edtRePassword.getText().toString().trim());
                   // startActivity(new Intent(SignUpActivity.this, OTPActivity.class));

                    signupApi();
                }
            }
        });

    }


    public void signupApi() {
        binding.progressBar2.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("name", binding.edtName.getText().toString().trim());
        map.put("email", binding.edtEmail.getText().toString().trim());
        map.put("mobile", binding.edtMobile.getText().toString().trim());
        map.put("password", binding.edtPassword.getText().toString().trim());
        map.put("re_password", binding.edtRePassword.getText().toString().trim());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<SignupResponse> resultCall = apiInterface.callSignUpApi(map);

        resultCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar2.setVisibility(View.GONE);
                    if (response.body().getMessage().equals("Registration successfully")) {

                        Intent i=new Intent(SignUpActivity.this,OTPActivity.class);
                        i.putExtra("userid",response.body().getData().getId());
                        startActivity(i);
                        finish();
                    }  else {
                        Utils.showFailureDialog(SignUpActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                binding.progressBar2.setVisibility(View.GONE);
                Utils.showFailureDialog(SignUpActivity.this, "Something went wrong!");
            }
        });
    }



    }
