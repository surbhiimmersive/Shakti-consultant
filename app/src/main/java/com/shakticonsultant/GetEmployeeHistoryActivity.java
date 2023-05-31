package com.shakticonsultant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.shakticonsultant.databinding.ActiivtyGetEmployeeHostoryBinding;
import com.shakticonsultant.databinding.ActivityEmployeeHistoryBinding;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.GetEmployeeHistoryResponse;
import com.shakticonsultant.responsemodel.InterestedSkillDatumResponse;
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

public class GetEmployeeHistoryActivity extends AppCompatActivity {
    List<AnnualDatumResponse> annualList=new ArrayList<>();
    ArrayList<String> sp_annual_income=new ArrayList<>();
    String strAnnual,strAnnual2;
    String state_id;
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
    ActiivtyGetEmployeeHostoryBinding binding;
    ApiInterface apiInterface;
    Spinner spSkill;
    int year,month,day;
    ArrayAdapter<String> adpstream;
    ConnectionDetector cd;
    String strstream3="";
            String currentdatejoining,firstdatejoining,first_Relivingdate,second_joiningdate,second_Relivingdate,third_joing,third_reliving;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActiivtyGetEmployeeHostoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userid=AppPrefrences.getUserid(GetEmployeeHistoryActivity.this);
        cd=new ConnectionDetector(GetEmployeeHistoryActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {
            getAnnualSalary();
            getStateListApi();
            getCatgoryId();

            getEmployeeHistoryData();

            binding.edtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
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
                            binding.spinnerCity.setVisibility(View.VISIBLE);

                            getCityApi(strStateid);
                            adspinnerStatep.notifyDataSetChanged();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adspinnerStatep = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_state_name_list);
                    spSkill.setAdapter(adspinnerStatep);
                    adspinnerStatep.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if(strstate.equals("Select State")){
                            binding.spinnerCity.setVisibility(View.GONE);

                            Toast.makeText(GetEmployeeHistoryActivity.this, "Please select state.", Toast.LENGTH_SHORT).show();
                        }else {
                            binding.spinnerCity.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        }
                    });


                }
            });

            binding.edtCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
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

                    adp1 = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_city_name_list);
                    spSkill.setAdapter(adp1);
                    adp1.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if(strcity.equals("Select City")){

                            Toast.makeText(GetEmployeeHistoryActivity.this, "Please select city.", Toast.LENGTH_SHORT).show();
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this, R.style.DatePickerTheme,
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

            binding.strStream.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                     spSkill = dialog.findViewById(R.id.spEmployeeStream);
                    getCatgoryId();

                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            strstream = (String) spSkill.getSelectedItem();
                            binding.strStream.setText(strstream);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adpstream = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                    spSkill.setAdapter(adpstream);
                    adpstream.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if(strstream.equals("Select skill")){

                            Toast.makeText(GetEmployeeHistoryActivity.this, "Please select skill.", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                        }

                    });


                }
            });
    binding.strStream1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                     spSkill = dialog.findViewById(R.id.spEmployeeStream);
                    getCatgoryId();

                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            strstream1 = (String) spSkill.getSelectedItem();
                            binding.strStream1.setText(strstream1);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adpstream = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                    spSkill.setAdapter(adpstream);
                    adpstream.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {
                        dialog.dismiss();

                    });


                }
            });
   binding.strStream2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                     spSkill = dialog.findViewById(R.id.spEmployeeStream);
                    getCatgoryId();

                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            strstream2 = (String) spSkill.getSelectedItem();
                            binding.strStream2.setText(strstream2);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adpstream = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                    spSkill.setAdapter(adpstream);
                    adpstream.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {


                        dialog.dismiss();

                    });


                }
            });
 binding.strStream3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(GetEmployeeHistoryActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                     spSkill = dialog.findViewById(R.id.spEmployeeStream);
                    getCatgoryId();

                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            strstream3 = (String) spSkill.getSelectedItem();
                            binding.strStream3.setText(strstream3);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adpstream = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                    spSkill.setAdapter(adpstream);
                    adpstream.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {
                        dialog.dismiss();

                    });


                }
            });

            binding.someEdit19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,

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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,
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


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetEmployeeHistoryActivity.this,R.style.DatePickerTheme,
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
                if(binding.edtState.toString().trim().equals("Select State")){
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select state", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();

                }else if(binding.edtCity.toString().trim().equals("Select City")){
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

                }else if(strstream.equals("Select Skill")){
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

        map.put("user_id",AppPrefrences.getUserid(GetEmployeeHistoryActivity.this));
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
                        Utils.showFailureDialog(GetEmployeeHistoryActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<UserCategoryResponse> call, Throwable t) {
                //  binding.progressAbout.setVisibility(View.GONE);
                Utils.showFailureDialog( GetEmployeeHistoryActivity.this, "Something went wrong!");
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

/*
                                spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstream = (String) spSkill.getSelectedItem();
                                        binding.strStream1.setText(strstream);


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        dialog.dismiss();
                                    }
                                });*/

                                /*binding.spStream1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
*/
                           /*     adpstream = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                spSkill.setAdapter(adpstream);
                                adpstream.notifyDataSetChanged();
*/
/*
                                ArrayAdapter<String> adpstream1 = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream1.setAdapter(adpstream1);
                                adpstream1.notifyDataSetChanged();

                                ArrayAdapter<String> adpstream2 = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream2.setAdapter(adpstream2);
                                adpstream2.notifyDataSetChanged();


                                ArrayAdapter<String> adpstream3 = new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream3.setAdapter(adpstream3);
                                adpstream3.notifyDataSetChanged();*/
                            }

                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }else{

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

                                sp_annual_income.add(annualList.get(i).getSalary()+ " LPA");
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

                            ArrayAdapter<String> currentadp=new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
                            binding.spAnnual.setAdapter(currentadp);
                            currentadp.notifyDataSetChanged();



                            ArrayAdapter<String> adp1=new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
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
sp_state_name_list.clear();
                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);


                    if (response.body().isSuccess()==true) {
                        statelist=response.body().getData();

                        if(statelist.size()>0){
                            // sp_state_name_list.add("Select State");
                            for(int i=0;i<statelist.size();i++){

                                sp_state_name_list.add(statelist.get(i).getState_name());
                                // spinner_state_list.add(model);

                             /*   binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                        /*    adspinnerStatep=new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_state_name_list);
                            binding.spState.setAdapter(adspinnerStatep);
                            adspinnerStatep.notifyDataSetChanged();*/
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

                               /* binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                });
                                adp1=new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                adp1.notifyDataSetChanged();
*/
                            }


                        }

                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        //   Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                        binding.spCity.setVisibility(View.GONE);
                        sp_city_name_list.add("Select City");
                        adp1=new ArrayAdapter<String>(GetEmployeeHistoryActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
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
       // binding.progressemployee.setVisibility(View.VISIBLE);
      /*  Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();
*/
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
                   // progress_spinner.dismiss();
                   // binding.progressemployee.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                      /*  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
*/

finish();

                    }  else {
                       // Utils.showFailureDialog(GetEmployeeHistoryActivity.this, response.body().getMessage());
                      //  progress_spinner.dismiss();


                    }
                }else{
                   // binding.progressemployee.setVisibility(View.GONE);
                //    progress_spinner.dismiss();

                    Utils.showFailureDialog(GetEmployeeHistoryActivity.this, response.body().getMessage());


                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                //binding.progressemployee.setVisibility(View.GONE);
              //  progress_spinner.dismiss();

                Utils.showFailureDialog(GetEmployeeHistoryActivity.this, t.toString());
            }
        });
    }
    public void getEmployeeHistoryData () {
      //  binding.progressemployee.setVisibility(View.VISIBLE);
       /* Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();
*/
        Map<String, String> map = new HashMap<>();

        map.put("user_id",AppPrefrences.getUserid(GetEmployeeHistoryActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetEmployeeHistoryResponse> resultCall = apiInterface.callGetEmployeeHistory(map);

        resultCall.enqueue(new Callback<GetEmployeeHistoryResponse>() {
            @Override
            public void onResponse(Call<GetEmployeeHistoryResponse> call, Response<GetEmployeeHistoryResponse> response) {

                if (response.isSuccessful()) {
                   // progress_spinner.dismiss();
                    //binding.progressemployee.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                         state_id= response.body().getData().getState();

                        strstream=response.body().getData().getCurrent_stream();
                        strstream1=response.body().getData().getFirst_stream();
                        strstream2=response.body().getData().getSecond_stream();
                        strstream3=response.body().getData().getThird_stream();
                        Log.e("User ID", response.body().getData().getId());
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        /*String content = response.body().getData().getContent();
                        binding.progressemployee.setText(content);
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                   */

                        binding.someEdit17.setText(response.body().getData().getCurrent_organisation());
                        binding.someEdit18.setText(response.body().getData().getFirst_organisation());
                        binding.someEdit21.setText(response.body().getData().getSecond_organisation());
                        binding.someEdit24.setText(response.body().getData().getThird_organisation());
                        binding.someEdit10.setText(response.body().getData().getCurrent_date_of_joining());
                        binding.someEdit19.setText(response.body().getData().getFirst_date_of_joining());
                        binding.someEdit22.setText(response.body().getData().getSecond_date_of_joining());
                        binding.someEdit25.setText(response.body().getData().getThird_date_of_joining());
                        binding.someEdit20.setText(response.body().getData().getFirst_date_of_reliving());
                        binding.someEdit23.setText(response.body().getData().getSecond_date_of_reliving());
                        binding.someEdit26.setText(response.body().getData().getThird_date_of_reliving());

                     //   Toast.makeText(GetEmployeeHistoryActivity.this, ""+response.body().getData().getCurrent_stream(), Toast.LENGTH_SHORT).show();

                        Cityid=response.body().getData().getCurrent_city();
                        strStateid=response.body().getData().getCurrent_state();
                        binding.spAnnual.setSelection(sp_annual_income.indexOf(response.body().getData().getCurrent_annual_salary()));
                        binding.spAnnual2.setSelection(sp_annual_income.indexOf(response.body().getData().getFirst_annual_salary()));
                        binding.spStream.setSelection(sp_stream_list.indexOf(strstream));
                     /*   binding.spStream1.setSelection(sp_stream_list.indexOf(strstream1));
                        binding.spStream2.setSelection(sp_stream_list.indexOf(strstream2));
                        binding.spStream3.setSelection(sp_stream_list.indexOf(strstream3));*/
                        binding.spState.setSelection(sp_state_name_list.indexOf(response.body().getData().getState()));
getCityApi(state_id);

binding.strStream.setText(strstream);
binding.strStream1.setText(strstream1);
binding.strStream2.setText(strstream2);
binding.strStream3.setText(strstream3);
binding.edtState.setText(response.body().getData().getCurrent_state_name());
binding.edtCity.setText(response.body().getData().getCurrent_city_name());

                    } else {
                        //Utils.showFailureDialog(GetEmployeeHistoryActivity.this, response.body().getMessage());
                      //  binding.progressemployee.setVisibility(View.GONE);

                     //   progress_spinner.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<GetEmployeeHistoryResponse> call, Throwable t) {
              //  binding.progressemployee.setVisibility(View.GONE);
              //  progress_spinner.dismiss();

              //  Utils.showFailureDialog(GetEmployeeHistoryActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getAnnualSalary();
        getStateListApi();
       getCatgoryId();

        getEmployeeHistoryData();
        getCityApi(state_id);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getAnnualSalary();
        getStateListApi();
       getCatgoryId();

        getEmployeeHistoryData();
        getCityApi(state_id);

    }
}