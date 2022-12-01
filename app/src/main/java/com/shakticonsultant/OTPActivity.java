package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class OTPActivity extends AppCompatActivity {

    ActivityOtpactivityBinding binding;
ApiInterface apiInterface;
String userid;
String otp;
ConnectionDetector cd;
TextView txtResend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd=new ConnectionDetector(OTPActivity.this);

        /* show keyboard */
       ShowHideKeyboard.showKeyboard(binding.otpView);

userid=getIntent().getStringExtra("userid");


binding.txtResend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        OtpApi();

    }
});
           binding.btnEnterOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(binding.otpView.getOtp().equals("")){

    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter otp.", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else {
    OtpApi();
}
               //startActivity(new Intent(OTPActivity.this, PersonalInfoActivity.class));
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
        binding.progressBar3.setVisibility(View.GONE);

        Map<String, String> map = new HashMap<>();

        map.put("otp", binding.otpView.getOtp());
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

                    }
                }else{

                    binding.progressBar3.setVisibility(View.GONE);
                    Utils.showFailureDialog(OTPActivity.this, "Please try sometime later.");
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                binding.progressBar3.setVisibility(View.GONE);
                Utils.showFailureDialog(OTPActivity.this, "Something went wrong!");
            }
        });
    }

}