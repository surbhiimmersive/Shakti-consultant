package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shakticonsultant.databinding.ActivityChatWithUsBinding;

public class ChatWIthUsActivity extends AppCompatActivity {

    ActivityChatWithUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatWithUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

    }
}