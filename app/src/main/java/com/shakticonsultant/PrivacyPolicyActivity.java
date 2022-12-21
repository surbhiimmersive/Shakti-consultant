package com.shakticonsultant;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityTermsConditionBinding;
import com.shakticonsultant.responsemodel.AboutResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity {

    ActivityTermsConditionBinding binding;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
binding.textView49.setText("Privacy Policy");
        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        cd = new ConnectionDetector(PrivacyPolicyActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {


            privacyPloicy();

        }

    }
        public void privacyPloicy () {
            binding.progressAbout.setVisibility(View.VISIBLE);

            Map<String, String> map = new HashMap<>();


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<AboutResponse> resultCall = apiInterface.callPrivacyPolicy();

            resultCall.enqueue(new Callback<AboutResponse>() {
                @Override
                public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {

                    if (response.isSuccessful()) {
                        binding.progressAbout.setVisibility(View.GONE);
                        if (response.body().isSuccess() == true) {
                            // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                            String content = response.body().getData().getContent();
                            binding.textView63.setText(content);
                            //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                        } else {
                            Utils.showFailureDialog(PrivacyPolicyActivity.this, response.body().getMessage());


                        }
                    }
                }

                @Override
                public void onFailure(Call<AboutResponse> call, Throwable t) {
                    binding.progressAbout.setVisibility(View.GONE);
                    Utils.showFailureDialog(PrivacyPolicyActivity.this, "Something went wrong!");
                }
            });
        }

    }
