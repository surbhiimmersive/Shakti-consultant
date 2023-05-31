package com.shakticonsultant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivitySearchJobBinding;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.InterestedSkillDatumResponse;
import com.shakticonsultant.responsemodel.WorkExpDatumResponse;
import com.shakticonsultant.responsemodel.WorkExpResponse;
import com.shakticonsultant.responsemodel.interestedSkillResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchJobFilterActivity extends AppCompatActivity {
    List<CityDatumResponse> cityList=new ArrayList<>();
    ArrayList<String> sp_city_name_list=new ArrayList<>();
    ArrayAdapter<String> adp1;
    ActivitySearchJobBinding binding;
    List<WorkExpDatumResponse> workExpList=new ArrayList<>();
    ArrayList<String> sp_work_exp=new ArrayList<>();
    ArrayList<String> sp_work_exp_id=new ArrayList<>();
    String strworkexp="";
    SparseBooleanArray sparseBooleanArray ;
    List<String>city_name_list=new ArrayList<>();
    String strExperienceId="";
    String strcity,Cityid;
    String strStream,strSkillId;
    List<InterestedSkillDatumResponse> streamList=new ArrayList<>();
    ArrayAdapter<String> adaStream;
    ArrayList<String> sp_stream_list=new ArrayList<>();
    String strMinSalary="";
    String strMaxSalary="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


getExperience();
        getJobSkill();
        getCityList();
        binding.spMinSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strMinSalary = (String) binding.spMinSalary.getSelectedItem();
                strMinSalary = strMinSalary.replace("LPA", "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spMaxSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strMaxSalary = (String) binding.spMaxSalary.getSelectedItem();
                strMaxSalary = strMaxSalary.replace("LPA", "");

                // Toast.makeText(JobFiltersActivity.this, ""+strMaxSalary, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.appCompatButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.spMinSalary.setSelection(0);
                binding.spMaxSalary.setSelection(0);

                binding.spCity.setSelection(0);
                binding.spExperience.setSelection(0);
                binding.spStream.setSelection(0);

               /* getJobSkill();
                getCityList();
                getExperience();
*/

            }
        });

        binding.imgSearch.setOnClickListener(v -> {

           /* if(strMinSalary.equals("Min Salary")){
                strMinSalary="";

            }else  if(strSkillId.equals("0")){

                strSkillId="";
                strStream="Job List";
            }
            else if(Cityid.equals("0")){
                Cityid="";

            }
            else if(strMaxSalary.equals("Max Salary")){
                strMaxSalary="";

            }
            else if(strworkexp.equals("")){
                strExperienceId="";

            }


            else {*/
                Intent intent = new Intent();
                intent.putExtra("city", Cityid);
                intent.putExtra("experience", strExperienceId);
                intent.putExtra("min_salary", strMinSalary);
                intent.putExtra("max_salary", strMaxSalary);
                intent.putExtra("skill_id", strSkillId);
                intent.putExtra("skill_name", strStream);
                setResult(2, intent);
                finish();//finishing activity

          //  }
           // startActivity(new Intent(getApplicationContext(), JobDescriptionActivity.class));
        });

        binding.imgBackArrow.setOnClickListener(v -> {
         /*   onBackPressed();*/

            Intent intent=new Intent();
            intent.putExtra("city", Cityid);
            intent.putExtra("experience", strExperienceId);
            intent.putExtra("min_salary", strMinSalary);
            intent.putExtra("max_salary", strMaxSalary);
            intent.putExtra("skill_id", strSkillId);
            intent.putExtra("skill_name", strStream);
            setResult(2,intent);
            finish();//finishing activity
            overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });

    }


    public void getCityList() {

        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        // pd_loading.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CityResponse> resultCall = apiInterface.callAllCityList();

        resultCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

               sp_city_name_list.clear();
                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        cityList=response.body().getData();

                        if(cityList.size()>0){
                            binding.spCity.setVisibility(View.VISIBLE);

                            for(int i=0;i<cityList.size();i++){

                                sp_city_name_list.add(cityList.get(i).getCity_name());

                                binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strcity=(String)binding.spCity.getSelectedItem();
                                        Cityid = cityList.get(i).getId();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                adp1=new ArrayAdapter<String>(SearchJobFilterActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                adp1.notifyDataSetChanged();

                            }


                        }

                    } else {
                        progress_spinner.dismiss();

                    }
                }
            }


            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                progress_spinner.dismiss();

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
                                sp_work_exp_id.add(workExpList.get(i).getId());
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

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(SearchJobFilterActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_work_exp);
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



    public void getJobSkill() {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("category_id", AppPrefrences.getCategoryId(SearchJobFilterActivity.this));

       ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<interestedSkillResponse> resultCall = apiInterface.callIntererstedSKill(map);

        resultCall.enqueue(new Callback<interestedSkillResponse>() {
            @Override
            public void onResponse(Call<interestedSkillResponse> call, Response<interestedSkillResponse> response) {
                sp_stream_list.clear();
                if (response.isSuccessful()) {

                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        streamList=response.body().getData();

                        if(streamList.size()>0){
                            //  sp_stream_list.add("Select Skill");

                            binding.spStream.setVisibility(View.VISIBLE);
                            //  binding.spinner4.setVisibility(View.VISIBLE);

                            for(int i=0;i<streamList.size();i++){

                                sp_stream_list.add(streamList.get(i).getTitle());

                                binding.spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strStream=(String)binding.spStream.getSelectedItem();
                                        strSkillId= streamList.get(i).getId();
                                        adaStream.notifyDataSetChanged();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                adaStream=new ArrayAdapter<String>(SearchJobFilterActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_stream_list);
                                binding.spStream.setAdapter(adaStream);
                                adaStream.notifyDataSetChanged();

                            }


                        }

                    } else {
                        binding.spStream.setVisibility(View.INVISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<interestedSkillResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                binding.spStream.setVisibility(View.INVISIBLE);

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCityList();
        getExperience();
        getJobSkill();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.spMinSalary.setSelection(0);
        binding.spMaxSalary.setSelection(0);
   /*     getExperience();
        getJobSkill();
        getCityList();*/

    }
}