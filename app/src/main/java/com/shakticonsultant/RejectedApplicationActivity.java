package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shakticonsultant.adapter.AppliedJobListAdapter;
import com.shakticonsultant.adapter.RejectedJobListAdapter;
import com.shakticonsultant.databinding.ActivityRejectedApplicationBinding;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
import com.shakticonsultant.responsemodel.JobRejectedListResponse;
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

public class RejectedApplicationActivity extends AppCompatActivity {

    ActivityRejectedApplicationBinding binding;
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRejectedApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(RejectedApplicationActivity.this);
        getRejectedJobList();
        binding.imgBackArrow.setOnClickListener(v -> {

            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);

        });

    }


    public void getRejectedJobList() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();
      //  Toast.makeText(this, ""+AppPrefrences.getUserid(RejectedApplicationActivity.this), Toast.LENGTH_SHORT).show();
        //binding.progressrejected.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(RejectedApplicationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobRejectedListResponse> resultCall = apiInterface.callRejectedJobList(map);

        resultCall.enqueue(new Callback<JobRejectedListResponse>() {
            @Override
            public void onResponse(Call<JobRejectedListResponse> call, Response<JobRejectedListResponse> response) {

                if (response.isSuccessful()) {
                   // binding.progressrejected.setVisibility(View.GONE);
progress_spinner.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.lEmpty.setVisibility(View.GONE);
                        binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerView9.setVisibility(View.VISIBLE);


                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RejectedApplicationActivity.this);
                        binding.recyclerView9.setLayoutManager(linearLayoutManager);
                        RejectedJobListAdapter adapter=new RejectedJobListAdapter(RejectedApplicationActivity.this,response.body().getData());
                        binding.recyclerView9.setAdapter(adapter);
                        binding.recyclerView9.getAdapter().notifyDataSetChanged();



                    } else {
                     //   binding.progressrejected.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        binding.lEmpty.setVisibility(View.VISIBLE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerView9.setVisibility(View.GONE);
                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{

                 //   binding.progressrejected.setVisibility(View.GONE);
                    progress_spinner.dismiss();

                    binding.lEmpty.setVisibility(View.VISIBLE);
                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerView9.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JobRejectedListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
               // binding.progressrejected.setVisibility(View.GONE);
                binding.lEmpty.setVisibility(View.VISIBLE);

                progress_spinner.dismiss();

                Utils.showFailureDialog(RejectedApplicationActivity.this, "Please try again sometime later..");
            }
        });
    }
}