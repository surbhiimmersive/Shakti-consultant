package com.shakticonsultant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.adapter.AppliedJobListAdapter;
import com.shakticonsultant.databinding.ActivityAcademicDetailsBinding;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedJobActivity extends AppCompatActivity {

ImageView imageView21,imageView23;
ProgressBar progressBarcategory;
RecyclerView recyclerAppliedJob;
LinearLayout lEmpty;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_applied_jobs);
        recyclerAppliedJob=(RecyclerView)findViewById(R.id.recyclerAppliedJob);
        imageView21=(ImageView) findViewById(R.id.imageView21);
        imageView23=(ImageView) findViewById(R.id.imageView23);
        imageView21.setOnClickListener(v -> {


            Intent i=new Intent(AppliedJobActivity.this,MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        getAppliedJob();

    }

    public void getAppliedJob() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(AppliedJobActivity.this);
        progress_spinner.show();

        //   progressBarcategory.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(AppliedJobActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobAppliedListResponse> resultCall = apiInterface.callAppliedJob(map);

        resultCall.enqueue(new Callback<JobAppliedListResponse>() {
            @Override
            public void onResponse(Call<JobAppliedListResponse> call, Response<JobAppliedListResponse> response) {

                if (response.isSuccessful()) {
                    //progressBarcategory.setVisibility(View.GONE);
progress_spinner.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        imageView23.setVisibility(View.VISIBLE);
                        recyclerAppliedJob.setVisibility(View.VISIBLE);
                        //lEmpty.setVisibility(View.GONE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppliedJobActivity.this);
                        recyclerAppliedJob.setLayoutManager(linearLayoutManager);
                        AppliedJobListAdapter adapter=new AppliedJobListAdapter(AppliedJobActivity.this,response.body().getData());
                        recyclerAppliedJob.setAdapter(adapter);
                        recyclerAppliedJob.getAdapter().notifyDataSetChanged();



                    } else {
                        progress_spinner.dismiss();

                        // progressBarcategory.setVisibility(View.GONE);
                        imageView23.setVisibility(View.GONE);
                        recyclerAppliedJob.setVisibility(View.GONE);
                      //  lEmpty.setVisibility(View.VISIBLE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else
                {progress_spinner.dismiss();

                    imageView23.setVisibility(View.GONE);
                    recyclerAppliedJob.setVisibility(View.GONE);
                  //  lEmpty.setVisibility(View.VISIBLE);
                   // progressBarcategory.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JobAppliedListResponse> call, Throwable t) {
                progress_spinner.dismiss();

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                imageView23.setVisibility(View.GONE);
                recyclerAppliedJob.setVisibility(View.GONE);
               // lEmpty.setVisibility(View.VISIBLE);
               // progressBarcategory.setVisibility(View.GONE);

                // Utils.showFailureDialog(getActivity(), "Please try again sometime later..");
            }
        });
    }

}
