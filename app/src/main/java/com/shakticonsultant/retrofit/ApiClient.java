package com.shakticonsultant.retrofit;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends Application {

    private static Retrofit retrofit = null;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static Retrofit getClient() {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://shakticonsultant.manageprojects.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private List<NotificationChannel> createChannels() {

        List<NotificationChannel> channels = new ArrayList<>();
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();

        Uri notificationSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_sound_long);

        *//*General Notification Channel*//*
        NotificationChannel generalChannel = new NotificationChannel(getString(R.string.default_notification_channel_id), "General", NotificationManager.IMPORTANCE_DEFAULT);

        *//*Catalog's Notification Channel*//*
        NotificationChannel catalogChannel = new NotificationChannel(getString(R.string.notification_channel_orders), getString(R.string.notification_channel_orders), NotificationManager.IMPORTANCE_HIGH);
        catalogChannel.setSound(notificationSound, attributes);
        catalogChannel.setVibrationPattern(new long[]{1000, 1000});

        *//*Set the Channel in the Channels List*//*
        channels.add(generalChannel);
        channels.add(catalogChannel);

        return channels;
    }*/
}