package com.shakticonsultant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.ActivitySpecificFacultyJobBinding;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
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

public class NewJobSearchListActivity extends AppCompatActivity {

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

        cd = new ConnectionDetector(NewJobSearchListActivity.this);

        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            skill_id = getIntent().getStringExtra("skill_id");
            skill_name = getIntent().getStringExtra("skill_name");
            city = getIntent().getStringExtra("city");
            experience = getIntent().getStringExtra("experience");
            min_salary = getIntent().getStringExtra("min_salary");
            max_salary = getIntent().getStringExtra("max_salary");


            Log.e("SKILLID",skill_id);
            Log.e("skill_name",skill_name);
            Log.e("city",city);
            Log.e("min_salary",min_salary);
            Log.e("max_salary",max_salary);

            binding.textView47.setText(skill_name);

Log.e("DATA",skill_id);
Log.e("skill_name",skill_name);
Log.e("experience",experience);
Log.e("min_salary",min_salary);
Log.e("max_salary",max_salary);


            binding.imageBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
            getJobFilterData();

            binding.imageViewFilter.setOnClickListener(v -> {
                Intent intent = new Intent(NewJobSearchListActivity.this, SearchJobFilterActivity.class);
                startActivityForResult(intent, 2);
            });

        }
    }
    public void getJobFilterData() {
        binding.progressBarSkillWise.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();


        map.put("location", city);
        map.put("min_salary",min_salary);
        map.put("max_salary", max_salary);
        map.put("experience", experience);
        map.put("skill_id", skill_id);
        map.put("user_id", AppPrefrences.getUserid(NewJobSearchListActivity.this));


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

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewJobSearchListActivity.this);
                        binding.recyclerJobSkillWiseList.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter=new JobSkillWiseListAdapter(NewJobSearchListActivity.this,response.body().getData());
                        binding.recyclerJobSkillWiseList.setAdapter(adapter);
                        binding.recyclerJobSkillWiseList.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarSkillWise.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                       binding.imgEmpty.setVisibility(View.VISIBLE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarSkillWise.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerJobSkillWiseList.setVisibility(View.GONE);

                 /*   Snackbar snackbar = Snackbar.make(binding.getRoot(), response.body().getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();*/
                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBarSkillWise.setVisibility(View.GONE);


                //  Utils.showFailureDialog(NewJobSearchListActivity.this, "Something went wrong!");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            try{
                city = data.getStringExtra("city");
                experience = data.getStringExtra("experience");
                min_salary = data.getStringExtra("min_salary");
                max_salary = data.getStringExtra("max_salary");
                skill_id = data.getStringExtra("skill_id");
                skill_name = data.getStringExtra("skill_name");
                //Toast.makeText(this, "Name"+experience, Toast.LENGTH_SHORT).show();

                getJobFilterData();
            }
            catch (Exception e){}
        }



    }
}