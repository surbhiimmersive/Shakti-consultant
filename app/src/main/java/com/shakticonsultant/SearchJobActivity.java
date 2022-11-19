package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.shakticonsultant.databinding.ActivitySearchJobBinding;

public class SearchJobActivity extends AppCompatActivity {

    ActivitySearchJobBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.imgSearchJob.setOnClickListener(v -> {
//            startActivity(new Intent(getApplicationContext(), JobDescriptionActivity.class));
//        });

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}