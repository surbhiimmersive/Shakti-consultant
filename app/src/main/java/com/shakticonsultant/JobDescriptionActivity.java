package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

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


import com.shakticonsultant.databinding.ActivityJobDescriptionBinding;

import java.util.Calendar;

public class JobDescriptionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ActivityJobDescriptionBinding binding;
    DatePickerDialog datePickerDialog;

        boolean isSelected;




    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         datePickerDialog = new DatePickerDialog(
                 JobDescriptionActivity.this, this, Calendar.getInstance().get(Calendar.YEAR) , Calendar.getInstance().get(Calendar.MONDAY),
                Calendar.getInstance().get(Calendar.DATE));

        binding.btnJobDetails.setOnClickListener(v -> {
            setButtonSelected(binding.btnJobDetails, binding.btnSimilarJobs);
            binding.layoutJobDescription.setVisibility(View.VISIBLE);
            binding.layoutSimilarJobs.setVisibility(View.GONE);

        });




        binding.imgSearch.setOnClickListener(v -> {

            if (!isSelected) {
                isSelected=true;
                binding.imgSearch.setImageResource(R.drawable.like_img);
            }else  {
                isSelected =false;
                binding.imgSearch.setImageResource(R.drawable.favourite_icon);
            }


        });

        binding.btnSimilarJobs.setOnClickListener(v -> {
            setButtonSelected(binding.btnSimilarJobs, binding.btnJobDetails);
            binding.layoutJobDescription.setVisibility(View.GONE);
            binding.layoutSimilarJobs.setVisibility(View.VISIBLE);

        });


        binding.btnApply.setOnClickListener(v -> {
            showSubscriptionDialog();
        });

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.shareVia.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Share this app.");
            startActivity(Intent.createChooser(intent, "Share using"));
        });


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



    private void showSubscriptionDialog(){
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_subscription_plan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_ok);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
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
}