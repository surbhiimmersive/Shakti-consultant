package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.shakticonsultant.databinding.ActivityAcademicDetailsBinding;

public class AcademicDetailsActivity extends AppCompatActivity {

    ActivityAcademicDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcademicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUpdate.setOnClickListener(v -> {
            startActivity(new Intent(AcademicDetailsActivity.this, EmployeeHistoryActivity.class));
        });

    }
}