package com.shakticonsultant;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityAboutUsBinding;
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

public class TermsCondition extends AppCompatActivity {

    ActivityTermsConditionBinding binding;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
binding.textView49.setText("Terms & Conditions");
        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        cd = new ConnectionDetector(TermsCondition.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {


            TermsConditionApi();

        }

    }
        public void TermsConditionApi () {
            binding.progressAbout.setVisibility(View.VISIBLE);

            Map<String, String> map = new HashMap<>();


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<AboutResponse> resultCall = apiInterface.callTermsCondition();

            resultCall.enqueue(new Callback<AboutResponse>() {
                @Override
                public void onResponse(Call<AboutResponse> call, Response<AboutResponse> response) {

                    if (response.isSuccessful()) {
                        binding.progressAbout.setVisibility(View.GONE);
                        if (response.body().isSuccess() == true) {
                            String content = response.body().getData().getContent();
                            binding.textView63.setText(content);
                            //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                        } else {
                            Utils.showFailureDialog(TermsCondition.this, response.body().getMessage());


                        }
                    }
                }

                @Override
                public void onFailure(Call<AboutResponse> call, Throwable t) {
                    binding.progressAbout.setVisibility(View.GONE);
                    Utils.showFailureDialog(TermsCondition.this, "Something went wrong!");
                }
            });
        }

    }
