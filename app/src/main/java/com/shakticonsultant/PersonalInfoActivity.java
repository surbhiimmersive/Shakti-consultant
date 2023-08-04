package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.PathUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.BuildConfig;
import com.shakticonsultant.adapter.JobSkillListAdapter;
import com.shakticonsultant.adapter.OrganizationAdapter;
import com.shakticonsultant.databinding.ActivityPersonalInfoBinding;
import com.shakticonsultant.responsemodel.AnnualDatumResponse;
import com.shakticonsultant.responsemodel.AnnualResponse;
import com.shakticonsultant.responsemodel.CityDatumResponse;
import com.shakticonsultant.responsemodel.CityResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.InterestedCategoryDatumResponse;
import com.shakticonsultant.responsemodel.InterestedCategoryResponse;
import com.shakticonsultant.responsemodel.InterestedFiledDatumResponse;
import com.shakticonsultant.responsemodel.InterestedSkillDatumResponse;
import com.shakticonsultant.responsemodel.IntrestedFieldResponse;
import com.shakticonsultant.responsemodel.JobSkillDatumResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.responsemodel.SpinnerModel;
import com.shakticonsultant.responsemodel.StateDatumResponse;
import com.shakticonsultant.responsemodel.StateResponse;
import com.shakticonsultant.responsemodel.interestedSkillResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.FileHelper;
import com.shakticonsultant.utils.FileUtils;
import com.shakticonsultant.utils.PathUtil;
import com.shakticonsultant.utils.PermissionManager;
import com.shakticonsultant.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoActivity extends AppCompatActivity {
    int year, month, day;
    RecyclerView recyclerOrganization;
    String uploadedFileName = "";
    PermissionManager permission;
    ActivityPersonalInfoBinding binding;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    String strCategoryId = "0";
    String strInterested_id = "1";
    String strSkillId = "0";
    Calendar minAdultAge, userAge;

    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    Uri selectedResume;
    ArrayList<SpinnerModel> spinner_state_list = new ArrayList<>();
    ArrayList<String> sp_state_name_list = new ArrayList<>();
    ArrayList<String> sp_annual_income = new ArrayList<>();
    ArrayList<String> sp_city_name_list = new ArrayList<>();
    ArrayList<String> sp_stream_list = new ArrayList<>();
    ArrayList<String> sp_subject_list = new ArrayList<>();
    ArrayList<String> sp_division_list = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 9544;
    private static final int PICK_doc_REQUEST = 9545;
    private static final int PICK_doc_id_proof_REQUEST = 9546;
    String userid;
    String strprefix, strstate, strcity;
    String profilefilepath = null;
    String idprooffilePath = null;
    String resumepath = null;
    String strGender;
    Uri selectedImage;
    String strfre_exp = "";
    Uri selected_id_proof_uri;
    String part_image;
    String uploadedFileName_id_proof = "";
    String strbirthdate = "";
    String strStateid;
    String strSubject = "";
    String strStream = "", strDivision = "";
    String strAnnual = "";
    String mobile;
    String MobilePattern = "[0-9]{10}";

    // Permissions for accessing the storage
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    List<StateDatumResponse> statelist = new ArrayList<>();
    List<AnnualDatumResponse> annualList = new ArrayList<>();
    List<InterestedSkillDatumResponse> streamList = new ArrayList<>();
    List<InterestedFiledDatumResponse> subjectList = new ArrayList<>();
    List<InterestedCategoryDatumResponse> designationList = new ArrayList<>();
    List<CityDatumResponse> cityList = new ArrayList<>();
    ArrayAdapter<String> adspinnerStatep;
    ArrayAdapter<String> adaStream;
    ArrayAdapter<String> adpCategory;
    ArrayAdapter<String> adp1;
    String str_are_you_work = "NO", working_organization_name = "NA";
    String str_first_job_month = "", str_first_job_year = "";
    String Cityid;
    Spinner spSkill;
    String fullpath = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    ConnectionDetector cd;
    String name;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPersonalInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cd = new ConnectionDetector(PersonalInfoActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            getStateListApi();
            getAnnualSalary();
            dahsboardInterenstedFiledAPi("1");

            if (checkStoragePermission()) {
            } else {
                requestPermissions();
            }

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    1
            );





            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
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
            }*/

            userid = getIntent().getStringExtra("userid");
            name = getIntent().getStringExtra("name");
            mobile = getIntent().getStringExtra("mobile");

            binding.button.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));

            binding.edtName.setText(name);
            binding.edtMobile.setText(mobile);
            // set up the RecyclerView
            binding.edtState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(PersonalInfoActivity.this);
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
                            binding.spinner4.setVisibility(View.VISIBLE);
                            getCityApi(strStateid);
                            adspinnerStatep.notifyDataSetChanged();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            dialog.dismiss();
                        }
                    });

                    adspinnerStatep = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_state_name_list);
                    spSkill.setAdapter(adspinnerStatep);
                    adspinnerStatep.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if (strstate.equals("Select State")) {
                            binding.spinner4.setVisibility(View.GONE);

                            Toast.makeText(PersonalInfoActivity.this, "Please select state.", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.spinner4.setVisibility(View.VISIBLE);

                            dialog.dismiss();
                        }
                    });


                }
            });

            binding.edtCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(PersonalInfoActivity.this);
                    dialog.setContentView(R.layout.skill_selection);
                    dialog.show();


                    AppCompatButton btnok = dialog.findViewById(R.id.btnok);
                    spSkill = dialog.findViewById(R.id.spEmployeeStream);


                    TextView textView57 = dialog.findViewById(R.id.textView57);
                    textView57.setText("Select City");
                    spSkill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            strcity = (String) spSkill.getSelectedItem();
                            Cityid = cityList.get(i).getId();
                            binding.edtCity.setText(strcity);
                            adp1.notifyDataSetChanged();
                            // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    adp1 = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_city_name_list);
                    spSkill.setAdapter(adp1);
                    adp1.notifyDataSetChanged();


                    btnok.setOnClickListener(v -> {

                        if (strcity.equals("Select City")) {

                            Toast.makeText(PersonalInfoActivity.this, "Please select city.", Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                        }

                    });


                }
            });

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
           /* binding.spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    strStream = (String) binding.spStream.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/
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


            binding.btnPersonalSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (profilefilepath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select your profile image.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strprefix.equals("Prefix")) {
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

                    } else if (binding.edtName.getText().toString().trim().equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter full name", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (binding.txtbday.getText().toString().equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select DOB.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (minAdultAge.before(userAge)) {
                        binding.txtbday.setText("");

                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "The age must be 18 year.", Snackbar.LENGTH_LONG)
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
                    } else if (!binding.edtMobile.getText().toString().matches(MobilePattern)) {

                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter valid 10 digit phone number.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (resumepath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please upload your resume.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (idprooffilePath == null) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please upload your I'd Proof.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();
                    } else if (strCategoryId.equals("0")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select category.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strSkillId.equals("0")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select skill.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (strfre_exp.equals("")) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select experience.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(getColor(R.color.purple_200));

                        snackbar.show();

                    } else if (binding.radioExperienced.isChecked()) {


                        if (str_first_job_month.equals("Select Month")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select job month.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else if (str_first_job_year.equals("Select Year")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select job year.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else if (strAnnual.equals("Select Current Salary")) {

                            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select salary.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(getColor(R.color.purple_200));

                            snackbar.show();

                        } else {
                            PersonalDetailApi();

                        }
                    } else {
                        //Toast.makeText(PersonalInfoActivity.this, "categor"+strCategoryId, Toast.LENGTH_SHORT).show();
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

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalInfoActivity.this, R.style.DatePickerTheme,

                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    userAge = new GregorianCalendar(year, month, day);
                                    minAdultAge = new GregorianCalendar();
                                    minAdultAge.add(Calendar.YEAR, -18);
                                    /*if (minAdultAge.before(userAge)) {

                                        Toast.makeText(PersonalInfoActivity.this, "Select 18 year date", Toast.LENGTH_SHORT).show();
                                    }else {*/
                                    // strbirthdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + (year);
                                    strbirthdate = year + "-" + (monthOfYear + 1) + "-" + (dayOfMonth);
                                    binding.txtbday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + (year));
                                    //  }
                                }
                            }, year - 18, month, day);

                    c.set(year - 18, month, day);//Year,Mounth -1,Day
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

                    //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 365.25 * 14));
                    // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 31556926000L);

/*
                    long value=c.getTimeInMillis();
                   datePickerDialog.getDatePicker().setMaxDate(value);*/
                    // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 31556926000L);
                    //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 365.25 * 14));

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
                    binding.button2.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                    binding.button.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));

                    binding.button2.setTextSize(15);

                    str_are_you_work = "No";
                    binding.textView19.setVisibility(View.GONE);
                    binding.recyclerOrganization.setVisibility(View.GONE);
                }
            });

            binding.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.button.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.button.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                    binding.button2.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));

                    binding.button.setTextSize(15);
                    binding.button2.setTextColor(getResources().getColor(R.color.black));
               /* Intent i=new Intent(PersonalInfoActivity.this,OrganizationDailog.class);
                startActivity(i);*/
                    binding.textView19.setVisibility(View.GONE);
                    str_are_you_work = "Yes";
                    Intent intent = new Intent(PersonalInfoActivity.this, OrganizationDailog.class);
                    intent.putExtra("working_organization_name", working_organization_name);

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
                        //   binding.cardStream.setVisibility(View.VISIBLE);
                        binding.cardSubject.setVisibility(View.GONE);
                        strSubject = "";
                        getInterenstedFiledAPi("1");

                        binding.cardStream.setVisibility(View.GONE);
                    }

                    if (binding.radioNonTeaching.isChecked()) {
                        strInterested_id = "2";

                        strStream = "";
                        strSkillId = "";
                        sp_stream_list.clear();

                        sp_stream_list.add("Select Skill");
                        if (adaStream == null) {
                            adaStream = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                            binding.spStream.setAdapter(adaStream);
                        } else {
                            adaStream.notifyDataSetChanged();
                        }
                        binding.radioNonTeaching.setTextColor(getResources().getColor(R.color.main_text_color));
                        binding.radioTeaching.setTextColor(getResources().getColor(R.color.black));

                        binding.cardDivision.setVisibility(View.VISIBLE);
                        //    binding.cardStream.setVisibility(View.VISIBLE);
                        binding.cardSubject.setVisibility(View.GONE);
                        getInterenstedFiledAPi("2");
                        strSubject = "";
                        binding.cardStream.setVisibility(View.GONE);
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
                        binding.edtOrganizationName.setVisibility(View.GONE);
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
                sp_state_name_list.clear();

                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);


                    if (response.body().isSuccess() == true) {
                        statelist = response.body().getData();

                        if (statelist.size() > 0) {
                            // sp_state_name_list.add("Select State");
                            for (int i = 0; i < statelist.size(); i++) {

                                sp_state_name_list.add(statelist.get(i).getState_name());
                                // spinner_state_list.add(model);

                                binding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strstate = (String) binding.spState.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                        strStateid = statelist.get(i).getId();

                                        // Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();

                                        if (strstate.equals("Select State")) {
                                            binding.spinner4.setVisibility(View.GONE);

                                            binding.spCity.setVisibility(View.GONE);
                                        } else {
                                            binding.spinner4.setVisibility(View.VISIBLE);
                                            binding.spCity.setVisibility(View.VISIBLE);

                                        }
                                        getCityApi(strStateid);
                                        adspinnerStatep.notifyDataSetChanged();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            adspinnerStatep = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_state_name_list);
                            binding.spState.setAdapter(adspinnerStatep);
                            //  adspinnerStatep.notifyDataSetChanged();
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
                    if (response.body().isSuccess() == true) {
                        cityList = response.body().getData();

                        if (cityList.size() > 0) {
                            // binding.spCity.setVisibility(View.VISIBLE);
                            //  binding.spinner4.setVisibility(View.VISIBLE);

                            for (int i = 0; i < cityList.size(); i++) {

                                sp_city_name_list.add(cityList.get(i).getCity_name());

                                binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strcity = (String) binding.spCity.getSelectedItem();
                                        Cityid = cityList.get(i).getId();
                                        adp1.notifyDataSetChanged();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                adp1 = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_city_name_list);
                                binding.spCity.setAdapter(adp1);
                                // adp1.notifyDataSetChanged();

                            }


                        }

                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        //   Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                        binding.spCity.setVisibility(View.GONE);
                        //  binding.spinner4.setVisibility(View.INVISIBLE);
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
                //   binding.spinner4.setVisibility(View.INVISIBLE);

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
        verifyStoragePermissions(PersonalInfoActivity.this);

        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.app_name)), PICK_IMAGE_REQUEST);
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_IMAGE_REQUEST);
        }
    }

    public void pickIdProof(View view) {
        verifyStoragePermissions(PersonalInfoActivity.this);
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Open Gallery"), PICK_doc_id_proof_REQUEST);*/

        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/jpeg");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.app_name)), PICK_doc_id_proof_REQUEST);
        } else {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PICK_doc_id_proof_REQUEST);
        }
    }

    public void pickdoc(View view) {
        verifyStoragePermissions(PersonalInfoActivity.this);
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
            Toast.makeText(PersonalInfoActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method to get the absolute path of the selected image from its URI
    @SuppressLint("ResourceType")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            working_organization_name = data.getStringExtra("Organization");
            //  Toast.makeText(this, "Name"+message, Toast.LENGTH_SHORT).show();
            if (working_organization_name != null) {
                if (working_organization_name.equals("no")) {
                    working_organization_name = "NA";
                    binding.button.setTextColor(getResources().getColor(R.color.black));
                    binding.button2.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.button2.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                    binding.button.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));

                    binding.button2.setTextSize(15);

                    str_are_you_work = "No";
                    binding.textView19.setVisibility(View.GONE);
                    binding.recyclerOrganization.setVisibility(View.GONE);
                } else {
                    binding.recyclerOrganization.setVisibility(View.VISIBLE);

                    binding.button.setTextColor(getResources().getColor(R.color.main_text_color));
                    binding.button.setBackground(getResources().getDrawable(R.drawable.custom_item_bg));
                    binding.button2.setBackground(getResources().getDrawable(R.drawable.custom_edittext_bg));

                    binding.button.setTextSize(15);
                    binding.button2.setTextColor(getResources().getColor(R.color.black));
               /* Intent i=new Intent(PersonalInfoActivity.this,OrganizationDailog.class);
                startActivity(i);*/
                    binding.textView19.setVisibility(View.GONE);
                    int numberOfColumns = 2;
                    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(working_organization_name.split("#")));

                    binding.recyclerOrganization.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                    OrganizationAdapter adapter = new OrganizationAdapter(this, myList);

                    binding.recyclerOrganization.setAdapter(adapter);

                }
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {


                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    // Handle the selected media URI

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (!Environment.isExternalStorageManager()) {
                            grantPersistableUriPermissions(uri);
                        }
                    }


                   /* try {
                        int takeFlags = intent.getFlags();
                        takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    } catch (SecurityException e) {
                        Log.e("", e.toString());
                    }*/

                    try {
                        //  profilefilepath = PathUtil.getPath(PersonalInfoActivity.this, uri);

                        File finalFile = new File(PathUtil.getPath(PersonalInfoActivity.this, uri));
                        profilefilepath = finalFile.getAbsolutePath();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //  binding.imageView7.setVisibility(View.VISIBLE);
                    //    binding.imageView8.setVisibility(View.VISIBLE);
                    binding.imageView6.setImageBitmap(bitmap);
                    binding.imgEdit.setVisibility(View.VISIBLE);
                }


            }
        }
        if (requestCode == PICK_doc_REQUEST) {
            if (resultCode == RESULT_OK) {
                //  if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                File finalFile = new File(FileHelper.getRealPathFromURI(PersonalInfoActivity.this, uri));
                resumepath = finalFile.getPath();
                binding.txtdoc.setText(finalFile.getName());
            }
        }
        if (requestCode == PICK_doc_id_proof_REQUEST) {
            if (resultCode == RESULT_OK) {
                selected_id_proof_uri = data.getData();                                                         // Get the image file URI

                Uri uri = data.getData();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        grantPersistableUriPermissions(uri);
                    }
                }

                File file = new File(uri.getPath());//create path from uri
                final String[] split = file.getPath().split(":");//split the path.

                try {
                    // idprooffilePath = PathUtil.getPath(PersonalInfoActivity.this, uri);


                    File finalFile = new File(PathUtil.getPath(PersonalInfoActivity.this, uri));
                    idprooffilePath = finalFile.getPath();

                    binding.uploadProof.setText(finalFile.getName());

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                // binding.uploadProof.setText(idprooffilePath);

            }
        }
    }


    private void grantPersistableUriPermissions(Uri uri) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } catch (IllegalArgumentException e) {
                // on Kitkat api only 0x3 is allowed (FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION)
                getApplicationContext().grantUriPermission(getApplicationContext().getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (SecurityException e) {
                Log.e("", e.toString());
            }


            //  getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

       /* final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
        getContentResolver().takePersistableUriPermission(uri, takeFlags);*/
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
                    if (response.body().isSuccess() == true) {

                        annualList = response.body().getData();

                        if (annualList.size() > 0) {
                            sp_annual_income.add("Select Current Salary");
                            for (int i = 0; i < annualList.size(); i++) {

                                sp_annual_income.add(annualList.get(i).getSalary() + " LPA");
                                // spinner_state_list.add(model);

                                binding.spAnnual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strAnnual = (String) binding.spAnnual.getSelectedItem();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> adp = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_annual_income);
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
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        Map<String, String> map = new HashMap<>();
        map.put("interested_field", strfiled);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<InterestedCategoryResponse> resultCall = apiInterface.callInterestedFiledCategory(map);

        resultCall.enqueue(new Callback<InterestedCategoryResponse>() {
            @Override
            public void onResponse(Call<InterestedCategoryResponse> call, Response<InterestedCategoryResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();

                    sp_division_list.clear();
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        designationList = response.body().getData();

                        if (designationList.size() > 0) {
                            //  sp_division_list.add("Select Category");

                            for (int i = 0; i < designationList.size(); i++) {

                                sp_division_list.add(designationList.get(i).getTitle());

                                binding.spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        try {
                                            strDivision = designationList.get(i).getTitle();

                                            if (strDivision.equals("Select Category")) {
                                                binding.cardStream.setVisibility(View.GONE);
                                            } else {
                                                binding.cardStream.setVisibility(View.VISIBLE);
                                                strCategoryId = designationList.get(i).getId();

                                                adpCategory.notifyDataSetChanged();
                                                getJobSkill(strCategoryId);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            adpCategory = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_division_list);
                            binding.spDivision.setAdapter(adpCategory);

                            //  adpCategory.notifyDataSetChanged();

                        }


                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<InterestedCategoryResponse> call, Throwable t) {
                progress_spinner.dismiss();

                //   binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, t.toString());
            }
        });
    }

    public void dahsboardInterenstedFiledAPi(String strfiled) {
        //  binding.progressInfo.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put("interested_field", strfiled);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<InterestedCategoryResponse> resultCall = apiInterface.callInterestedFiledCategory(map);

        resultCall.enqueue(new Callback<InterestedCategoryResponse>() {
            @Override
            public void onResponse(Call<InterestedCategoryResponse> call, Response<InterestedCategoryResponse> response) {

                if (response.isSuccessful()) {

                    sp_division_list.clear();
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        designationList = response.body().getData();

                        if (designationList.size() > 0) {
                            //  sp_division_list.add("Select Category");
                            for (int i = 0; i < designationList.size(); i++) {

                                sp_division_list.add(designationList.get(i).getTitle());


                                binding.spDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        try {
                                            strDivision = designationList.get(i).getTitle();

                                            if (strDivision.equals("Select Category")) {
                                                binding.cardStream.setVisibility(View.GONE);
                                            } else {
                                                binding.cardStream.setVisibility(View.VISIBLE);
                                                strCategoryId = designationList.get(i).getId();

                                                adpCategory.notifyDataSetChanged();
                                                getJobSkill(strCategoryId);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }
                            adpCategory = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_division_list);
                            binding.spDivision.setAdapter(adpCategory);
                        }


                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        // Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<InterestedCategoryResponse> call, Throwable t) {

                //   binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, t.toString());
            }
        });
    }

    public void getJobSkill(String category_id) {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);

        progress_spinner.show();

        Map<String, String> map = new HashMap<>();
        map.put("category_id", category_id);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<interestedSkillResponse> resultCall = apiInterface.callIntererstedSKill(map);

        resultCall.enqueue(new Callback<interestedSkillResponse>() {
            @Override
            public void onResponse(Call<interestedSkillResponse> call, Response<interestedSkillResponse> response) {
                sp_stream_list.clear();
                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        streamList = response.body().getData();
                        Log.d("streamListstreamList", streamList.toString());

                        if (streamList.size() > 0) {
                            //  sp_stream_list.add("Select Skill");

                            binding.spStream.setVisibility(View.VISIBLE);
                            //  binding.spinner4.setVisibility(View.VISIBLE);

                            for (int i = 0; i < streamList.size(); i++) {

                                sp_stream_list.add(streamList.get(i).getTitle());

                                binding.spStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        try {
                                            if (!sp_stream_list.get(i).equalsIgnoreCase("Select Skill")) {
                                                strStream = streamList.get(i).getTitle();
                                                strSkillId = streamList.get(i).getId();
                                            } else {
                                                //  Toast.makeText(PersonalInfoActivity.this, "No Skill found for selected category", Toast.LENGTH_SHORT).show();
                                            }
                                            adaStream.notifyDataSetChanged();
                                            // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                adaStream = new ArrayAdapter<String>(PersonalInfoActivity.this, android.R.layout.simple_spinner_dropdown_item, sp_stream_list);
                                binding.spStream.setAdapter(adaStream);
                                //  adaStream.notifyDataSetChanged();

                            }


                        } else {
                            Toast.makeText(PersonalInfoActivity.this, "No Skill found for selected category", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        // binding.spStream.setVisibility(View.INVISIBLE);

                        progress_spinner.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<interestedSkillResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();
                //  binding.spinner4.setVisibility(View.INVISIBLE);
                progress_spinner.dismiss();

                // binding.progressInfo.setVisibility(View.GONE);
                //    Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }

    private boolean checkStoragePermission() {
        return (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        permission = new PermissionManager() {
            @Override
            public List<String> setPermission() {
                List<String> permssions = new ArrayList<>();
                permssions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permssions.add(Manifest.permission.CAMERA);
                return permssions;
            }
        };

        permission.checkAndRequestPermissions((Activity) PersonalInfoActivity.this);
    }

    public void PersonalDetailApi() {

        binding.progresspersonal.setVisibility(View.VISIBLE);
        if (strDivision.equals("Select Division")) {

            strDivision = "";
        } else if (strSubject.equals("Select Subject")) {

            strSubject = "";
        } else {

            Map<String, RequestBody> map = new HashMap<>();

            map.put("user_id", Utils.getRequestBodyParameter(userid));
            map.put("name", Utils.getRequestBodyParameter(binding.edtName.getText().toString().trim()));
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
            map.put("category_id", Utils.getRequestBodyParameter(strCategoryId));
            map.put("skill_id", Utils.getRequestBodyParameter(strSkillId));
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

                            AppPrefrences.setCategoryId(PersonalInfoActivity.this, strCategoryId);

                            Intent i = new Intent(PersonalInfoActivity.this, AcademicDetailsActivity.class);
                            // i.putExtra("experience",strfre_exp);
                            i.putExtra("userid", userid);
                            i.putExtra("experience", strfre_exp);

                            startActivity(i);

                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);

                        } else {
                            //   pd_loading.setVisibility(View.GONE);
                            binding.progresspersonal.setVisibility(View.GONE);

                            Snackbar.make(findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_LONG)
                                    .setActionTextColor(Color.RED)
                                    .show();


                        }
                    } else {
                        // Toast.makeText(PersonalInfoActivity.this, "ERROR" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        //
                    }

                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    // pd_loading.setVisibility(View.GONE);
                    binding.progresspersonal.setVisibility(View.GONE);

                  /* try{
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                           if (!Environment.isExternalStorageManager()) {
                               snackbar.make(findViewById(android.R.id.content), "Permission needed!", Snackbar.LENGTH_INDEFINITE)
                                       .setAction("Settings", new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                               *//* Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);*//*

                                               try {
                                                   Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                                                   Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                                                   startActivity(intent);
                                               } catch (Exception ex) {
                                                   Intent intent = new Intent();
                                                   intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                                   startActivity(intent);
                                               }
                                           }
                                       })
                                       .show();

                           }
                       }
                   }
                   catch (Exception e){}*/
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        // getStateListApi();
        //getAnnualSalary();

    }
   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);

        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.shakti_consultant_logo);
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                finishAffinity();
                System.exit(0);

                   /* int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    finishAffinity();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);

                    finishAffinity();
                    System.exit(0);
*/
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}