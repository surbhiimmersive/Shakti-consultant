package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.card.MaterialCardView;
import com.shakticonsultant.databinding.ActivityJobFiltersBinding;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.OrganizationDatumResponse;
import com.shakticonsultant.responsemodel.OrganizationResponse;
import com.shakticonsultant.responsemodel.WorkExpDatumResponse;
import com.shakticonsultant.responsemodel.WorkExpResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFiltersActivity extends AppCompatActivity implements View.OnClickListener {
String data;
   ActivityJobFiltersBinding binding;
   private MaterialCardView previousCard;
    List<WorkExpDatumResponse> workExpList=new ArrayList<>();
    ArrayList<String> sp_work_exp=new ArrayList<>();
    String strworkexp="";
    SparseBooleanArray sparseBooleanArray ;
    List<String>city_name_list=new ArrayList<>();
    String strExperienceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobFiltersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        previousCard = binding.cardviewSalary;

        binding.cardviewSalary.setOnClickListener(this);
        binding.cardviewCities.setOnClickListener(this);
        binding.cardviewExperience.setOnClickListener(this);

        binding.btnApply.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.putExtra("city",data);
            intent.putExtra("experience",strExperienceId);
            intent.putExtra("min_salary",binding.edtMinSalary.getText().toString().trim());
            intent.putExtra("max_salary",binding.edtMaxSalary.getText().toString().trim());
            setResult(2,intent);
            finish();//finishing activity



        });
  binding.imgSearch.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        });

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });


    }

    private void setCardViewSelected(MaterialCardView target){
        target.setStrokeColor(Color.parseColor("#FFFFFF"));
        target.setStrokeWidth(4);
    }

    private void setCardViewUnSelected(MaterialCardView target){
        target.setStrokeColor(Color.parseColor("#DADADA"));
        target.setStrokeWidth(1);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.cardviewSalary.getId()){
            setCardViewSelected(binding.cardviewSalary);

            binding.headerTitle.setText("Salary");

            binding.salaryView.setVisibility(View.VISIBLE);
            binding.citiesView.setVisibility(View.GONE);
            binding.experienceView.setVisibility(View.GONE);

            binding.imgSearch.setVisibility(View.VISIBLE);

            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewSalary;
        }

        if(v.getId() == binding.cardviewCities.getId()){
            setCardViewSelected(binding.cardviewCities);

            binding.headerTitle.setText("Cities");

            binding.salaryView.setVisibility(View.GONE);
            binding.citiesView.setVisibility(View.VISIBLE);
            binding.experienceView.setVisibility(View.GONE);

            binding.imgSearch.setVisibility(View.GONE);
getCityList();

            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewCities;

        }

        if(v.getId() == binding.cardviewExperience.getId()){
            setCardViewSelected(binding.cardviewExperience);

            binding.headerTitle.setText("Experience");

            binding.salaryView.setVisibility(View.GONE);
            binding.citiesView.setVisibility(View.GONE);
            binding.experienceView.setVisibility(View.VISIBLE);

            binding.imgSearch.setVisibility(View.VISIBLE);
            getExperience();
            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewExperience;

        }

    }
//--------All city List---------------


    public void getCityList() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CityResponse> resultCall = apiInterface.callAllCityList();

        resultCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                if (response.isSuccessful()) {
                     binding.progressBar.setVisibility(View.GONE);
                    // lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        List<CityDatumResponse> orglist=response.body().getData();


                        for(int i=0;i<orglist.size();i++){

                            city_name_list.add(orglist.get(i).getCity_name());

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (JobFiltersActivity.this,
                                            android.R.layout.simple_list_item_multiple_choice,
                                            android.R.id.text1, city_name_list );

                            binding.listViewCity.setAdapter(adapter);

                            binding.listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // TODO Auto-generated method stub

                                    sparseBooleanArray =binding.listViewCity.getCheckedItemPositions();
                                    String ValueHolder = "" ;

                                    int i = 0 ;

                                    while (i < sparseBooleanArray.size()) {

                                        if (sparseBooleanArray.valueAt(i)) {

                                            ValueHolder += city_name_list.get(sparseBooleanArray.keyAt(i)) + "#";
                                            data=ValueHolder;
                                        }

                                        i++ ;
                                    }

                                    ValueHolder = ValueHolder.replaceAll("(#)*$", "");
                                    data=ValueHolder;
                                    // Toast.makeText(OrganizationDailog.this, "ListView Selected Values = " + ValueHolder, Toast.LENGTH_LONG).show();

binding.listViewCity.setItemChecked(0,false);

                                }
                            });
                            // selectableItems.add(new Item(response.body().getData().get(i).getOrganisation(),response.body().getData().get(i).getOrganisation()));
                        }

                        // adapter = new SelectableAdapter(OrganizationDailog.this,selectableItems,true);
                        // recyclerView.setAdapter(adapter);
                    } else {
binding.progressBar.setVisibility(View.GONE);
                        //  lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);

                //lemprtNotification.setVisibility(View.VISIBLE);
                //  pd_loading.setVisibility(View.GONE);
                //Utils.showFailureDialog(NotificationActivity.this, "Something went wrong!");
            }
        });
    }

//-----------------Experience------------------------------------------------------------

    public void getExperience() {
        // binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

      ApiInterface  apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<WorkExpResponse> resultCall = apiInterface.callWorkExperience();

        resultCall.enqueue(new Callback<WorkExpResponse>() {
            @Override
            public void onResponse(Call<WorkExpResponse> call, Response<WorkExpResponse> response) {
sp_work_exp.clear();
                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        workExpList=response.body().getData();

                        if(workExpList.size()>0){
                            for(int i=0;i<workExpList.size();i++){

                                sp_work_exp.add(workExpList.get(i).getExperience());
                                // spinner_state_list.add(model);

                                binding.spExperience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strworkexp=(String)binding.spExperience.getSelectedItem();

                                        strExperienceId = workExpList.get(i).getId();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(JobFiltersActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_work_exp);
                            binding.spExperience.setAdapter(adp);
                            adp.notifyDataSetChanged();
                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        //  Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<WorkExpResponse> call, Throwable t) {

                //  binding.progressInfo.setVisibility(View.GONE);
                //   Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCityList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getCityList();


    }
}