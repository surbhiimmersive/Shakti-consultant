package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobCategoryAdapter;
import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.databinding.ActivityPackageBinding;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.PackageDatumResponse;
import com.shakticonsultant.responsemodel.PackageResponse;
import com.shakticonsultant.responsemodel.PaymentIntentResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, PackageListAdapter.OnItemClickListener {
    ActivityPackageBinding binding;
    DatePickerDialog datePickerDialog;
    ConnectionDetector cd;

    Response<PackageResponse> response_public;

    PaymentSheet paymentSheet;
    int package_id;

    String paymentIntentClientSecret;
    String amount;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = new ConnectionDetector(PackageActivity.this);

        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {

            getPackageListApi();
            getUserPackageActiveApi();
           /* datePickerDialog = new DatePickerDialog(
                    PackageActivity.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY),
                    Calendar.getInstance().get(Calendar.DATE));
*/
            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
            binding.btnUpgradePackage.setOnClickListener(v -> {
                //showDateDialog();

                getPackageListApi();
            });
            binding.btnActivePackage.setOnClickListener(v -> {
                if (response_public != null)
                    showActivePackageDialog();
            });

            paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        }
    }

    public void callApiForToekn() {
        //  binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        map.put("amount", amount + "00");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<PaymentIntentResponse> resultCall = apiInterface.CallCreateToken(map);

        resultCall.enqueue(new Callback<PaymentIntentResponse>() {
            @Override
            public void onResponse(Call<PaymentIntentResponse> call, Response<PaymentIntentResponse> response) {

                if (response.isSuccessful()) {
                    //response.body().getData().get(0).getStarting_salary()

                    try {
                        customerConfig = new PaymentSheet.CustomerConfiguration(
                                response.body().getData().get(0).getcustomer(),
                                response.body().getData().get(0).getephemeralKey());

                        paymentIntentClientSecret = response.body().getData().get(0).getPaymentIntent();
                        PaymentConfiguration.init(getApplicationContext(), response.body().getData().get(0).getpublishableKey());
                        presentPaymentSheet();
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentIntentResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //  binding.progressBardetail.setVisibility(View.GONE);

                Utils.showFailureDialog(PackageActivity.this, "Something went wrong!");
            }
        });
    }


    private void showActivePackageDialog() {
        Dialog dialog = new Dialog(PackageActivity.this);
        dialog.setContentView(R.layout.dialog_active_package);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerpackage = dialog.findViewById(R.id.recyclerpackage);
        TextView tv_pakage_balance = dialog.findViewById(R.id.tv_pakage_balance);
        TextView tv_start_date = dialog.findViewById(R.id.tv_start_date);
        TextView tv_end_date = dialog.findViewById(R.id.tv_end_date);

        tv_pakage_balance.setText("Package Balanace - " + response_public.body().getData().get(0).getPackage_balance().toString());
        tv_start_date.setText("Package Start Date - " + response_public.body().getData().get(0).getPackage_start_date().toString());
        tv_end_date.setText("Package End Date - " + response_public.body().getData().get(0).getPackage_end_date().toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PackageActivity.this);
        recyclerpackage.setLayoutManager(linearLayoutManager);
        PackageListAdapter adapter = new PackageListAdapter(PackageActivity.this, response_public.body().getData().get(0));
        recyclerpackage.setAdapter(adapter);
        recyclerpackage.getAdapter().notifyDataSetChanged();

    }


    private void showActivePackageDialog(String status, String payment_status) {

        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_payment_status);
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        androidx.appcompat.widget.AppCompatButton button = dialog.findViewById(R.id.btn_interview_ok);
        TextView tvStatus = dialog.findViewById(R.id.tvStatus);
        ImageView imageView25 = dialog.findViewById(R.id.imageView25);

        tvStatus.setText(payment_status);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return true;
            }
        });

        if (status.equals("Completed")) {
            //imageView25.setImageDrawable(R.drawable.pay_success);
            imageView25.setImageDrawable(getResources().getDrawable(R.drawable.pay_success));
        } else if (status.equals("Failed")) {
            imageView25.setImageDrawable(getResources().getDrawable(R.drawable.pay_failed));
        } else {
            imageView25.setImageDrawable(getResources().getDrawable(R.drawable.pay_cancel));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PackageActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            showActivePackageDialog("Canceled", "Payment Canceled ");
            //  Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            showActivePackageDialog("Failed", "Payment Failed");
            //  Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            getSubscriptionApi();
        }
    }

    //kotvali thana

    private void presentPaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("Example, Inc.")
                .customer(customerConfig)
                // Set `allowsDelayedPaymentMethods` to true if your business can handle payment methods
                // that complete payment after a delay, like SEPA Debit and Sofort.
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentIntentClientSecret,
                configuration
        );

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
    }

    public void getSubscriptionApi() {
        Log.d("getSubscriptionApi","getSubscriptionApi1");
        Log.d("getSubscriptionApi",""+package_id);
        //binding.progressContatc.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(this));
        map.put("package_id", "" + package_id);
        map.put("transaction_id", paymentIntentClientSecret);
        map.put("payment_status", "success");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callSubscriptionApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                Log.d("getSubscriptionApi",""+response);

                if (response.isSuccessful()) {
                    Log.d("getSubscriptionApi","isSuccessful");
                     //  progress_spinner.dismiss();
                    //binding.progressContatc.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()) {
                        Log.d("getSubscriptionApi","isSuccess");
                        showActivePackageDialog("Completed", "Payment Completed");
                    } else {
                        Log.d("getSubscriptionApi","false");
                        //binding.progressContatc.setVisibility(View.GONE);
                    //    progress_spinner.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                // binding.progressContatc.setVisibility(View.GONE);
               // progress_spinner.dismiss();

                // Utils.showFailureDialog(context, "Something went wrong!");
            }
        });
    }


    private void showDateDialog() {
        Dialog dialog = new Dialog(PackageActivity.this);
        dialog.setContentView(R.layout.dialog_select_interview_date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton date1 = dialog.findViewById(R.id.btn_select_date_1);
        AppCompatButton date2 = dialog.findViewById(R.id.btn_select_date_2);
        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);

        date1.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        date2.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmationDialog();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }


    private void showConfirmationDialog() {
        Dialog dialog = new Dialog(PackageActivity.this);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
        });

        faq.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    public void getPackageListApi() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        // binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(PackageActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<PackageResponse> resultCall = apiInterface.callPackageList(map);

        resultCall.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    //  binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PackageActivity.this);
                        binding.recyclerpackage.setLayoutManager(linearLayoutManager);
                        PackageListAdapter adapter = new PackageListAdapter(PackageActivity.this, response.body().getData(),
                                PackageActivity.this);
                        binding.recyclerpackage.setAdapter(adapter);
                        binding.recyclerpackage.getAdapter().notifyDataSetChanged();

                    } else {
                        //binding.progressBarpackage.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                //binding.progressBarpackage.setVisibility(View.GONE);
                progress_spinner.dismiss();

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(PackageActivity.this, "Something went wrong!");
            }
        });
    }


    public void getUserPackageActiveApi() {

        // binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(PackageActivity.this));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<PackageResponse> resultCall = apiInterface.callUserActivePackage(map);

        resultCall.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {

                if (response.isSuccessful()) {
                    //  binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()) {
                        response_public = response;
                        if (!response.body().getData().get(0).getPrice().equals("0")) {
                            binding.btnActivePackage.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //binding.progressBarpackage.setVisibility(View.GONE);
                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                //binding.progressBarpackage.setVisibility(View.GONE);

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(PackageActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    public void onItemClick(int package_id, String amount) {
        this.package_id = package_id;
        this.amount = amount;

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PackageActivity.this);

        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.shakti_consultant_logo);
        builder.setMessage("Are you sure you want to upgrade your package?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // dialog.dismiss();
                callApiForToekn();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}