package com.shakticonsultant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobCategoryAdapter;
import com.shakticonsultant.adapter.JobSkillListAdapter;
import com.shakticonsultant.databinding.ActivityJobsListBinding;
import com.shakticonsultant.responsemodel.JobCategoryResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsListActivity extends AppCompatActivity {

    ActivityJobsListBinding binding;
    String category_id;
    String category_name;
    ConnectionDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cd = new ConnectionDetector(JobsListActivity.this);

        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {

            binding.imageView16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    startActivity(new Intent(JobsListActivity.this, NotificationActivity.class));

                }
            });
            category_id = getIntent().getStringExtra("category_id");
            category_name = getIntent().getStringExtra("category_name");
            binding.textView45.setText(category_name);

            binding.imgBack.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });



     /*   binding.include.getRoot().setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SpecificFacultyJobActivity.class));
        });*/
            getJobSkillList();
        }
        binding.imageView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(JobsListActivity.this, NotificationActivity.class));

            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);

    }

    public void getJobSkillList() {


        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();


        //   binding.progressBarSkill.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("category_id", category_id);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillResponse> resultCall = apiInterface.callJobSkillList(map);

        resultCall.enqueue(new Callback<JobSkillResponse>() {
            @Override
            public void onResponse(Call<JobSkillResponse> call, Response<JobSkillResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressBarSkill.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        progress_spinner.dismiss();
                        binding.lEmpty.setVisibility(View.GONE);
                        binding.recycleSkillList.setVisibility(View.VISIBLE);

                        GridLayoutManager linearLayoutManager = new GridLayoutManager(JobsListActivity.this, 2);
                        binding.recycleSkillList.setLayoutManager(linearLayoutManager);
                        JobSkillListAdapter adapter = new JobSkillListAdapter(JobsListActivity.this, response.body().getData());
                        binding.recycleSkillList.setAdapter(adapter);
                        binding.recycleSkillList.getAdapter().notifyDataSetChanged();


                    } else {
                        // binding.progressBarSkill.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                } else {
                    //   binding.progressBarSkill.setVisibility(View.GONE);

                    binding.lEmpty.setVisibility(View.VISIBLE);
                    binding.recycleSkillList.setVisibility(View.GONE);
                    progress_spinner.dismiss();

                }
            }

            @Override
            public void onFailure(Call<JobSkillResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //binding.progressBarSkill.setVisibility(View.GONE);
                progress_spinner.dismiss();

                binding.lEmpty.setVisibility(View.VISIBLE);
                binding.recycleSkillList.setVisibility(View.GONE);
                Utils.showFailureDialog(JobsListActivity.this, "Something went wrong!");
            }
        });
    }
}