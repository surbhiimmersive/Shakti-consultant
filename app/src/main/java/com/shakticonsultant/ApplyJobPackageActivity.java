package com.shakticonsultant;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.ApplyJobPackageListAdapter;
import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.databinding.ActivityPackageBinding;
import com.shakticonsultant.responsemodel.PackageResponse;
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

public class ApplyJobPackageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityPackageBinding binding;
    DatePickerDialog datePickerDialog;
ConnectionDetector cd;
String job_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd=new ConnectionDetector(ApplyJobPackageActivity.this);

        job_id=getIntent().getStringExtra("job_id");
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }
        else {

            getPackageListApi();
            datePickerDialog = new DatePickerDialog(
                    ApplyJobPackageActivity.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY),
                    Calendar.getInstance().get(Calendar.DATE));

            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
            binding.btnUpgradePackage.setOnClickListener(v -> {
               // showDateDialog();
                getPackageListApi();
            });
        }
    }


    private void showDateDialog() {
        Dialog dialog = new Dialog(ApplyJobPackageActivity.this);
        dialog.setContentView(R.layout.dialog_select_interview_date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
        Dialog dialog = new Dialog(ApplyJobPackageActivity.this);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    public void getPackageListApi() {
          binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(ApplyJobPackageActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<PackageResponse> resultCall = apiInterface.callPackageList(map);

        resultCall.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ApplyJobPackageActivity.this);
                        binding.recyclerpackage.setLayoutManager(linearLayoutManager);
                        ApplyJobPackageListAdapter adapter=new ApplyJobPackageListAdapter(ApplyJobPackageActivity.this,response.body().getData(),job_id);
                        binding.recyclerpackage.setAdapter(adapter);
                        binding.recyclerpackage.getAdapter().notifyDataSetChanged();

                    } else {
                        binding.progressBarpackage.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                binding.progressBarpackage.setVisibility(View.GONE);

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
              //  Utils.showFailureDialog(ApplyJobPackageActivity.this, "Something went wrong!");
            }
        });
    }

}