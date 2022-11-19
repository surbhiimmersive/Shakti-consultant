package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shakticonsultant.databinding.ActivityJobsRecommendedBinding;

public class JobsRecommendedActivity extends AppCompatActivity {

    ActivityJobsRecommendedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsRecommendedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}