package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.FaqListAdapter;
import com.shakticonsultant.databinding.ActivityFaqactivityBinding;
import com.shakticonsultant.responsemodel.FaqsResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQActivity extends AppCompatActivity {

    ActivityFaqactivityBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        cd = new ConnectionDetector(FAQActivity.this);


        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {

            getFaqList();


        }
    }
        public void getFaqList() {
            binding.progressBarFaq.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
            // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<FaqsResponse> resultCall = apiInterface.callFaqsList();

            resultCall.enqueue(new Callback<FaqsResponse>() {
                @Override
                public void onResponse(Call<FaqsResponse> call, Response<FaqsResponse> response) {

                    if (response.isSuccessful()) {
                        binding.progressBarFaq.setVisibility(View.GONE);

                        //  lemprtNotification.setVisibility(View.GONE);
                        if (response.body().isSuccess()==true) {

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FAQActivity.this);
                            binding.recyclerFaqs.setLayoutManager(linearLayoutManager);
                            FaqListAdapter adapter=new FaqListAdapter(FAQActivity.this,response.body().getData());
                            binding.recyclerFaqs.setAdapter(adapter);
                            //  binding.recyclerFaqs.getAdapter().notifyDataSetChanged();

                        } else {

                            binding.progressBarFaq.setVisibility(View.GONE);

                            Utils.showFailureDialog(FAQActivity.this, "No Data Found");
                        }
                    }
                }

                @Override
                public void onFailure(Call<FaqsResponse> call, Throwable t) {
                    binding.progressBarFaq.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.VISIBLE);
                    //    pd_loading.setVisibility(View.GONE);
                    Utils.showFailureDialog(FAQActivity.this, "Something went wrong!");
                }
            });
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(FAQActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}