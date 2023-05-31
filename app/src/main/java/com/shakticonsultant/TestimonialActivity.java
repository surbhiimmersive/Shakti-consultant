package com.shakticonsultant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.adapter.TestimonialListAdapter;
import com.shakticonsultant.databinding.ActivityPackageBinding;
import com.shakticonsultant.databinding.ActivityTestimonialBinding;
import com.shakticonsultant.responsemodel.PackageResponse;
import com.shakticonsultant.responsemodel.TestimonialResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimonialActivity extends AppCompatActivity {

    ActivityTestimonialBinding binding;
    DatePickerDialog datePickerDialog;
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestimonialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd=new ConnectionDetector(TestimonialActivity.this);


        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        else {

            getTestimonialListApi();

            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });

        }
    }


    public void getTestimonialListApi() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        //    binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<TestimonialResponse> resultCall = apiInterface.callTestimonialApi();

        resultCall.enqueue(new Callback<TestimonialResponse>() {
            @Override
            public void onResponse(Call<TestimonialResponse> call, Response<TestimonialResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                  //  binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TestimonialActivity.this);
                        binding.recyclerpackage.setLayoutManager(linearLayoutManager);
                        TestimonialListAdapter adapter=new TestimonialListAdapter(TestimonialActivity.this,response.body().getData());
                        binding.recyclerpackage.setAdapter(adapter);
                        binding.recyclerpackage.getAdapter().notifyDataSetChanged();

                    } else {
                       // binding.progressBarpackage.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<TestimonialResponse> call, Throwable t) {
              //  binding.progressBarpackage.setVisibility(View.GONE);
                progress_spinner.dismiss();

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(TestimonialActivity.this, "Something went wrong!");
            }
        });
    }
}