package com.shakticonsultant;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.OurClientAdapter;
import com.shakticonsultant.databinding.ActivityOurClientBinding;
import com.shakticonsultant.responsemodel.OurClientDatumResponse;
import com.shakticonsultant.responsemodel.OurClientResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OurClientActivity extends AppCompatActivity {
    private ViewPager viewPager;
    ActivityOurClientBinding binding;
    ConnectionDetector cd;
    private List<OurClientDatumResponse> sliderDatumResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOurClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgOurback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        viewPager = binding.viewpager;
        cd = new ConnectionDetector(OurClientActivity.this);

        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            getSliderApi();
        }
    }

    public void getSliderApi() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<OurClientResponse> resultCall = apiInterface.callOurClient();

        resultCall.enqueue(new Callback<OurClientResponse>() {
            @Override
            public void onResponse(Call<OurClientResponse> call, Response<OurClientResponse> response) {
                Log.d("responseresponse", "" + response.isSuccessful());

                if (response.isSuccessful()) {
                    //  pd_loading.setVisibility(View.GONE);
                    progress_spinner.dismiss();
                    if (response.body().isSuccess() == true) {
                        binding.lEmpty.setVisibility(View.GONE);
                        sliderDatumResponses = response.body().getData();
                        if (sliderDatumResponses != null && sliderDatumResponses.size() > 0) {

                            OurClientAdapter pagerAdapter = new OurClientAdapter(OurClientActivity.this, sliderDatumResponses);
                            viewPager.setAdapter(pagerAdapter);

                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new SliderTimer(), 3000, 5000);
                        } else {
                            progress_spinner.dismiss();

                            // Utils.showFailureDialog(getActivity(), "Please search valid keyword");
                        }
                    } else {
                        progress_spinner.dismiss();
                        binding.lEmpty.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(getActivity(), "Please search valid keyword");
                    }
                }
            }

            @Override

            public void onFailure(Call<OurClientResponse> call, Throwable t) {
                // pd_loading.setVisibility(View.GONE);
                progress_spinner.dismiss();

                // Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < sliderDatumResponses.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}