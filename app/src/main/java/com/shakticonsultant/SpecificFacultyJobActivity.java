package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobSkillListAdapter;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.adapter.OrganizationAdapter;
import com.shakticonsultant.databinding.ActivitySpecificFacultyJobBinding;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.FileHelper;
import com.shakticonsultant.utils.PathUtil;
import com.shakticonsultant.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificFacultyJobActivity extends AppCompatActivity {

    ActivitySpecificFacultyJobBinding binding;
    private int selectedPos;

    private Boolean txtoneSelected = false;
    private Boolean txttwoSelected = false;
    private Boolean txtthreeSelected = false;
String skill_id;
String skill_name;
ConnectionDetector cd;
    String city,experience,min_salary,max_salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecificFacultyJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = new ConnectionDetector(SpecificFacultyJobActivity.this);

        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {
            skill_id = getIntent().getStringExtra("skill_id");
            skill_name = getIntent().getStringExtra("skill_name");
            binding.textView47.setText(skill_name);
            binding.imageBackArrow.setOnClickListener(v -> {
                onBackPressed();
            });
            getJobSkillWiseList();

            binding.imageViewFilter.setOnClickListener(v -> {
                Intent intent = new Intent(SpecificFacultyJobActivity.this, JobFiltersActivity.class);
                startActivityForResult(intent, 2);
            });



/*
            binding.include10.btnView.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), JobDescriptionActivity.class));
            });

            binding.imgchngpg.setOnClickListener(v -> {


            });

            binding.txtonepage.setOnClickListener(v -> {
                setTextSelected(binding.txtonepage, binding.txttwopage);
                setTextSelected(binding.txtonepage, binding.txtthreepage);
            });
            binding.txttwopage.setOnClickListener(v -> {
                setTextSelected(binding.txttwopage, binding.txtthreepage);
                setTextSelected(binding.txttwopage, binding.txtonepage);
            });

            binding.txtthreepage.setOnClickListener(v -> {
                setTextSelected(binding.txtthreepage, binding.txttwopage);
                setTextSelected(binding.txtthreepage, binding.txtonepage);
            });*/
        }
    }

   /* private void setTextSelectedPos(int selectedPos) {
        if (selectedPos==0) {
            binding.txtonepage.setOnClickListener(v -> {
                setTextSelected(binding.txtonepage, binding.txttwopage);
                setTextSelected(binding.txtonepage, binding.txtthreepage);
            });
        } else if (selectedPos==1){
            binding.txttwopage.setOnClickListener(v -> {
                setTextSelected(binding.txttwopage, binding.txtthreepage);
                setTextSelected(binding.txttwopage, binding.txtonepage);
            });
        } else if (selectedPos==2){
            binding.txtthreepage.setOnClickListener(v -> {
                setTextSelected(binding.txtthreepage, binding.txttwopage);
                setTextSelected(binding.txtthreepage, binding.txtonepage);
            });


        }*/
  /*

    private void setTextSelected(TextView textToSelect, TextView textDeselect){
        textToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        textToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        textDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        textDeselect.setTextColor(Color.parseColor("#000000"));
    }
    */

    public void getJobSkillWiseList() {
          binding.progressBarSkillWise.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("skill_id", skill_id);
        map.put("user_id", AppPrefrences.getUserid(SpecificFacultyJobActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callJobSkillWiseList(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarSkillWise.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.tvEmpty.setVisibility(View.GONE);
                        binding.imgEmpty.setVisibility(View.GONE);
                        binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpecificFacultyJobActivity.this);
                        binding.recyclerJobSkillWiseList.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter=new JobSkillWiseListAdapter(SpecificFacultyJobActivity.this,response.body().getData());
                        binding.recyclerJobSkillWiseList.setAdapter(adapter);
                        binding.recyclerJobSkillWiseList.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarSkillWise.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.imgEmpty.setVisibility(View.VISIBLE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarSkillWise.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerJobSkillWiseList.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBarSkillWise.setVisibility(View.GONE);

            //    Utils.showFailureDialog(SpecificFacultyJobActivity.this, "Something went wrong!");
            }
        });
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            city=data.getStringExtra("city");
            experience=data.getStringExtra("experience");
            min_salary=data.getStringExtra("min_salary");
            max_salary=data.getStringExtra("max_salary");
             //Toast.makeText(this, "Name"+experience, Toast.LENGTH_SHORT).show();

            getJobFilterData();
        }



    }

    public void getJobFilterData() {
        binding.progressBarSkillWise.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("location", city);
        map.put("min_salary",min_salary);
        map.put("max_salary", max_salary);
        map.put("experience", experience);
        map.put("user_id", AppPrefrences.getUserid(SpecificFacultyJobActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callFilterApi(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarSkillWise.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.tvEmpty.setVisibility(View.GONE);
                        binding.imgEmpty.setVisibility(View.GONE);
                        binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SpecificFacultyJobActivity.this);
                        binding.recyclerJobSkillWiseList.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter=new JobSkillWiseListAdapter(SpecificFacultyJobActivity.this,response.body().getData());
                        binding.recyclerJobSkillWiseList.setAdapter(adapter);
                        binding.recyclerJobSkillWiseList.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarSkillWise.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                       binding.imgEmpty.setVisibility(View.VISIBLE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarSkillWise.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerJobSkillWiseList.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBarSkillWise.setVisibility(View.GONE);

                Utils.showFailureDialog(SpecificFacultyJobActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }
}