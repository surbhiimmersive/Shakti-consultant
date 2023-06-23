package com.shakticonsultant;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.PaymentIntentResponse;
import com.shakticonsultant.responsemodel.PaymentIntentDetailResponse;
import com.shakticonsultant.responsemodel.PaymentIntentResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    PaymentSheet paymentSheet;
    String package_id;
    String amount;

    String paymentIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        package_id = getIntent().getStringExtra("package_id");

        callApiForToekn();

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
    }

    public void callApiForToekn() {
        //  binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        map.put("amount", "10000");

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

                Utils.showFailureDialog(CheckoutActivity.this, "Something went wrong!");
            }
        });
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
                Intent i = new Intent(CheckoutActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            showActivePackageDialog("Canceled", "Payment cancelled ");
            //  Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            showActivePackageDialog("Failed", "Payment Failed");
            //  Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            getSubscriptionApi();

            //    Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();

            // Log.d("Completed")
        }
    }

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
        //binding.progressContatc.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(getApplicationContext());
        progress_spinner.show();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(this));
        map.put("package_id", package_id);
        map.put("transaction_id", paymentIntentClientSecret);
        map.put("payment_status", "success");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callSubscriptionApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    //binding.progressContatc.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        showActivePackageDialog("Completed", "Payment Completed !!");
                    } else {
                        //binding.progressContatc.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(context, response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                // binding.progressContatc.setVisibility(View.GONE);
                progress_spinner.dismiss();

                // Utils.showFailureDialog(context, "Something went wrong!");
            }
        });
    }

}
