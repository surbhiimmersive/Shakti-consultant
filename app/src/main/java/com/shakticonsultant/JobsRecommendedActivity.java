package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.ActivityJobsRecommendedBinding;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsRecommendedActivity extends AppCompatActivity {

    ActivityJobsRecommendedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsRecommendedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getJobSkillWiseList();
        binding.imageBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }


    public void getJobSkillWiseList() {
        binding.progressBarSkillWise.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("skill_id", AppPrefrences.getSkillId(JobsRecommendedActivity.this));
        map.put("user_id", AppPrefrences.getUserid(JobsRecommendedActivity.this));


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

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JobsRecommendedActivity.this);
                        binding.recyclerJobSkillWiseList.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter=new JobSkillWiseListAdapter(JobsRecommendedActivity.this,response.body().getData());
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

                Utils.showFailureDialog(JobsRecommendedActivity.this, "Something went wrong!");
            }
        });
    }
}