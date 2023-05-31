package com.shakticonsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityRateUsBinding;
import com.shakticonsultant.databinding.ActivityUpdateResumeBinding;
import com.shakticonsultant.responsemodel.ProfileResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditResumeActivity extends AppCompatActivity {
    String name,email,mobile,img;

    ActivityUpdateResumeBinding binding;
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateResumeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        cd=new ConnectionDetector(EditResumeActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {

            getprofiledata();

            if(AppPrefrences.getExperience(EditResumeActivity.this).equals("Fresher")){

                binding.btnEmployeeHistory.setVisibility(View.GONE);
            }else{
                binding.btnEmployeeHistory.setVisibility(View.VISIBLE);

            }
            //  getFaqList();

            binding.btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent( EditResumeActivity.this,GetPersonalInfoActivity.class));

                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);

                }
            });


            binding.btnAcademicDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent( EditResumeActivity.this,GetAcademicDetailsActivity.class));
                   overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            });
            binding.btnEmployeeHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent( EditResumeActivity.this,GetEmployeeHistoryActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);

                }
            });


        }

    }


    public void getprofiledata () {
        // binding..setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(EditResumeActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileResponse> resultCall = apiInterface.callgetProfileApi(map);

        resultCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressAbout.setVisibility(View.GONE);
                    binding.consprofile.setVisibility(View.VISIBLE);
                    if (response.body().isSuccess() == true) {
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());

                        /*binding.textView33.setText(AppPrefrences.getName(getActivity()));
                        binding.textView34.setText(AppPrefrences.getMail(getActivity()));
                        binding.textView35.setText(AppPrefrences.getMobile(getActivity()));
                        Picasso.get()
                                .load(ApiClient.Photourl+ AppPrefrences.getProfileImg(getActivity()))
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView14);

*/

                        name=response.body().getData().getName();
                        email=response.body().getData().getEmail();
                        mobile=response.body().getData().getMobile();
                        img=response.body().getData().getProfile_image();
                        binding.textView33.setText(response.body().getData().getName());
                        binding.textView34.setText(response.body().getData().getEmail());
                        binding.textView35.setText(response.body().getData().getMobile());
                        Picasso.get()
                                .load(ApiClient.Photourl+ response.body().getData().getProfile_image())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView14);
                    } else {
                        // Utils.showFailureDialog(AboutUsActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // binding.progressAbout.setVisibility(View.GONE);
                // Utils.showFailureDialog(AboutUsActivity.this, "Something went wrong!");
            }
        });
    }

}