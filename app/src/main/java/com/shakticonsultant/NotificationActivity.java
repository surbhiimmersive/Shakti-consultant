package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.adapter.NotificationAdapter;
import com.shakticonsultant.databinding.ActivityJobsRecommendedBinding;
import com.shakticonsultant.databinding.ActivityMainBinding;
import com.shakticonsultant.databinding.ActivityNotificationBinding;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.responsemodel.NotificationResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getNotificationAPi();
        binding.imageBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

    }


    public void getNotificationAPi() {
        binding.progressBarSkillWise.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<NotificationResponse> resultCall = apiInterface.callNotificationList(map);

        resultCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarSkillWise.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.tvEmpty.setVisibility(View.GONE);
                        binding.imgEmpty.setVisibility(View.GONE);
                       // binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
                        binding.recyclerJobSkillWiseList.setLayoutManager(linearLayoutManager);
                        NotificationAdapter adapter=new NotificationAdapter(NotificationActivity.this,response.body().getData());
                        binding.recyclerJobSkillWiseList.setAdapter(adapter);
                        binding.recyclerJobSkillWiseList.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarSkillWise.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.imgEmpty.setVisibility(View.VISIBLE);
                       // binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerJobSkillWiseList.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarSkillWise.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                  //  binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerJobSkillWiseList.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBarSkillWise.setVisibility(View.GONE);

                Utils.showFailureDialog(NotificationActivity.this, "Something went wrong!");
            }
        });
    }

}