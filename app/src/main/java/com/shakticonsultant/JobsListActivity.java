package com.shakticonsultant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shakticonsultant.databinding.ActivityJobsListBinding;

public class JobsListActivity extends AppCompatActivity {

    ActivityJobsListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.include.getRoot().setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SpecificFacultyJobActivity.class));
        });

    }
}