package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.shakticonsultant.databinding.ActivitySplashBinding;
import com.shakticonsultant.utils.AppPrefrences;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean c = AppPrefrences.getLogin_status(SplashActivity.this);
                if (!c) {
                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
              /*  startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();*/
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
            }
        }, 2000);

    }
}