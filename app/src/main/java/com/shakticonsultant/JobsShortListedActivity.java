package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.shakticonsultant.Interface.FevInterface;
<<<<<<< HEAD
import com.shakticonsultant.adapter.ShortListAdapter;
import com.shakticonsultant.databinding.ActivityJobsShortListedBinding;
import com.shakticonsultant.responsemodel.FavouriteResponse;
=======
import com.shakticonsultant.adapter.AppliedJobListAdapter;
import com.shakticonsultant.adapter.ShortListAdapter;
import com.shakticonsultant.databinding.ActivityJobsShortListedBinding;
import com.shakticonsultant.responsemodel.FavouriteResponse;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
>>>>>>> github/main
import com.shakticonsultant.responsemodel.ShortListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobsShortListedActivity extends AppCompatActivity {

    ActivityJobsShortListedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsShortListedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView21.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        getShortListApi();

    }


    public void getShortListApi() {
        binding.progressBarcategory.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(JobsShortListedActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ShortListResponse> resultCall = apiInterface.callShortListApi(map);

        resultCall.enqueue(new Callback<ShortListResponse>() {
            @Override
            public void onResponse(Call<ShortListResponse> call, Response<ShortListResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarcategory.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerView4.setVisibility(View.VISIBLE);
                        binding.lEmpty.setVisibility(View.GONE);
                        //binding.btnApply.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JobsShortListedActivity.this);
                        binding.recyclerView4.setLayoutManager(linearLayoutManager);
                        ShortListAdapter adapter=new ShortListAdapter(JobsShortListedActivity.this, response.body().getData(), new FevInterface() {
                            @Override
<<<<<<< HEAD
                            public void getFavResponse(Response<FavouriteResponse> respnse) {

                                getShortListApi();
=======
                            public void getFavResponse(FavouriteResponse respnse) {

>>>>>>> github/main

                            }
                        });
                        binding.recyclerView4.setAdapter(adapter);
                        binding.recyclerView4.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarcategory.setVisibility(View.GONE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerView4.setVisibility(View.GONE);
                      //  binding.btnApply.setVisibility(View.GONE);
                        binding.lEmpty.setVisibility(View.VISIBLE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else
                {
                  //  binding.btnApply.setVisibility(View.GONE);

                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerView4.setVisibility(View.GONE);
                    binding.lEmpty.setVisibility(View.VISIBLE);
                    binding.progressBarcategory.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ShortListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.imageView23.setVisibility(View.GONE);
                binding.recyclerView4.setVisibility(View.GONE);
                binding.lEmpty.setVisibility(View.VISIBLE);
              //  binding.btnApply.setVisibility(View.GONE);
                binding.progressBarcategory.setVisibility(View.GONE);

                Utils.showFailureDialog(JobsShortListedActivity.this, "Please try again sometime later..");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getShortListApi();
    }
}