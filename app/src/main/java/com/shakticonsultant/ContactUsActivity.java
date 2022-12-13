package com.shakticonsultant;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.ActivityContactUsBinding;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    ActivityContactUsBinding binding;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
cd=new ConnectionDetector(ContactUsActivity.this);
        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

        binding.imageView33.setOnClickListener(v -> {


            if (!cd.isConnectingToInternet()) {
                Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }else
if(binding.edtName.getText().toString().trim().equals("")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter name", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else if(binding.edtEmail.getText().toString().trim().equals("")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter email address.", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else if(!binding.edtEmail.getText().toString().matches(emailPattern)) {

    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid email address.", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();


}else if(!binding.edtText.getText().toString().matches(emailPattern)) {

    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter text.", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();


}else {

    getContactUsApi();
}
        });

    }


    public void getContactUsApi() {
        binding.progressContatc.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put("name", binding.edtName.getText().toString().trim());
        map.put("email", binding.edtEmail.getText().toString().trim());
        map.put("message", binding.edtText.getText().toString().trim());


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callContactusApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressContatc.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(ContactUsActivity.this)
                                .setTitle(R.string.app_name)
                                .setMessage("Contact request send successfully!")
                                .setIcon(R.drawable.shakti_consultant_logo)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        binding.edtEmail.setText("");
                                        binding.edtName.setText("");
                                        binding.edtText.setText("");
                                    }
                                });
                        logoutDialog.show();
                    } else {
                        binding.progressContatc.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressContatc.setVisibility(View.GONE);

                Utils.showFailureDialog(ContactUsActivity.this, "Something went wrong!");
            }
        });
    }
}