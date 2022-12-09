package com.shakticonsultant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.adapter.ScheduleInterviewAdapter;
import com.shakticonsultant.databinding.ActivityPackageBinding;
import com.shakticonsultant.databinding.ActivityScheduleInterviewBinding;
import com.shakticonsultant.responsemodel.PackageResponse;
import com.shakticonsultant.responsemodel.ScheduleInterviewResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import in.akshit.horizontalcalendar.HorizontalCalendarView;
import in.akshit.horizontalcalendar.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleInterviewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityScheduleInterviewBinding binding;
    DatePickerDialog datePickerDialog;
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleInterviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd=new ConnectionDetector(ScheduleInterviewActivity.this);


        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        else {

            getScheduleInterviewList("1");

            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
            });
            binding.btnToday.setOnClickListener(v -> {
                setButtonSelected(binding.btnToday, binding.btnUpcoming);
                getScheduleInterviewList("1");

            });
binding.btnUpcoming.setOnClickListener(v -> {
    setButtonSelected(binding.btnUpcoming, binding.btnToday);
    getScheduleInterviewList("1");


});

        }
    }

    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect){
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    public void getScheduleInterviewList(String type) {
          binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
         map.put("user_id", AppPrefrences.getUserid(ScheduleInterviewActivity.this));
         map.put("type", type);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ScheduleInterviewResponse> resultCall = apiInterface.callInterviewSchedule(map);

        resultCall.enqueue(new Callback<ScheduleInterviewResponse>() {
            @Override
            public void onResponse(Call<ScheduleInterviewResponse> call, Response<ScheduleInterviewResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.tvEmpty.setVisibility(View.GONE);
                        binding.imgEmpty.setVisibility(View.GONE);
                        binding.recyclerpackage.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ScheduleInterviewActivity.this);
                        binding.recyclerpackage.setLayoutManager(linearLayoutManager);
                        ScheduleInterviewAdapter adapter=new ScheduleInterviewAdapter(ScheduleInterviewActivity.this,response.body().getData());
                        binding.recyclerpackage.setAdapter(adapter);
                        binding.recyclerpackage.getAdapter().notifyDataSetChanged();

                    } else {
                        binding.progressBarpackage.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.imgEmpty.setVisibility(View.VISIBLE);
                        binding.recyclerpackage.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarpackage.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.recyclerpackage.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ScheduleInterviewResponse> call, Throwable t) {
                binding.progressBarpackage.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.imgEmpty.setVisibility(View.VISIBLE);
                binding.recyclerpackage.setVisibility(View.GONE);
                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(ScheduleInterviewActivity.this, "Something went wrong!");
            }
        });
    }
}