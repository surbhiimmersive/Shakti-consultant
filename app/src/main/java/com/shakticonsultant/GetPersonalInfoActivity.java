package com.shakticonsultant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.OrganizationAdapter;
import com.shakticonsultant.databinding.ActivityPersonalInfoBinding;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.GetEmployeeHistoryResponse;
import com.shakticonsultant.responsemodel.GetPersonalInformationResponse;
import com.shakticonsultant.responsemodel.InterestedFiledDatumResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.SpinnerModel;
import com.shakticonsultant.responsemodel.StateDatumResponse;
import com.shakticonsultant.responsemodel.StateResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.FileHelper;
import com.shakticonsultant.utils.PathUtil;
import com.shakticonsultant.utils.PermissionManager;
import com.shakticonsultant.utils.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPersonalInfoActivity extends AppCompatActivity   {
    int year,month,day;
    RecyclerView recyclerOrganization;
String uploadedFileName="";
    PermissionManager permission;
    ActivityPersonalInfoBinding binding;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    Uri selectedResume;
    ArrayList<SpinnerModel> spinner_state_list=new ArrayList<>();
    ArrayList<String> sp_state_name_list=new ArrayList<>();
    ArrayList<String> sp_annual_income=new ArrayList<>();
    ArrayList<String> sp_city_name_list=new ArrayList<>();
    ArrayList<String> sp_stream_list=new ArrayList<>();
    ArrayList<String> sp_subject_list=new ArrayList<>();
    ArrayList<String> sp_division_list=new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 9544;
    private static final int PICK_doc_REQUEST = 9545;
    private static final int PICK_doc_id_proof_REQUEST = 9546;
String userid,strInterested_id;
String strprefix,strstate,strcity;
    String profilefilepath = null;
    String idprooffilePath = null;
    String resumepath = null;
String strGender;
    Uri selectedImage;
    String strfre_exp="";
    Uri selected_id_proof_uri;
    String part_image;
    String uploadedFileName_id_proof="";
    String strbirthdate;
    String strStateid;
    String strSubject="";
    String strStream="",strDivision="";
    String strAnnual="";
    String MobilePattern = "[0-9]{10}";

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    List<StateDatumResponse> statelist=new ArrayList<>();
    List<AnnualDatumResponse> annualList=new ArrayList<>();
    List<InterestedFiledDatumResponse> streamList=new ArrayList<>();
    List<InterestedFiledDatumResponse> subjectList=new ArrayList<>();
    List<InterestedFiledDatumResponse> designationList=new ArrayList<>();
    List<CityDatumResponse> cityList=new ArrayList<>();
    ArrayAdapter<String> adspinnerStatep;
    ArrayAdapter<String> adp1;
    String str_are_you_work="NO",working_organization_name="";
    String str_first_job_month="",str_first_job_year="";
    String Cityid;
    String fullpath = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = new ConnectionDetector(GetPersonalInfoActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            getStateListApi();
            getAnnualSalary();

            if (checkStoragePermission()) {
            } else {
                requestPermissions();
            }

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    },
                    1
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                    // If you don't have access, launch a new activity to show the user the system's dialog
                    // to allow access to the external storage
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
           // userid = getIntent().getStringExtra("userid");
            getPersonalInformation();
            // set up the RecyclerView

            binding.spprefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strprefix = (String) binding.spprefix.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strGender = (String) binding.spGender.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strStream = (String) binding.spStream.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spfirstJobMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    str_first_job_month = (String) binding.spfirstJobMonth.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spfirstjobyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    str_first_job_year = (String) binding.spfirstjobyear.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            binding.spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strSubject = (String) binding.spSubject.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strDivision = (String) binding.spDivision.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            binding.btnPersonalSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (strprefix.equals("Prefix")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select prefix.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strGender.equals("Gender")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select gender.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (binding.edtName.equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter full name", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (binding.txtbday.equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select birthdate.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strstate.equals("Select State")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select state.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strcity.equals("Select City")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select city.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (binding.edtMobile.equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter mobile number.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    }

                    else if(!binding.edtMobile.getText().toString().matches(MobilePattern)) {

                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid 10 digit phone number.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    }
                    else if (resumepath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select your resume.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (idprooffilePath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select your Id proof.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (profilefilepath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select your profile image.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strStream.equals("Select Stream")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select stream.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    }


                    else if (binding.radioExperienced.isChecked()) {


                        if (binding.edtOrganizationName.getText().toString().trim().equals("")) {
                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter organization name.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();
                        } else if (str_first_job_month.equals("Select Month")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select first job month.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else if (str_first_job_year.equals("Select Year")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select first job year.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else if (strAnnual.equals("Select Annual")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select annual salary.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else {
                            PersonalDetailApi();

                        }
                    } else {

                        PersonalDetailApi();
                    }

              /*  Intent i=new Intent(PersonalInfoActivity.this,AcademicDetailsActivity.class);
                startActivity(i);*/


                }
            });


            binding.txtbday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(GetPersonalInfoActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    strbirthdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                    binding.txtbday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, year, month, day);

                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                }
            });


         /*   binding.btnSubmit.setOnClickListener(v -> {
                startActivity(new Intent(PersonalInfoActivity.this, AcademicDetailsActivity.class));
            });
*/
            binding.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.button.setTextColor(getResources().getColor(R.color.black));
                    binding.button2.setTextColor(getResources().getColor(R.color.main_text_color));

                    str_are_you_work = "No";

                    binding.recyclerOrganization.setVisibility(View.GONE);
                }
            });

            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.button.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.button2.setTextColor(getResources().getColor(R.color.black));
               /* Intent i=new Intent(PersonalInfoActivity.this,OrganizationDailog.class);
                startActivity(i);*/
                    str_are_you_work = "Yes";
                    Intent intent = new Intent(GetPersonalInfoActivity.this, OrganizationDailog.class);
                    startActivityForResult(intent, 2);

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

                    if (binding.radioTeaching.isChecked()) {
                        strInterested_id = "1";
                        // Changing radio button 1 color on checked.
                        binding.radioTeaching.setTextColor(getResources().getColor(R.color.main_text_color));
                        binding.radioNonTeaching.setTextColor(getResources().getColor(R.color.black));

                        binding.cardDivision.setVisibility(View.VISIBLE);
                        binding.cardStream.setVisibility(View.VISIBLE);
                        binding.cardSubject.setVisibility(View.VISIBLE);
                        getInterenstedFiledAPi("Teaching");
                    }

                    if (binding.radioNonTeaching.isChecked()) {
                        binding.radioNonTeaching.setTextColor(getResources().getColor(R.color.main_text_color));
                        binding.radioTeaching.setTextColor(getResources().getColor(R.color.black));

                        binding.cardDivision.setVisibility(View.GONE);
                        binding.cardStream.setVisibility(View.VISIBLE);
                        binding.cardSubject.setVisibility(View.GONE);
                        getInterenstedFiledAPi("Non-Teaching");
                        strSubject="";
                        strDivision="";
                        strInterested_id = "2";


                    }
                }
            });


            binding.radioExperienceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub

                    if (binding.radioFresher.isChecked()) {
                        // Changing radio button 1 color on checked.
                        binding.radioFresher.setTextColor(getResources().getColor(R.color.main_text_color));
                        binding.radioExperienced.setTextColor(getResources().getColor(R.color.black));
                        str_are_you_work = "No";
                        working_organization_name = "NA";
                        str_first_job_month = "";
                        str_first_job_year = "";
                        strAnnual = "";
                        strfre_exp = "Fresher";
                        binding.edtOrganizationName.setVisibility(View.GONE);
                        binding.cardAnnual.setVisibility(View.GONE);
                        binding.constraintLayoutYear.setVisibility(View.GONE);
                        binding.constraintLayout3.setVisibility(View.GONE);
                    }

                    if (binding.radioExperienced.isChecked()) {
                        str_are_you_work = "Yes";
                        strfre_exp = "Experience";
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
                                });
                            }

                            adspinnerStatep=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_state_name_list);
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
                            binding.spCity.setVisibility(View.VISIBLE);
                            binding.spinner4.setVisibility(View.VISIBLE);

                            for(int i=0;i<cityList.size();i++){

                                sp_city_name_list.add(cityList.get(i).getCity_name());

                                binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                adp1=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                adp1.notifyDataSetChanged();

                            }


                        }

                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        //   Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                        binding.spCity.setVisibility(View.GONE);
                        binding.spinner4.setVisibility(View.INVISIBLE);
/*
                        sp_city_name_list.add("Select City");
                        adp1=new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_city_name_list);
                        binding.spCity.setAdapter(adp1);
                        adp1.notifyDataSetChanged();*/

                    }
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                binding.spinner4.setVisibility(View.INVISIBLE);

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    //-------------Upload Image------------------

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    // Method for starting the activity for selecting image from phone storage
    public void pick(View view) {
        verifyStoragePermissions(GetPersonalInfoActivity.this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_IMAGE_REQUEST);
    }

    public void pickIdProof(View view) {
        verifyStoragePermissions(GetPersonalInfoActivity.this);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_doc_id_proof_REQUEST);
    }

    public void pickdoc(View view) {
        verifyStoragePermissions(GetPersonalInfoActivity.this);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    PICK_doc_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(GetPersonalInfoActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get the absolute path of the selected image from its URI
    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            working_organization_name=data.getStringExtra("Organization");
          //  Toast.makeText(this, "Name"+message, Toast.LENGTH_SHORT).show();

            if(working_organization_name.equals("no")){
                working_organization_name="";
                binding.recyclerOrganization.setVisibility(View.GONE);
            }else {
                binding.recyclerOrganization.setVisibility(View.VISIBLE);
                int numberOfColumns = 2;
                ArrayList<String> myList = new ArrayList<String>(Arrays.asList(working_organization_name.split("#")));

                binding.recyclerOrganization.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                OrganizationAdapter adapter = new OrganizationAdapter(this, myList);

                binding.recyclerOrganization.setAdapter(adapter);

            }
        }

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {


                Uri uri = data.getData();
              //  File file = new File(uri.getPath());//create path from uri
              //  final String[] split = file.getPath().split(":");//split the path.

                try {
                  //  profilefilepath = PathUtil.getPath(PersonalInfoActivity.this, uri);

                    File finalFile = new File(PathUtil.getPath(GetPersonalInfoActivity.this, uri));
                    profilefilepath= finalFile.getAbsolutePath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.imageView7.setVisibility(View.VISIBLE);
                binding.imageView8.setVisibility(View.GONE);
                binding.imageView7.setImageBitmap(bitmap);


            }
        } if (requestCode == PICK_doc_REQUEST) {
            if (resultCode == RESULT_OK) {

                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uri.getPath());

                File finalFile = new File(FileHelper.getRealPathFromURI(GetPersonalInfoActivity.this,uri));
                resumepath= finalFile.getPath();
                binding.txtdoc.setText(resumepath);
               /* resumepath = myFile.getAbsolutePath();
                String displayName = null;


                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Log.d("nameeeee>>>>  ", resumepath + "/" + displayName);
                            fullpath=resumepath + "/" + displayName;
                            binding.txtdoc.setText(fullpath);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                    Log.d("nameeeee>>>>  ", displayName);

                    fullpath=resumepath + "/" + displayName;

                    binding.txtdoc.setText(fullpath);

                }
*/

            }
            }
        if (requestCode == PICK_doc_id_proof_REQUEST) {
            if (resultCode == RESULT_OK) {
                selected_id_proof_uri = data.getData();                                                         // Get the image file URI

                Uri uri = data.getData();
                File file = new File(uri.getPath());//create path from uri
                final String[] split = file.getPath().split(":");//split the path.

                try {
                   // idprooffilePath = PathUtil.getPath(PersonalInfoActivity.this, uri);


                    File finalFile = new File(PathUtil.getPath(GetPersonalInfoActivity.this, uri));
                    idprooffilePath= finalFile.getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                binding.uploadProof.setText(idprooffilePath);

            }
        }

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

                                         strAnnual=(String)binding.spAnnual.getSelectedItem();




                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_annual_income);
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
                    sp_stream_list.clear();
                    sp_division_list.clear();
                    sp_subject_list.clear();
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

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_stream_list);
                            binding.spStream.setAdapter(adp);
                        }
                        if(subjectList.size()>0){
                            sp_subject_list.add("Select Subject");

                            for(int i=0;i<subjectList.size();i++){

                                sp_subject_list.add(subjectList.get(i).getName());
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_subject_list);
                            binding.spSubject.setAdapter(adp);
                        }

                        if(designationList.size()>0){
                            sp_division_list.add("Select Division");

                            for(int i=0;i<designationList.size();i++){

                                sp_division_list.add(designationList.get(i).getName());
                            }

                            ArrayAdapter<String> adp=new ArrayAdapter<String>(GetPersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_division_list);
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

    private boolean checkStoragePermission() {
        return (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED );
    }
    private void requestPermissions() {
        permission = new PermissionManager() {
            @Override
            public List<String> setPermission() {
                List<String> permssions = new ArrayList<>();
                permssions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.CAMERA);
                return permssions;
            }
        };

        permission.checkAndRequestPermissions((Activity) GetPersonalInfoActivity.this);
    }

    public void PersonalDetailApi() {

        binding.progresspersonal.setVisibility(View.VISIBLE);
      if(strDivision.equals("Select Division")){

       strDivision="";
      }else if(strSubject.equals("Select Subject")){

      strSubject="";
     }else {

    Map<String, RequestBody> map = new HashMap<>();

    map.put("user_id", Utils.getRequestBodyParameter(AppPrefrences.getUserid(GetPersonalInfoActivity.this)));
    map.put("name_prefix", Utils.getRequestBodyParameter(strprefix));
    map.put("gender", Utils.getRequestBodyParameter(strGender));
    map.put("date_of_birth", Utils.getRequestBodyParameter(strbirthdate));
    map.put("state_id", Utils.getRequestBodyParameter(strStateid));
    map.put("city_id", Utils.getRequestBodyParameter(Cityid));
    // map.put("alternate_mobile", Utils.getRequestBodyParameter(binding.someEdit6.getText().toString().trim()));
    MultipartBody.Part strresume = null;
    if (resumepath != null) {
        File serviceImageUri = new File(resumepath);
        RequestBody fbody = RequestBody.create(serviceImageUri, MediaType.parse("application/pdf"));
        strresume = MultipartBody.Part.createFormData("resume", serviceImageUri.getName(), fbody);
    }

    // Parsing any Media type file

    MultipartBody.Part strIdProof = null;
    if (idprooffilePath != null) {
        File serviceImageUri = new File(idprooffilePath);
        RequestBody fbody = RequestBody.create(serviceImageUri, MediaType.parse("image*/"));
        strIdProof = MultipartBody.Part.createFormData("id_proof", idprooffilePath, fbody);
    }

    MultipartBody.Part strprofileimg = null;
    if (profilefilepath != null) {
        File serviceImageUri = new File(profilefilepath);
        RequestBody fbody = RequestBody.create(serviceImageUri, MediaType.parse("image*/"));
        strprofileimg = MultipartBody.Part.createFormData("profile_image", profilefilepath, fbody);
    }

    map.put("interested_field", Utils.getRequestBodyParameter(strInterested_id));
    map.put("experience", Utils.getRequestBodyParameter(strfre_exp));
    map.put("division", Utils.getRequestBodyParameter(strDivision));
    map.put("stream", Utils.getRequestBodyParameter(strStream));
    map.put("subject", Utils.getRequestBodyParameter(strSubject));
    map.put("organization_name", Utils.getRequestBodyParameter(binding.edtOrganizationName.getText().toString().trim()));
    map.put("alternate_mobile", Utils.getRequestBodyParameter(binding.someEdit6.getText().toString().trim()));


    map.put("first_job_month", Utils.getRequestBodyParameter(str_first_job_month));
    map.put("first_job_year", Utils.getRequestBodyParameter(str_first_job_year));
    map.put("annual_salary", Utils.getRequestBodyParameter(strAnnual));
    map.put("are_you_working_with_these_group", Utils.getRequestBodyParameter(str_are_you_work));
    map.put("working_organization", Utils.getRequestBodyParameter(working_organization_name));
    map.put("lecture_video_link", Utils.getRequestBodyParameter(binding.edtVideoLink.getText().toString().trim()));
       /* MultipartBody.Part serviceImage1 = null;
        if (selectedImage != null) {
            File serviceImageUri = new File(selectedImage.getPath());
            RequestBody fbody = RequestBody.create(serviceImageUri, MediaType.parse("image/*"));
            serviceImage1 = MultipartBody.Part.createFormData("image", serviceImageUri.getName(), fbody);
        }
*/
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    //Call<SignUpResponse> resultCall = apiInterface.callSignupApi(map,body);
    Call<CommonResponse> resultCall = apiInterface.callPersonalInformation(map, strresume, strIdProof, strprofileimg);
//Log.e("MAP DTA",map.toString());
    resultCall.enqueue(new Callback<CommonResponse>() {
        @Override
        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
            if (response.isSuccessful()) {
                binding.progresspersonal.setVisibility(View.GONE);
                if (response.body().isSuccess() == true) {


            finish();


                } else {
                    //   pd_loading.setVisibility(View.GONE);
                    binding.progresspersonal.setVisibility(View.GONE);

                    Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG)
                            .setActionTextColor(Color.RED)
                            .show();

                    // Utils.showFailureDialog(MyProfileActivity.this, "Please try later");

                }
            } else {
                Toast.makeText(GetPersonalInfoActivity.this, "ERROR" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                //
            }

        }

        @Override
        public void onFailure(Call<CommonResponse> call, Throwable t) {
            // pd_loading.setVisibility(View.GONE);
            binding.progresspersonal.setVisibility(View.GONE);

            Toast.makeText(GetPersonalInfoActivity.this, "ERROR" + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}

    }


    public void getPersonalInformation () {
        binding.progresspersonal.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(GetPersonalInfoActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetPersonalInformationResponse> resultCall = apiInterface.callgetPersonalInformation(map);

        resultCall.enqueue(new Callback<GetPersonalInformationResponse>() {
            @Override
            public void onResponse(Call<GetPersonalInformationResponse> call, Response<GetPersonalInformationResponse> response) {

                if (response.isSuccessful()) {
                    binding.progresspersonal.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        Log.e("User ID", response.body().getData().getId());
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        /*String content = response.body().getData().getContent();
                        binding.progressemployee.setText(content);
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                   */
                        Picasso.get()
                                .load(ApiClient.Photourl+response.body().getData().getProfile_image())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView8);

                        binding.edtName.setText(AppPrefrences.getName(GetPersonalInfoActivity.this));
                        binding.txtbday.setText(response.body().getData().getDate_of_birth());
                        binding.edtMobile.setText(AppPrefrences.getName(GetPersonalInfoActivity.this));
                        binding.edtVideoLink.setText(response.body().getData().getLecture_video_link());
binding.edtMobile.setEnabled(false);
binding.edtName.setEnabled(false);
                        binding.spDivision.setSelection(sp_division_list.indexOf(response.body().getData().getDivision()));
                        binding.spStream.setSelection(sp_stream_list.indexOf(response.body().getData().getStream()));
                        binding.spSubject.setSelection(sp_subject_list.indexOf(response.body().getData().getSubject()));

                        binding.someEdit6.setText(response.body().getData().getAlternate_mobile());

                      //  strInterested_id=response.body().getData().getInterested_field();

                        if(response.body().getData().getInterested_field().equals("1")){

                            binding.radioTeaching.setChecked(true);
                        }else{

                            binding.radioNonTeaching.setChecked(true);

                        }
                        String[] nameprefix = getResources().getStringArray(R.array.test);
                        binding.spprefix.setSelection(Arrays.asList(nameprefix).indexOf(response.body().getData().getName_prefix()));

                       String[] gender = getResources().getStringArray(R.array.test1);
                        binding.spprefix.setSelection(Arrays.asList(gender).indexOf(response.body().getData().getGender()));



                    } else {
                        Utils.showFailureDialog(GetPersonalInfoActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<GetPersonalInformationResponse> call, Throwable t) {
                binding.progresspersonal.setVisibility(View.GONE);
                Utils.showFailureDialog(GetPersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }
}