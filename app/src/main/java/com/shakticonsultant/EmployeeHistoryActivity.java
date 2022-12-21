package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityEmployeeHistoryBinding;
import com.shakticonsultant.responsemodel.AboutResponse;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.InterestedFiledDatumResponse;
import com.shakticonsultant.responsemodel.InterestedSkillDatumResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.StateDatumResponse;
import com.shakticonsultant.responsemodel.StateResponse;
import com.shakticonsultant.responsemodel.UserCategoryResponse;
import com.shakticonsultant.responsemodel.interestedSkillResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeHistoryActivity extends AppCompatActivity {
    List<AnnualDatumResponse> annualList=new ArrayList<>();
    ArrayList<String> sp_annual_income=new ArrayList<>();
    String strAnnual,strAnnual2;
    String strstream,strstream1="",strstream2="";
    ArrayList<String> sp_stream_list=new ArrayList<>();
    List<InterestedSkillDatumResponse> streamList=new ArrayList<>();
    List<StateDatumResponse> statelist=new ArrayList<>();
    List<CityDatumResponse> cityList=new ArrayList<>();
    String strstate,strcity;
    ArrayAdapter<String> adspinnerStatep;
    String strStateid;
    String userid;
    ArrayList<String> sp_state_name_list=new ArrayList<>();
    ArrayList<String> sp_city_name_list=new ArrayList<>();
    ArrayAdapter<String> adp1;
    String Cityid,Interested_id;
    ActivityEmployeeHistoryBinding binding;
   ApiInterface apiInterface;
   int year,month,day;
   ConnectionDetector cd;
   Spinner spSkill;
   String strstream3,currentdatejoining,firstdatejoining,first_Relivingdate,second_joiningdate,second_Relivingdate,third_joing,third_reliving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userid=getIntent().getStringExtra("userid");
cd=new ConnectionDetector(EmployeeHistoryActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {
            getAnnualSalary();
            getStateListApi();
            getCatgoryId();

            binding.edtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(EmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                    spSkill = dialog.findViewById(R.id.spEmployeeStream);
                    getCityApi(strStateid);

                    TextView textView57 = dialog.findViewById(R.id.textView57);
                    textView57.setText("Select State");
                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            strstate = (String) spSkill.getSelectedItem();
                            binding.edtState.setText(strstate);
                            binding.edtCity.setText("Select City");

                            strStateid = statelist.get(i).getId();
                            getCityApi(strStateid);
                            adspinnerStatep.notifyDataSetChanged();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adspinnerStatep = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_state_name_list);
                    spSkill.setAdapter(adspinnerStatep);
                    adspinnerStatep.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if(strstate.equals("Select State")){

                            Toast.makeText(EmployeeHistoryActivity.this, "Please select state.", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                        }

                    });


                }
            });

            binding.edtCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(EmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                    spSkill = dialog.findViewById(R.id.spEmployeeStream);


                    TextView textView57 = dialog.findViewById(R.id.textView57);
                    textView57.setText("Select City");
                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            strcity=(String)spSkill.getSelectedItem();
                            Cityid = cityList.get(i).getId();
                            binding.edtCity.setText(strcity);
                            adp1.notifyDataSetChanged();
                            // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    adp1 = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_city_name_list);
                    spSkill.setAdapter(adp1);
                    adp1.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if(strcity.equals("Select City")){

                            Toast.makeText(EmployeeHistoryActivity.this, "Please select city.", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                        }

                    });


                }
            });


            binding.someEdit10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    currentdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit10.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });



            binding.someEdit19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    firstdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit19.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });binding.someEdit20.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    first_Relivingdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit20.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
binding.someEdit22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    second_joiningdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit22.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
binding.someEdit23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    second_Relivingdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit23.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });binding.someEdit25.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    third_joing = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit25.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });
binding.someEdit26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(EmployeeHistoryActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    third_reliving = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.someEdit26.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });


            binding.btnUpdate.setOnClickListener(v -> {

if(binding.someEdit17.getText().toString().trim().equals("")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter current organization name", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else
if(strstate.equals("Select State")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select state", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else if(strcity.equals("Select City")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select city", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else if(strAnnual.equals("Select Annual")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select annual salary", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else if(strstream.equals("Select Stream")){
    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select Stream", Snackbar.LENGTH_LONG)
            .setAction("Action", null);
    View sbView = snackbar.getView();
    sbView.setBackgroundColor(getColor(R.color.purple_200));

    snackbar.show();

}else {

   EmployeeHistoryApi();
}
            });
        }



    }
    public void getCatgoryId () {

        Map<String, String> map = new HashMap<>();

map.put("user_id",userid);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserCategoryResponse> resultCall = apiInterface.calluserCategorySkill(map);

        resultCall.enqueue(new Callback<UserCategoryResponse>() {
            @Override
            public void onResponse(Call<UserCategoryResponse> call, Response<UserCategoryResponse> response) {

                if (response.isSuccessful()) {
                    //binding.progressAbout.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
getJobSkill(response.body().getData().getCategory_id());


                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.showFailureDialog(EmployeeHistoryActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<UserCategoryResponse> call, Throwable t) {
              //  binding.progressAbout.setVisibility(View.GONE);
                Utils.showFailureDialog(EmployeeHistoryActivity.this, "Something went wrong!");
            }
        });
    }




    public void getJobSkill(String category_id) {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("category_id", category_id);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<interestedSkillResponse> resultCall = apiInterface.callIntererstedSKill(map);

        resultCall.enqueue(new Callback<interestedSkillResponse>() {
            @Override
            public void onResponse(Call<interestedSkillResponse> call, Response<interestedSkillResponse> response) {
                sp_stream_list.clear();
                if (response.isSuccessful()) {

                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        streamList=response.body().getData();

                        if(streamList.size()>0) {

                            for (int i = 0; i < streamList.size(); i++) {

                                sp_stream_list.add(streamList.get(i).getTitle());

                                binding.spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstream = (String) binding.spStream.getSelectedItem();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                binding.spStream1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstream1 = (String) binding.spStream1.getSelectedItem();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                binding.spStream2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstream2 = (String) binding.spStream2.getSelectedItem();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                binding.spStream3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstream3 = (String) binding.spStream3.getSelectedItem();

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                ArrayAdapter<String> adp = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream.setAdapter(adp);


                                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream1.setAdapter(adp1);

                                ArrayAdapter<String> adp2 = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream2.setAdapter(adp2);


                                ArrayAdapter<String> adp3 = new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream3.setAdapter(adp3);


                            }

                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }


            @Override
            public void onFailure(Call<interestedSkillResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
               // binding.spinner4.setVisibility(View.INVISIBLE);

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
sp_annual_income.clear();
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

                                        strAnnual=(String)binding.spAnnual.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                        //  String id = annualList.get(i).getId();

                                        //   Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                binding.spAnnual2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strAnnual2=(String)binding.spAnnual2.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                      //  String id = annualList.get(i).getId();

                                        //   Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> currentadp=new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
                            binding.spAnnual.setAdapter(currentadp);
                            currentadp.notifyDataSetChanged();



                            ArrayAdapter<String> adp1=new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
                            binding.spAnnual2.setAdapter(adp1);
                            adp1.notifyDataSetChanged();

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
///-----------State Api--------------



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
                    sp_state_name_list.clear();

                    if (response.body().isSuccess()==true) {
                        statelist=response.body().getData();

                        if(statelist.size()>0){
                            // sp_state_name_list.add("Select State");
                            for(int i=0;i<statelist.size();i++){

                                sp_state_name_list.add(statelist.get(i).getState_name());
                                // spinner_state_list.add(model);

                               /* binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstate=(String)binding.spState.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                        strStateid = statelist.get(i).getId();

                                        // Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();


                                        getCityApi(strStateid);
                                        adspinnerStatep.notifyDataSetChanged();


                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });*/
                            }

                            adspinnerStatep=new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_state_name_list);
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
                           // binding.spCity.setVisibility(View.VISIBLE);

                            for(int i=0;i<cityList.size();i++){

                                sp_city_name_list.add(cityList.get(i).getCity_name());

                                /*binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strcity=(String)binding.spCity.getSelectedItem();
                                        Cityid = cityList.get(i).getId();
                                        adp1.notifyDataSetChanged();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });*/
                                adp1=new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                adp1.notifyDataSetChanged();

                            }


                        }

                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        //   Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                       // binding.spCity.setVisibility(View.GONE);
                        sp_city_name_list.add("Select City");
                        adp1=new ArrayAdapter<String>(EmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                        binding.spCity.setAdapter(adp1);
                        adp1.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    public void EmployeeHistoryApi() {
          binding.progressemployee.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", userid);
        map.put("current_organisation", binding.someEdit17.getText().toString().trim());
        map.put("current_city", Cityid);
        map.put("current_state", strStateid);
        map.put("current_date_of_joining", binding.someEdit10.getText().toString().trim());
        map.put("current_annual_salary", strAnnual);
        map.put("current_stream", strstream);
        map.put("first_organisation", binding.someEdit18.getText().toString().trim());
        map.put("first_date_of_joining", binding.someEdit19.getText().toString().trim());
        map.put("first_date_of_reliving", binding.someEdit20.getText().toString().trim());
        map.put("first_annual_salary", strAnnual2);
        map.put("first_stream", strstream1);
        map.put("second_organisation", binding.someEdit21.getText().toString().trim());
        map.put("second_date_of_joining", binding.someEdit19.getText().toString().trim());
        map.put("second_date_of_reliving", binding.someEdit20.getText().toString().trim());
        map.put("second_stream", strstream2);
        map.put("third_organisation", binding.someEdit24.getText().toString().trim());
        map.put("third_date_of_joining", binding.someEdit25.getText().toString().trim());
        map.put("third_date_of_reliving", binding.someEdit26.getText().toString().trim());
        map.put("third_stream", strstream3);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callEmployeeHistory(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                     binding.progressemployee.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                      /*  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
*/
                        AppPrefrences.setUserid(EmployeeHistoryActivity.this,userid);

                        Intent i=new Intent(EmployeeHistoryActivity.this,MainActivity.class);
                        i.putExtra("userid",userid);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                    }  else {
                        Utils.showFailureDialog(EmployeeHistoryActivity.this, response.body().getMessage());


                    }
                }else{
                    binding.progressemployee.setVisibility(View.GONE);

                    Utils.showFailureDialog(EmployeeHistoryActivity.this, response.body().getMessage());


                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                 binding.progressemployee.setVisibility(View.GONE);
                Utils.showFailureDialog(EmployeeHistoryActivity.this, t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAnnualSalary();
        getStateListApi();
        getCatgoryId();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getAnnualSalary();
        getStateListApi();
        getCatgoryId();
    }
}