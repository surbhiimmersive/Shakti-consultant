package com.shakticonsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.databinding.ActivityAcademicDetailsBinding;
import com.shakticonsultant.responsemodel.BoardDatumResponse;
import com.shakticonsultant.responsemodel.BoardResponse;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.EducationDatumResponse;
import com.shakticonsultant.responsemodel.EducationResponse;
import com.shakticonsultant.responsemodel.GetAcademicDetailResponse;
import com.shakticonsultant.responsemodel.GetEmployeeHistoryResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAcademicDetailsActivity extends AppCompatActivity {

    ActivityAcademicDetailsBinding binding;
 ApiInterface apiInterface;
    List<BoardDatumResponse> boardList=new ArrayList<>();
    List<EducationDatumResponse> graduationList=new ArrayList<>();
    List<EducationDatumResponse> postgraduationList=new ArrayList<>();
    ArrayList<String> sp_board_list=new ArrayList<>();
    ArrayList<String> sp_graduation_list=new ArrayList<>();
    ArrayList<String> sp_postgraduation_list=new ArrayList<>();
    String strBoard1;
    String strBoard2;
    String strgraduation="";
    String strpostgraduation="";
    String userid;
    String stryear1="",stryear2="",stryear3="",stryear4="";
ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAcademicDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       userid=getIntent().getStringExtra("userid");
       // Toast.makeText(this, "userid"+userid, Toast.LENGTH_SHORT).show();

        cd = new ConnectionDetector(GetAcademicDetailsActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            getBoardListApi();

           getEducationApi();
           getPostEducationApi();
            getAcademicdata();
            binding.spYear1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    stryear1 = (String) binding.spYear1.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            binding.spYear2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    stryear2 = (String) binding.spYear2.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spyear3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    stryear3 = (String) binding.spyear3.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.spYear4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    stryear4 = (String) binding.spYear4.getSelectedItem();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            binding.btnUpdate.setOnClickListener(v -> {

                if (strBoard1.equals("Select Board")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select board.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (binding.edtXPercent.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter Xth Percentage.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (stryear1.equals("Select Year")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select Xth year", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (strBoard2.equals("Select Board")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select XIIth board.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (binding.edtXIIPercent.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter XIIth Percentage.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (stryear2.equals("Select Year")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select XIIth year", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (strgraduation.equals("Select Graduation")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select Graduation.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (binding.edtSpecilization.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter specilization.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (stryear3.equals("Select Year")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please select graduation year", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (binding.edtGraduationPercentage.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter graduation percentage.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else if (binding.edtUniversityName.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please enter university name.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(getColor(R.color.purple_200));

                    snackbar.show();
                } else {
                    AcademicDetailApi();

                }
            });
        }


    }
///////-------------Borad Api lIst----------------------------------------------------------------
    public void getBoardListApi() {
        // binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<BoardResponse> resultCall = apiInterface.callBoardListApi();

        resultCall.enqueue(new Callback<BoardResponse>() {
            @Override
            public void onResponse(Call<BoardResponse> call, Response<BoardResponse> response) {

                if (response.isSuccessful()) {
                    sp_board_list.clear();
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        boardList=response.body().getData();

                        if(boardList.size()>0){
                            sp_board_list.add("Select Board");
                            for(int i=0;i<boardList.size();i++){

                                sp_board_list.add(boardList.get(i).getBoard());
                                // spinner_state_list.add(model);

                                binding.spBoard1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strBoard1=(String)binding.spBoard1.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                       // String id = boardList.get(i).getId();

                                        //   Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                // spinner_state_list.add(model);
                                ArrayAdapter<String> adp=new ArrayAdapter<String>(GetAcademicDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_board_list);
                                binding.spBoard1.setAdapter(adp);
                                adp.notifyDataSetChanged();



                                binding.spBoard2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        strBoard2=(String)binding.spBoard2.getSelectedItem();

                                        // SpinnerModel model=(SpinnerModel) spinner_state_list.get(i);
                                      //  String id = boardList.get(i).getId();

                                        //   Toast.makeText(PersonalInfoActivity.this, "state" + id, Toast.LENGTH_SHORT).show();



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                            ArrayAdapter<String> adp1=new ArrayAdapter<String>(GetAcademicDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_board_list);
                            binding.spBoard2.setAdapter(adp1);
                            adp1.notifyDataSetChanged();
                        }



                    } else {
                        // binding.progressInfo.setVisibility(View.GONE);

                        //  Utils.showFailureDialog(PersonalInfoActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<BoardResponse> call, Throwable t) {

                //  binding.progressInfo.setVisibility(View.GONE);
                //   Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }
///////-------------Borad Api lIst----------------------------------------------------------------


    ///////---------------Education Api-----------------


    public void getEducationApi() {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("degree_type", "Graduation");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<EducationResponse> resultCall = apiInterface.callEducationList(map);

        resultCall.enqueue(new Callback<EducationResponse>() {
            @Override
            public void onResponse(Call<EducationResponse> call, Response<EducationResponse> response) {
                sp_graduation_list.clear();
                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        graduationList=response.body().getData();

                        if(graduationList.size()>0){
                            sp_graduation_list.add("Select Graduation");
                            for(int i=0;i<graduationList.size();i++){

                                sp_graduation_list.add(graduationList.get(i).getDegree_title());

                                binding.spGraduation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strgraduation=(String)binding.spGraduation.getSelectedItem();
                                   // String   strId = graduationList.get(i).getId();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                ArrayAdapter<String> adp=new ArrayAdapter<String>(GetAcademicDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_graduation_list);
                                binding.spGraduation.setAdapter(adp);
                                adp.notifyDataSetChanged();

                            }


                        }

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<EducationResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }


    ///////---------------Post Education Api-----------------


    public void getPostEducationApi() {
        //  binding.progressInfo.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("degree_type", "PostGraduation");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<EducationResponse> resultCall = apiInterface.callEducationList(map);

        resultCall.enqueue(new Callback<EducationResponse>() {
            @Override
            public void onResponse(Call<EducationResponse> call, Response<EducationResponse> response) {
                sp_graduation_list.clear();
                if (response.isSuccessful()) {
                    // binding.progressInfo.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        postgraduationList=response.body().getData();

                        if(postgraduationList.size()>0){
                            sp_postgraduation_list.add("Select Post Graduation");

                            for(int i=0;i<postgraduationList.size();i++){

                                sp_postgraduation_list.add(postgraduationList.get(i).getDegree_title());

                                binding.spPostGraduation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        strpostgraduation=(String)binding.spPostGraduation.getSelectedItem();
                                    // String   strId = postgraduationList.get(i).getId();
                                        // Toast.makeText(PersonalInfoActivity.this, "city"+id, Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                ArrayAdapter<String> adp=new ArrayAdapter<String>(GetAcademicDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_postgraduation_list);
                                binding.spPostGraduation.setAdapter(adp);
                                adp.notifyDataSetChanged();

                            }


                        }

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<EducationResponse> call, Throwable t) {
                // Toast.makeText(PersonalInfoActivity.this, "no data", Toast.LENGTH_SHORT).show();

                // binding.progressInfo.setVisibility(View.GONE);
                //  Utils.showFailureDialog(PersonalInfoActivity.this, "Something went wrong!");
            }
        });
    }



    public void AcademicDetailApi() {
        binding.progressacadeic.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", userid);
        map.put("board_X", strBoard1);
        map.put("passed_year_X",stryear1);
        map.put("percentage_X", binding.edtXPercent.getText().toString().trim());
        map.put("board_XII", strBoard2);
        map.put("passed_year_XII", stryear2);
        map.put("percentage_XII", binding.edtXIIPercent.getText().toString().trim());
        map.put("degree_graduation", strgraduation);
        map.put("specialization_graduation", binding.edtSpecilization.getText().toString().trim());
        map.put("passed_year_graduation", stryear3);
        map.put("percentage_graduation", binding.edtGraduationPercentage.getText().toString().trim());
        map.put("university_graduation", binding.edtUniversityName.getText().toString().trim());
        map.put("degree_postgraduation", strpostgraduation);
        map.put("specialization_postgraduation", binding.someEdit14.getText().toString().trim());
        map.put("passed_year_postgraduation", stryear4);
        map.put("percentage_postgraduation", binding.someEdit15.getText().toString().trim());
        map.put("university_postgraduation", binding.someEdit16.getText().toString().trim());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callAcademyDetailApi(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressacadeic.setVisibility(View.GONE);

                    // binding.progressBar2.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {


                        finish();
                    }  else {
                        Utils.showFailureDialog(GetAcademicDetailsActivity.this, response.body().getMessage());


                    }
                }else{
                    Utils.showFailureDialog(GetAcademicDetailsActivity.this,"Please try sometime later.");
                     binding.progressacadeic.setVisibility(View.GONE);



                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
               // binding.progressBar2.setVisibility(View.GONE);
                binding.progressacadeic.setVisibility(View.GONE);

                Utils.showFailureDialog(GetAcademicDetailsActivity.this, "Something went wrong!");
            }
        });
    }
    public void getAcademicdata () {
        binding.progressacadeic.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(GetAcademicDetailsActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetAcademicDetailResponse> resultCall = apiInterface.callGetAcademicDetails(map);

        resultCall.enqueue(new Callback<GetAcademicDetailResponse>() {
            @Override
            public void onResponse(Call<GetAcademicDetailResponse> call, Response<GetAcademicDetailResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressacadeic.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        Log.e("User ID", response.body().getData().getId());
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        /*String content = response.body().getData().getContent();
                        binding.progressemployee.setText(content);
                        //Toast.makeText(SignInActivity.this, "Detail"+personal, Toast.LENGTH_SHORT).show();
                   */

                        binding.edtXPercent.setText(response.body().getData().getPercentage_X());
                        binding.edtXIIPercent.setText(response.body().getData().getPercentage_XII());
                        binding.edtGraduationPercentage.setText(response.body().getData().getPercentage_graduation());
                        binding.someEdit15.setText(response.body().getData().getPercentage_postgraduation());
                        binding.edtSpecilization.setText(response.body().getData().getSpecialization_graduation());
                        binding.someEdit14.setText(response.body().getData().getSpecialization_postgraduation());
                        binding.someEdit16.setText(response.body().getData().getUniversity_postgraduation());
                        binding.edtUniversityName.setText(response.body().getData().getUniversity_graduation());

                        binding.spBoard1.setSelection(sp_board_list.indexOf(response.body().getData().getBoard_X()));
                        binding.spBoard2.setSelection(sp_board_list.indexOf(response.body().getData().getBoard_XII()));
                        binding.spGraduation.setSelection(sp_graduation_list.indexOf(response.body().getData().getDegree_graduation()));
                        binding.spPostGraduation.setSelection(sp_postgraduation_list.indexOf(response.body().getData().getDegree_postgraduation()));

                        String[] year = getResources().getStringArray(R.array.year);
                        binding.spYear1.setSelection(Arrays.asList(year).indexOf(response.body().getData().getPassed_year_X()));
                        binding.spYear2.setSelection(Arrays.asList(year).indexOf(response.body().getData().getPassed_year_XII()));
                        binding.spyear3.setSelection(Arrays.asList(year).indexOf(response.body().getData().getPassed_year_graduation()));
                        binding.spYear4.setSelection(Arrays.asList(year).indexOf(response.body().getData().getPassed_year_postgraduation()));



                    /*    ArrayAdapter<String> currentadp=new ArrayAdapter<String>(GetAcademicDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item,sp_board_list);
                        currentadp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spBoard1.setAdapter(currentadp);

                        if (!response.body().getData().getBoard_X().equals(null)) {
                            int spinnerPosition = currentadp.getPosition(response.body().getData().getBoard_X());
                            binding.spBoard1.setSelection(spinnerPosition);
                        }

*/


                    } else {
                        Utils.showFailureDialog(GetAcademicDetailsActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<GetAcademicDetailResponse> call, Throwable t) {
                binding.progressacadeic.setVisibility(View.GONE);
                Utils.showFailureDialog(GetAcademicDetailsActivity.this, "Something went wrong!");
            }
        });
    }


}