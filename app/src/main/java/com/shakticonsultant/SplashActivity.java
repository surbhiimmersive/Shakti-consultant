package com.shakticonsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shakticonsultant.databinding.ActivitySplashBinding;
import com.shakticonsultant.utils.AppPrefrences;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        FirebaseApp.initializeApp(this);
        firebasetoken();
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
            }
        }, 2000);

    }


    public void firebasetoken() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.e("SHIKHA TOKEN", token);
                        // Log and toast
                        String msg = token;
                        //Log.e("Shikhamsg", msg);
                        AppPrefrences.setUserToken(SplashActivity.this,msg);
                        //Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();

                        // Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                        //AppPrefrences.setUserToken(MainActivity.this,msg);

                    }
                });
    }
}