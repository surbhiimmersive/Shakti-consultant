package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
cd=new ConnectionDetector(SignInActivity.this);
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
            }
            else
            if (binding.edtEmail.getText().toString().trim().equals("") || !binding.edtEmail.getText().toString().trim().matches(emailPattern)) {

                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid email.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();

            }
 else
    if(binding.edtPassword.getText().toString().trim().equals("")) {

        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter password.", Snackbar.LENGTH_LONG)
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

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> resultCall = apiInterface.callSignInApi(map);

        resultCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    binding.idLoadingPB.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                       Log.e( "User ID",response.body().getData().getId());
                       // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
String personal=response.body().getData().getPersonal();
String acedemic=response.body().getData().getAcademic();
String employee=response.body().getData().getEmployee();
                        if(personal.equals("0") && acedemic.equals("0") && employee.equals("0") ) {
                            Intent i = new Intent(SignInActivity.this, PersonalInfoActivity.class);
                            i.putExtra("userid",response.body().getData().getId());
                            startActivity(i);
                            finish();

                        }else if(employee.equals("0")&& acedemic.equals("0") ) {

                                Intent i = new Intent(SignInActivity.this, AcademicDetailsActivity.class);
                            i.putExtra("userid",response.body().getData().getId());

                            startActivity(i);
                                finish();


                        }else if(employee.equals("0") ){

                            Intent i = new Intent(SignInActivity.this, EmployeeHistoryActivity.class);
                            i.putExtra("userid",response.body().getData().getId());

                            startActivity(i);
                            finish();

                        }else{
                            AppPrefrences.setLogin_status(SignInActivity.this,true);
                            AppPrefrences.setLocation(SignInActivity.this,response.body().getData().getLocation());

                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            i.putExtra("userid",response.body().getData().getId());

                            startActivity(i);
                            finish();
                               // startActivity(new Intent(SignInActivity.this, MainActivity.class));

                    }
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.idLoadingPB.setVisibility(View.GONE);
                Utils.showFailureDialog(SignInActivity.this, "Something went wrong!");
            }
        });
    }





}