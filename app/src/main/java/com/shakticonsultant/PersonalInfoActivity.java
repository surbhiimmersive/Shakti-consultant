package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shakticonsultant.databinding.ActivityPersonalInfoBinding;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.InterestedFiledDatumResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.SpinnerModel;
import com.shakticonsultant.responsemodel.StateDatumResponse;
import com.shakticonsultant.responsemodel.StateResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoActivity extends AppCompatActivity  {
    int year,month,day;

    ActivityPersonalInfoBinding binding;
    ApiInterface apiInterface;
    ArrayList<SpinnerModel> spinner_state_list=new ArrayList<>();
    ArrayList<String> sp_state_name_list=new ArrayList<>();
    ArrayList<String> sp_annual_income=new ArrayList<>();
    ArrayList<String> sp_city_name_list=new ArrayList<>();
    ArrayList<String> sp_stream_list=new ArrayList<>();
    ArrayList<String> sp_subject_list=new ArrayList<>();
    ArrayList<String> sp_division_list=new ArrayList<>();

    List<StateDatumResponse> statelist=new ArrayList<>();
    List<AnnualDatumResponse> annualList=new ArrayList<>();
    List<InterestedFiledDatumResponse> streamList=new ArrayList<>();
    List<InterestedFiledDatumResponse> subjectList=new ArrayList<>();
    List<InterestedFiledDatumResponse> designationList=new ArrayList<>();
    List<CityDatumResponse> cityList=new ArrayList<>();
    ArrayAdapter<String> adspinnerStatep;
    ArrayAdapter<String> adp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getStateListApi();
        getAnnualSalary();

        binding.txtbday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                binding.txtbday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        binding.btnSubmit.setOnClickListener(v -> {
            startActivity(new Intent(PersonalInfoActivity.this, AcademicDetailsActivity.class));
        });





        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.button.setTextColor(getResources().getColor(R.color.black));
                binding.button2.setTextColor(getResources().getColor(R.color.main_text_color));
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.button.setTextColor(getResources().getColor(R.color.main_text_color));
                binding.button2.setTextColor(getResources().getColor(R.color.black));
                Intent i=new Intent(PersonalInfoActivity.this,OrganizationDailog.class);
                startActivity(i);

            }
        });


        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.button3.setTextColor(getResources().getColor(R.color.main_text_color));
                binding.button4.setTextColor(getResources().getColor(R.color.black));
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.button3.setTextColor(getResources().getColor(R.color.black));
                binding.button4.setTextColor(getResources().getColor(R.color.main_text_color));
            }
        });

        binding.rgInterested.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if(binding.radioTeaching.isChecked())
                {
                    // Changing radio button 1 color on checked.
                    binding.radioTeaching.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.radioNonTeaching.setTextColor(getResources().getColor(R.color.black));

                    binding.cardDivision.setVisibility(View.VISIBLE);
                    binding.cardStream.setVisibility(View.VISIBLE);
                    binding.cardSubject.setVisibility(View.VISIBLE);
                    getInterenstedFiledAPi("Teaching");
                }

                if(binding.radioNonTeaching.isChecked())
                {
                    binding.radioNonTeaching.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.radioTeaching.setTextColor(getResources().getColor(R.color.black));

                    binding.cardDivision.setVisibility(View.GONE);
                    binding.cardStream.setVisibility(View.VISIBLE);
                    binding.cardSubject.setVisibility(View.GONE);
                    getInterenstedFiledAPi("Non-Teaching");


                }
            }
        });


        binding.radioExperienceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                if(binding.radioFresher.isChecked())
                {
                    // Changing radio button 1 color on checked.
                    binding.radioFresher.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.radioExperienced.setTextColor(getResources().getColor(R.color.black));

                    binding.edtOrganizationName.setVisibility(View.GONE);
                    binding.cardAnnual.setVisibility(View.GONE);
                    binding.constraintLayoutYear.setVisibility(View.GONE);
                    binding.constraintLayout3.setVisibility(View.GONE);
                }

                if(binding.radioExperienced.isChecked())
                {
                    binding.radioExperienced.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.radioFresher.setTextColor(getResources().getColor(R.color.black));
                    binding.edtOrganizationName.setVisibility(View.VISIBLE);
                    binding.cardAnnual.setVisibility(View.VISIBLE);
                    binding.constraintLayoutYear.setVisibility(View.VISIBLE);
                    binding.constraintLayout3.setVisibility(View.VISIBLE);

                }
            }
        });

    }


    public void getStateListApi() {
        // binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<StateResponse> resultCall = apiInterface.callStateListApi();

        resultCall.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        statelist=response.body().getData();

                        if(statelist.size()>0){
                            // sp_state_name_list.add("Select State");
                            for(int i=0;i<statelist.size();i++){

                                sp_state_name_list.add(statelist.get(i).getState_name());
                                // spinner_state_list.add(model);

                                binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        String srtState=(String)binding.spState.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                        String id = statelist.get(i).getId();

                                        Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();
                                        getCityApi(id);
                                        adspinnerStatep.notifyDataSetChanged();


                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            adspinnerStatep=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_state_name_list);
                            binding.spState.setAdapter(adspinnerStatep);
                            adspinnerStatep.notifyDataSetChanged();
                        }




                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        //  Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

                //  binding.progressInfo.setVisibility(View.GONE);
                //   Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    ///////---------------City Api-----------------


    public void getCityApi(String state_id) {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("state_id", state_id);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CityResponse> resultCall = apiInterface.callCityListApi(map);

        resultCall.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
               sp_city_name_list.clear();
                if (response.isSuccessful()) {

                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        cityList=response.body().getData();

                        if(cityList.size()>0){

                            for(int i=0;i<cityList.size();i++){

                                sp_city_name_list.add(cityList.get(i).getCity_name());

                                binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        String srtState=(String)binding.spCity.getSelectedItem();
                                        String id = cityList.get(i).getId();
                                        adp1.notifyDataSetChanged();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                adp1=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                adp1.notifyDataSetChanged();

                            }


                        }

                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        //   Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                        sp_city_name_list.add("Select City");
                        adp1=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                        binding.spCity.setAdapter(adp1);
                        adp1.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                 Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }
//-----------------Annual Salary List------------------------------------------------------------

    public void getAnnualSalary() {
        // binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<AnnualResponse> resultCall = apiInterface.callAnnualSalary();

        resultCall.enqueue(new Callback<AnnualResponse>() {
            @Override
            public void onResponse(Call<AnnualResponse> call, Response<AnnualResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        annualList=response.body().getData();

                        if(annualList.size()>0){
                            sp_annual_income.add("Select Annual");
                            for(int i=0;i<annualList.size();i++){

                                sp_annual_income.add(annualList.get(i).getSalary());
                                // spinner_state_list.add(model);

                                binding.spAnnual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        String srtState=(String)binding.spAnnual.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                        String id = annualList.get(i).getId();

                                        Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
                            binding.spAnnual.setAdapter(adp);
                            adp.notifyDataSetChanged();
                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        //  Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<AnnualResponse> call, Throwable t) {

                //  binding.progressInfo.setVisibility(View.GONE);
                //   Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }


    //////--------Interrested Filed-----------------------


    public void getInterenstedFiledAPi(String strfiled) {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("interest_type", strfiled);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<IntrestedFieldResponse> resultCall = apiInterface.callInterestedFiledApi(map);

        resultCall.enqueue(new Callback<IntrestedFieldResponse>() {
            @Override
            public void onResponse(Call<IntrestedFieldResponse> call, Response<IntrestedFieldResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        streamList=response.body().getStream();
                        subjectList=response.body().getSubject();
                        designationList=response.body().getDesignation();

                        if(streamList.size()>0){
                            sp_stream_list.add("Select Stream");

                            for(int i=0;i<streamList.size();i++){

                                sp_stream_list.add(streamList.get(i).getName());
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_stream_list);
                            binding.spStream.setAdapter(adp);
                        }
                        if(subjectList.size()>0){
                            sp_subject_list.add("Select Subject");

                            for(int i=0;i<subjectList.size();i++){

                                sp_subject_list.add(subjectList.get(i).getName());
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_subject_list);
                            binding.spSubject.setAdapter(adp);
                        }

                        if(designationList.size()>0){
                            sp_division_list.add("Select Designation");

                            for(int i=0;i<designationList.size();i++){

                                sp_division_list.add(designationList.get(i).getName());
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_division_list);
                            binding.spDivision.setAdapter(adp);
                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<IntrestedFieldResponse> call, Throwable t) {

                //   binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, t.toString());
            }
        });
    }
}