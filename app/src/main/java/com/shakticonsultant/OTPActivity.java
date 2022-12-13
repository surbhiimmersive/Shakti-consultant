package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.freedomotpview.Configuration;
import com.freedomotpview.OTPView;
import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityOtpactivityBinding;
import com.shakticonsultant.responsemodel.CommonResponse;
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

public class OTPActivity extends AppCompatActivity implements TextWatcher {

    ActivityOtpactivityBinding binding;
ApiInterface apiInterface;
String userid;
String otp;
ConnectionDetector cd;
TextView txtResend;
String strConcat;
    String stredt1,stredt2,stredt3,stredt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd=new ConnectionDetector(OTPActivity.this);

        /* show keyboard */
       ShowHideKeyboard.showKeyboard(binding.otpView);

userid=getIntent().getStringExtra("userid");
        binding.edt1.addTextChangedListener(this);
        binding.edt2.addTextChangedListener(this);
        binding.edt3.addTextChangedListener(this);
        binding.edt4.addTextChangedListener(this);

binding.txtResend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        binding.edt1.setText("");
        binding.edt2.setText("");
        binding.edt3.setText("");
        binding.edt4.setText("");
        stredt1=binding.edt1.getText().toString().trim();
        stredt2=binding.edt2.getText().toString().trim();
        stredt3=binding.edt3.getText().toString().trim();
        stredt4=binding.edt4.getText().toString().trim();

                /*edt1.getText().toString().equals("");
                edt2.getText().toString().equals("");
                edt3.getText().toString().equals("");
                edt4.getText().toString().equals("");*/


        if(!stredt1.equals("") && !stredt2.equals("") && !stredt3.equals("")&& !stredt4.equals(""))

        {
            // Utils.showFailureDialog(OtpVerificationActivity.this, "Please enter OTP");
            Snackbar.make(findViewById(android.R.id.content), "The otp field is required.", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            resendOtpApi();
        }


    }
});
           binding.btnEnterOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
if(binding.otpView.getOtp().equals("")){

    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter otp.", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else {
    OtpApi();
}
               //startActivity(new Intent(OTPActivity.this, PersonalInfoActivity.class));*/


                stredt1=binding.edt1.getText().toString().trim();
                stredt2=binding.edt2.getText().toString().trim();
                stredt3=binding.edt3.getText().toString().trim();
                stredt4=binding.edt4.getText().toString().trim();
                strConcat=stredt1+stredt2+stredt3+stredt4;
                strConcat=stredt1+stredt2+stredt3+stredt4;
                if(stredt1.equals("") && stredt2.equals("") && stredt3.equals("")&& stredt4.equals(""))

                {
                    // Utils.showFailureDialog(OtpVerificationActivity.this, "Please enter OTP");
                    Snackbar.make(findViewById(android.R.id.content), "The otp field is required.", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                } else {
                    OtpApi();
                }
            }
        });

        binding.imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        Configuration config = new OTPView.Builder()
//        .setBackground(ContextCompat.getDrawable(this, R.drawable.otp_box_bg))
//                .build();
//
//        binding.otpView.setConfiguration(config);

    }




    public void OtpApi() {
        binding.progressBar3.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("otp", strConcat);
        map.put("user_id", userid);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callMatchOtpApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar3.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                      //  startActivity(new Intent(OTPActivity.this, PersonalInfoActivity.class));                    }  else {
Intent i=new Intent(OTPActivity.this,PersonalInfoActivity.class);
i.putExtra("userid",userid);
startActivity(i);
finish();
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);

                    }else{
                        binding.progressBar3.setVisibility(View.GONE);

                        Utils.showFailureDialog(OTPActivity.this, response.body().getMessage());

                    }
                }else{

                    binding.progressBar3.setVisibility(View.GONE);
                    Snackbar.make(findViewById(android.R.id.content), "The valid otp is required.", Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();
                 //  Utils.showFailureDialog(OTPActivity.this,"OTP not match.");
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressBar3.setVisibility(View.GONE);
                Utils.showFailureDialog(OTPActivity.this, "Something went wrong!");
            }
        });
    }
    public void resendOtpApi() {
        binding.progressBar3.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("otp", strConcat);
        map.put("user_id", userid);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callMatchOtpApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBar3.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                      //  startActivity(new Intent(OTPActivity.this, PersonalInfoActivity.class));                    }  else {


                    }else{
                        binding.progressBar3.setVisibility(View.GONE);

                    }
                }else{

                    binding.progressBar3.setVisibility(View.GONE);
                 //   Utils.showFailureDialog(OTPActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressBar3.setVisibility(View.GONE);
                //Utils.showFailureDialog(OTPActivity.this, "Something went wrong!");
            }
        });
    }
    @Override
    public void afterTextChanged(Editable editable) {if (editable.length() == 1) {
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}