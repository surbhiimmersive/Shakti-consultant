package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.shakticonsultant.databinding.ActivityEmployeeHistoryBinding;

public class EmployeeHistoryActivity extends AppCompatActivity {

    ActivityEmployeeHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnUpdate.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }
}