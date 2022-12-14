package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityResumeBinding;
import com.shakticonsultant.responsemodel.GetAcademicDetailResponse;
import com.shakticonsultant.responsemodel.GetEmployeeHistoryResponse;
import com.shakticonsultant.responsemodel.GetPersonalInformationResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeActivity extends AppCompatActivity {

    ActivityResumeBinding binding;
   ConnectionDetector cd;
   File imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResumeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(ResumeActivity.this);

if(!cd.isConnectingToInternet()){
    Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.RED)
            .show();

}else {

    getPersonalInformation();
    getAcademicdata();
    getEmployeeHistoryData();
    binding.constraintprofile.setVisibility(View.VISIBLE);
binding.imageView392.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        share(screenShot(binding.getRoot()));


    }
});
    binding.imageView39.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
startActivity(new Intent(ResumeActivity.this,EditResumeActivity.class));
        }
    });
    binding.imgBackArrow.setOnClickListener(v -> {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    });

    binding.btnUpdate.setOnClickListener(v -> {


        ProfileFragment frag = new ProfileFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(frag,"Test Fragment");
        transaction.commit();

    });
}
    }
//////----------GetAcademic Data----------------------------
    public void getAcademicdata () {
        binding.progressResume.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(ResumeActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetAcademicDetailResponse> resultCall = apiInterface.callGetAcademicDetails(map);

        resultCall.enqueue(new Callback<GetAcademicDetailResponse>() {
            @Override
            public void onResponse(Call<GetAcademicDetailResponse> call, Response<GetAcademicDetailResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressResume.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        //   binding.edtXPercent.setText(response.body().getData().getPercentage_X());

                        if (!(response.body().getData().getUniversity_postgraduation()==null))
                        {
                            binding.tvPostdegeree.setVisibility(View.VISIBLE);
                            binding.tvPostdegeree.setText("• " + response.body().getData().getDegree_postgraduation() + " | " + "Passed Year: " + response.body().getData().getPassed_year_postgraduation() + " | " + " Percentage: " + response.body().getData().getPassed_year_postgraduation() + " | " + " Specilization: " + response.body().getData().getSpecialization_postgraduation() + " | " + " University Name: " + response.body().getData().getUniversity_postgraduation());
                    }else {
                        binding.tvPostdegeree.setText("• " + response.body().getData().getDegree_postgraduation() + " | " + "Passed Year: " + response.body().getData().getPassed_year_postgraduation() + " | " + " Percentage: " + response.body().getData().getPassed_year_postgraduation() + " | " + " Specilization: " + response.body().getData().getSpecialization_postgraduation() + " | " + " University Name: " + response.body().getData().getUniversity_postgraduation());
                        binding.tvPostdegeree.setVisibility(View.GONE);

                    }
binding.textView86.setText(response.body().getData().getDegree_graduation());
                        binding.tvdegeree.setText("• "+response.body().getData().getDegree_graduation()+" | "+"Passed Year: "+response.body().getData().getPassed_year_graduation()+" | "+" Percentage: "+response.body().getData().getPercentage_graduation() +" | "+" Specilization: "+response.body().getData().getSpecialization_graduation()+" | "+" University Name: "+response.body().getData().getUniversity_graduation());
binding.tv12degree.setText("• 12th Standard: "+response.body().getData().getBoard_XII()+" | " +" Passed Year: "+response.body().getData().getPassed_year_XII()+" | "+" Percentage: "+ response.body().getData().getPercentage_XII());
binding.tv10thboard.setText("• 10th Standard: "+response.body().getData().getBoard_X()+" | " +" Passed Year: "+response.body().getData().getPassed_year_X()+" | "+" Percentage: "+ response.body().getData().getPercentage_X());

                    } else {
                        //Utils.showFailureDialog(ResumeActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<GetAcademicDetailResponse> call, Throwable t) {
                binding.progressResume.setVisibility(View.GONE);
              //  Utils.showFailureDialog(ResumeActivity.this, "Something went wrong!");
            }
        });
    }

    //-------------GetPersonalInformation---------------



    public void getPersonalInformation () {
        binding.progressResume.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(ResumeActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetPersonalInformationResponse> resultCall = apiInterface.callgetPersonalInformation(map);

        resultCall.enqueue(new Callback<GetPersonalInformationResponse>() {
            @Override
            public void onResponse(Call<GetPersonalInformationResponse> call, Response<GetPersonalInformationResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressResume.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

binding.textView89.setText(response.body().getData().getCity());
binding.textView85.setText(response.body().getData().getName());
binding.textView87.setText(response.body().getData().getEmail());
binding.textView88.setText(response.body().getData().getPhone());


                        Picasso.get()
                                .load(ApiClient.Photourl+response.body().getData().getProfile_image())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView46);



                    } else {
                       // Utils.showFailureDialog(ResumeActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<GetPersonalInformationResponse> call, Throwable t) {
                binding.progressResume.setVisibility(View.GONE);
                //Utils.showFailureDialog(ResumeActivity.this, "Something went wrong!");
            }
        });
    }

    public void getEmployeeHistoryData () {
        binding.progressResume.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id",AppPrefrences.getUserid(ResumeActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetEmployeeHistoryResponse> resultCall = apiInterface.callGetEmployeeHistory(map);

        resultCall.enqueue(new Callback<GetEmployeeHistoryResponse>() {
            @Override
            public void onResponse(Call<GetEmployeeHistoryResponse> call, Response<GetEmployeeHistoryResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressResume.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        /*String content = response.body().getData().getContent();
                        binding.progressemployee.setText(content);
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                   */

                        binding.textView94.setText("• "+ response.body().getData().getCurrent_stream() +", "+response.body().getData().getCurrent_date_of_joining()+" - "+"Present");
                        binding.tvcurrentname.setText("• Current Organization Name: "+ response.body().getData().getCurrent_organisation());

                       if(!(response.body().getData().getFirst_organisation()==null)) {
                           binding.tvFirstOrgaStream.setText("• " + response.body().getData().getFirst_stream() + ", " + response.body().getData().getFirst_date_of_joining() + " - " + response.body().getData().getFirst_date_of_reliving());
                           binding.tvfirstorganization.setText("• Organization Name: " + response.body().getData().getFirst_organisation());
                       }
                       else{
                           binding.tvFirstOrgaStream.setVisibility(View.GONE);
                           binding.tvfirstorganization.setVisibility(View.GONE);
                       }

                        if(!(response.body().getData().getSecond_organisation()==null)) {
                            binding.tvSecondStream.setText("• " + response.body().getData().getSecond_stream() + ", " + response.body().getData().getSecond_date_of_joining() + " - " + response.body().getData().getSecond_date_of_reliving());
                            binding.tvSecondOrganization.setText("• Organization Name: " + response.body().getData().getSecond_organisation());
                        }
                        else{
                            binding.tvSecondStream.setVisibility(View.GONE);
                            binding.tvSecondOrganization.setVisibility(View.GONE);
                        }
                   if(!(response.body().getData().getThird_organisation()==null)) {

                       binding.tvThirdStream.setText("• " + response.body().getData().getThird_stream() + ", " + response.body().getData().getThird_date_of_joining() + " - " + response.body().getData().getThird_date_of_reliving());
                       binding.tvThirdOrganization.setText("• Organization Name: " + response.body().getData().getThird_organisation());
                   }
                        else{
                            binding.tvThirdStream.setVisibility(View.GONE);
                            binding.tvThirdOrganization.setVisibility(View.GONE);
                        }

                    } else {
                        //Utils.showFailureDialog(GetEmployeeHistoryActivity.this, response.body().getMessage());
                        binding.progressResume.setVisibility(View.GONE);


                    }
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeHistoryResponse> call, Throwable t) {
                binding.progressResume.setVisibility(View.GONE);
            //    Utils.showFailureDialog(GetEmployeeHistoryActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPersonalInformation();
        getAcademicdata();
        getEmployeeHistoryData();
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void share(Bitmap bitmap){
        String pathofBmp=
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        bitmap,"title", null);
        Uri uri = Uri.parse(pathofBmp);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        ResumeActivity.this.startActivity(Intent.createChooser(shareIntent, "hello hello"));
    }

}