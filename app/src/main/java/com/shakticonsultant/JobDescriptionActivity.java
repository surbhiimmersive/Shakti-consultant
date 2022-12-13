package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;


import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobSkillListAdapter;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.ActivityJobDescriptionBinding;
import com.shakticonsultant.responsemodel.JobDetailResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDescriptionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityJobDescriptionBinding binding;
    DatePickerDialog datePickerDialog;
    String job_id,skill_name,skill_id="";
    boolean isSelected;
    ConnectionDetector cd;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        job_id = getIntent().getStringExtra("job_id");
        skill_name = getIntent().getStringExtra("skill_name");
        skill_id = getIntent().getStringExtra("skill_id");
cd=new ConnectionDetector(JobDescriptionActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {

            binding.textView50.setText(skill_name);


            getJobDetailApi();
            datePickerDialog = new DatePickerDialog(
                    JobDescriptionActivity.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY),
                    Calendar.getInstance().get(Calendar.DATE));

            binding.btnJobDetails.setOnClickListener(v -> {
                setButtonSelected(binding.btnJobDetails, binding.btnSimilarJobs);
                binding.layoutJobDescription.setVisibility(View.VISIBLE);
                binding.layoutSimilarJobs.setVisibility(View.GONE);

            });

            binding.imgSearch.setOnClickListener(v -> {

                if (!isSelected) {
                    isSelected = true;
                    binding.imgSearch.setImageResource(R.drawable.like_img);
                } else {
                    isSelected = false;
                    binding.imgSearch.setImageResource(R.drawable.favourite_icon);
                }


            });

            binding.btnSimilarJobs.setOnClickListener(v -> {
                setButtonSelected(binding.btnSimilarJobs, binding.btnJobDetails);
                binding.layoutJobDescription.setVisibility(View.GONE);
                binding.layoutSimilarJobs.setVisibility(View.VISIBLE);
                //getSimilarJobList(job_id);
                getSimilarJobList();
            });




            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });

            binding.shareVia.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Share this app.");
                startActivity(Intent.createChooser(intent, "Share using"));
            });

        }
    }


    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect){
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }


    private boolean setImageSelected(ImageView imageToSelect,ImageView imageToDeSelect){


        imageToSelect.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.main_text_color));
        imageToDeSelect.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
        return true;
    }

    public void getJobDetailApi() {
        binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("job_id", job_id);
        map.put("user_id",AppPrefrences.getUserid(JobDescriptionActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobDetailResponse> resultCall = apiInterface.callJobDetail(map);

        resultCall.enqueue(new Callback<JobDetailResponse>() {
            @Override
            public void onResponse(Call<JobDetailResponse> call, Response<JobDetailResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBardetail.setVisibility(View.GONE);
                    binding.const1.setVisibility(View.VISIBLE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                      /*  GridLayoutManager linearLayoutManager = new GridLayoutManager(JobsListActivity.this,2);
                        binding.recycleSkillList.setLayoutManager(linearLayoutManager);
                        JobSkillListAdapter adapter=new JobSkillListAdapter(JobsListActivity.this,response.body().getData());
                        binding.recycleSkillList.setAdapter(adapter);
                        binding.recycleSkillList.getAdapter().notifyDataSetChanged();

*/
binding.tvrange.setText(response.body().getData().get(0).getStarting_salary()+" "+response.body().getData().get(0).getPay_according());
binding.tvExperience.setText(response.body().getData().get(0).getWork_experience());
binding.tvLocation.setText(response.body().getData().get(0).getLocation());
binding.textView51.setText(response.body().getData().get(0).getJob_description());
binding.tvdocans.setText(response.body().getData().get(0).getDocument_required());
binding.tvimportant.setText(response.body().getData().get(0).getImportant_instructions());


                        binding.btnApply.setOnClickListener(v -> {

                            if(response.body().getData().get(0).getPackage_balance()==0){
                                showSubscriptionDialog(response.body().getData().get(0).getId());
                            }else{


                                showDateDialog();

                            }


                        });
                    } else {
                        binding.progressBardetail.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                         Utils.showFailureDialog(JobDescriptionActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobDetailResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBardetail.setVisibility(View.GONE);

                Utils.showFailureDialog(JobDescriptionActivity.this, "Something went wrong!");
            }
        });
    }

    private void showDateDialog() {
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_select_interview_date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton date1 = dialog.findViewById(R.id.btn_select_date_1);
        AppCompatButton date2 = dialog.findViewById(R.id.btn_select_date_2);
        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);

        date1.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        date2.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmationDialog();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }


    private void showConfirmationDialog(){
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
        });

        faq.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }



    private void showSubscriptionDialog(String job_id){
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_subscription_plan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_ok);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            //startActivity(new Intent(getApplicationContext(), ApplyJobPackageActivity.class));


            Intent i=new Intent(JobDescriptionActivity.this,ApplyJobPackageActivity.class);
            i.putExtra("job_id",job_id);
            startActivity(i);
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            //showDateDialog();
        });
    }



//    private void showDateDialog(){
//        Dialog dialog = new Dialog(JobDescriptionActivity.this);
//        dialog.setContentView(R.layout.dialog_select_interview_date);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.show();
//
//        AppCompatButton date1 = dialog.findViewById(R.id.btn_select_date_1);
//        AppCompatButton date2 = dialog.findViewById(R.id.btn_select_date_2);
//        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
//        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);
//
//        date1.setOnClickListener(v -> {
//            datePickerDialog.show();
//        });
//
//        date2.setOnClickListener(v -> {
//            datePickerDialog.show();
//        });
//
//        confirm.setOnClickListener(v -> {
//            dialog.dismiss();
//            showConfirmationDialog();
//        });
//
//        cancel.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//
//
//        AppCompatButton ok = dialog.findViewById(R.id.btn_ok);
//        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);
//
//        ok.setOnClickListener(v -> {
//            dialog.dismiss();
//            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
//        });
//
//        cancel.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//    }

//    private void showConfirmationDialog(){
//        Dialog dialog = new Dialog(JobDescriptionActivity.this);
//        dialog.setContentView(R.layout.dialog_interview_further_process);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.show();
//
//        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
//        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);
//
//        ok.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//        faq.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    public void getSimilarJobList() {
        binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("skill_id", AppPrefrences.getSkillId(JobDescriptionActivity.this));
        map.put("user_id", AppPrefrences.getUserid(JobDescriptionActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callJobSkillWiseList(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBardetail.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JobDescriptionActivity.this);
                        binding.recyclerView7.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter=new JobSkillWiseListAdapter(JobDescriptionActivity.this,response.body().getData());
                        binding.recyclerView7.setAdapter(adapter);
                        binding.recyclerView7.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBardetail.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBardetail.setVisibility(View.GONE);

                Utils.showFailureDialog(JobDescriptionActivity.this, "Something went wrong!");
            }
        });
    }
}