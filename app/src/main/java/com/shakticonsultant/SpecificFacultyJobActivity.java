package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.shakticonsultant.databinding.ActivitySpecificFacultyJobBinding;

public class SpecificFacultyJobActivity extends AppCompatActivity {

    ActivitySpecificFacultyJobBinding binding;
    private int selectedPos;

    private Boolean txtoneSelected = false;
    private Boolean txttwoSelected = false;
    private Boolean txtthreeSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecificFacultyJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.imageViewFilter.setOnClickListener(v -> {
            startActivity(new Intent(SpecificFacultyJobActivity.this, JobFiltersActivity.class));
        });

        binding.include10.btnView.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), JobDescriptionActivity.class));
        });

        binding.imgchngpg.setOnClickListener(v -> {


        });

        binding.txtonepage.setOnClickListener(v -> {
            setTextSelected(binding.txtonepage, binding.txttwopage);
            setTextSelected(binding.txtonepage, binding.txtthreepage);
        });
        binding.txttwopage.setOnClickListener(v -> {
            setTextSelected(binding.txttwopage, binding.txtthreepage);
            setTextSelected(binding.txttwopage, binding.txtonepage);
        });

        binding.txtthreepage.setOnClickListener(v -> {
            setTextSelected(binding.txtthreepage, binding.txttwopage);
            setTextSelected(binding.txtthreepage, binding.txtonepage);
        });

    }

    private void setTextSelectedPos(int selectedPos) {
        if (selectedPos==0) {
            binding.txtonepage.setOnClickListener(v -> {
                setTextSelected(binding.txtonepage, binding.txttwopage);
                setTextSelected(binding.txtonepage, binding.txtthreepage);
            });
        } else if (selectedPos==1){
            binding.txttwopage.setOnClickListener(v -> {
                setTextSelected(binding.txttwopage, binding.txtthreepage);
                setTextSelected(binding.txttwopage, binding.txtonepage);
            });
        } else if (selectedPos==2){
            binding.txtthreepage.setOnClickListener(v -> {
                setTextSelected(binding.txtthreepage, binding.txttwopage);
                setTextSelected(binding.txtthreepage, binding.txtonepage);
            });


        }

    }

    private void setTextSelected(TextView textToSelect, TextView textDeselect){
        textToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        textToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        textDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        textDeselect.setTextColor(Color.parseColor("#000000"));
    }
}