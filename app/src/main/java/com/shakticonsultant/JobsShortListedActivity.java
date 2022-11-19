package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shakticonsultant.databinding.ActivityJobsShortListedBinding;

public class JobsShortListedActivity extends AppCompatActivity {

    ActivityJobsShortListedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobsShortListedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}