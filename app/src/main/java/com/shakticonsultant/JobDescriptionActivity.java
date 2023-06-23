package com.shakticonsultant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.JobSkillListAdapter;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.ActivityJobDescriptionBinding;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.JobDetailResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDescriptionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    int year, month, day;
    EditText date1, date2;
    String strdate1 = "", strdate2 = "";

    ActivityJobDescriptionBinding binding;
    DatePickerDialog datePickerDialog;
    String job_id, skill_name, skill_id = "";
    boolean isSelected;
    ConnectionDetector cd;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        job_id = getIntent().getStringExtra("job_id");
        //     Toast.makeText(this, ""+job_id, Toast.LENGTH_SHORT).show();
        skill_name = getIntent().getStringExtra("skill_name");
        skill_id = getIntent().getStringExtra("skill_id");

        if (getIntent().getStringExtra("call") != null) {
            showDateDialog();
        }

        cd = new ConnectionDetector(JobDescriptionActivity.this);
        if (!cd.isConnectingToInternet()) {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        } else {
            binding.textView50.setText(skill_name);
            binding.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    share(getBitmapFromView(binding.nest, binding.nest.getChildAt(0).getHeight(), binding.nest.getChildAt(0).getWidth()));

                    // share(screenShot());

                }
            });

            getJobDetailApi();
            datePickerDialog = new DatePickerDialog(
                    JobDescriptionActivity.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY),
                    Calendar.getInstance().get(Calendar.DATE));

            binding.btnJobDetails.setOnClickListener(v -> {
                setButtonSelected(binding.btnJobDetails, binding.btnSimilarJobs);
                binding.layoutJobDescription.setVisibility(View.VISIBLE);
                binding.layoutSimilarJobs.setVisibility(View.GONE);

            });

            binding.imgSearch.setOnClickListener(v -> {

                if (!isSelected) {
                    isSelected = true;
                    binding.imgSearch.setImageResource(R.drawable.like_img);
                } else {
                    isSelected = false;
                    binding.imgSearch.setImageResource(R.drawable.favourite_icon);
                }


            });

            binding.btnSimilarJobs.setOnClickListener(v -> {
                setButtonSelected(binding.btnSimilarJobs, binding.btnJobDetails);
                binding.layoutJobDescription.setVisibility(View.GONE);
                binding.layoutSimilarJobs.setVisibility(View.VISIBLE);
                //getSimilarJobList(job_id);
                getSimilarJobList();
            });


            binding.imgBackArrow.setOnClickListener(v -> {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect) {
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }


    private boolean setImageSelected(ImageView imageToSelect, ImageView imageToDeSelect) {


        imageToSelect.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.main_text_color));
        imageToDeSelect.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black));
        return true;
    }


    public void getJobDetailApi() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        //  binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("job_id", job_id);
        map.put("user_id", AppPrefrences.getUserid(JobDescriptionActivity.this));
        map.put("skill_id", AppPrefrences.getSkillId(JobDescriptionActivity.this));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobDetailResponse> resultCall = apiInterface.callJobDetail(map);

        resultCall.enqueue(new Callback<JobDetailResponse>() {
            @Override
            public void onResponse(Call<JobDetailResponse> call, Response<JobDetailResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();

                    //   binding.progressBardetail.setVisibility(View.GONE);
                    binding.const1.setVisibility(View.VISIBLE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                      /*  GridLayoutManager linearLayoutManager = new GridLayoutManager(JobsListActivity.this,2);
                        binding.recycleSkillList.setLayoutManager(linearLayoutManager);
                        JobSkillListAdapter adapter=new JobSkillListAdapter(JobsListActivity.this,response.body().getData());
                        binding.recycleSkillList.setAdapter(adapter);
                        binding.recycleSkillList.getAdapter().notifyDataSetChanged();
*/

                        binding.tvrange.setText(response.body().getData().get(0).getStarting_salary() + "-" + response.body().getData().get(0).getMaximum_salary() + " LPA");
                        binding.tvExperience.setText(response.body().getData().get(0).getWork_experience());
                        binding.tvLocation.setText(response.body().getData().get(0).getLocation());
                        binding.textView51.setText(response.body().getData().get(0).getJob_description());
                        binding.tvdocans.setText(response.body().getData().get(0).getDocument_required());
                        binding.tvimportant.setText(response.body().getData().get(0).getImportant_instructions());

                        if (response.body().getData().get(0).getApplied_status() == 0) {

                            binding.btnApply.setVisibility(View.VISIBLE);
                            binding.btnApplied.setVisibility(View.GONE);

                        } else {
                            binding.btnApply.setVisibility(View.GONE);
                            binding.btnApplied.setVisibility(View.VISIBLE);
                        }
                        binding.btnApply.setOnClickListener(v -> {
                            // showSubscriptionDialog(response.body().getData().get(0).getId());

                            if (response.body().getData().get(0).getPackage_balance().equals("0")) {
                                showSubscriptionDialog(job_id, skill_name);
                            } else {
                                // onCreateCall=false;
                                showDateDialog();
                            }
                        });
                    } else {
                        //   binding.progressBardetail.setVisibility(View.GONE);
                        progress_spinner.dismiss();
                        binding.const1.setVisibility(View.GONE);
                        //lemprtNotification.setVisibility(View.VISIBLE);
                        Utils.showFailureDialog(JobDescriptionActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobDetailResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //  binding.progressBardetail.setVisibility(View.GONE);
                progress_spinner.dismiss();

                Utils.showFailureDialog(JobDescriptionActivity.this, "Something went wrong!");
            }
        });
    }

    private void showDateDialog() {
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_select_interview_date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        date1 = dialog.findViewById(R.id.btn_select_date_1);
        date2 = dialog.findViewById(R.id.btn_select_date_2);
        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);
        date1.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(JobDescriptionActivity.this, R.style.DatePickerTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            //  currentdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            date1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            strdate1 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        }
                    }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        date2.setOnClickListener(v -> {
            //datePickerDialog.show();

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(JobDescriptionActivity.this, R.style.DatePickerTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            //  currentdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            date2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            strdate2 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        }
                    }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        confirm.setOnClickListener(v -> {


            if (date1.getText().toString().trim().equals("")) {
                Snackbar snackbar = Snackbar.make(v, "Please Select First Prefered Date.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
                //   Toast.makeText(context, "Please Select First Prefered Date", Toast.LENGTH_SHORT).show();
            } else if (date2.getText().toString().trim().equals("")) {
                Snackbar snackbar = Snackbar.make(v, "Please Select Second Prefered Date.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
                //  Toast.makeText(context, "Please Select Second Prefered Date ", Toast.LENGTH_SHORT).show();
            } else if (date1.getText().toString().trim().equals(date2.getText().toString().trim())) {
                Snackbar snackbar = Snackbar.make(v, "Please Select Different Dates.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(getColor(R.color.purple_200));

                snackbar.show();
                // Toast.makeText(context, "Please Select Different Dates", Toast.LENGTH_SHORT).show();

            } else {

                getApplyJob(strdate1, strdate2, dialog);
                // dialog.dismiss();
            }
            //showConfirmationDialog();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }

    public void getApplyJob(String date1, String date2, Dialog dialog) {
        //binding.progressContatc.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(JobDescriptionActivity.this));
        map.put("job_id", job_id);
        map.put("interview_date_1", date1);
        map.put("interview_date_2", date2);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callApplyJob(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                progress_spinner.dismiss();
                if (response.isSuccessful()) {
                    //binding.progressContatc.setVisibility(View.GONE);
                    dialog.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {


                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(JobDescriptionActivity.this, R.style.AlertDialogTheme)
                                .setTitle(R.string.app_name)
                                .setMessage("Your job has been applied successfully.")
                                .setCancelable(false)
                                .setIcon(R.drawable.shakti_consultant_logo)
                                .setPositiveButton(Html.fromHtml("<font color='#BB274D'>Ok</font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showConfirmationDialog();
                                      /*  binding.edtEmail.setText("");
                                        binding.edtName.setText("");
                                        binding.edtText.setText("");*/
                                    }
                                });
                        logoutDialog.show();
                    } else {
                        //binding.progressContatc.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        Utils.showFailureDialog(JobDescriptionActivity.this, response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                // binding.progressContatc.setVisibility(View.GONE);
                progress_spinner.dismiss();

                //Utils.showFailureDialog(JobDescriptionActivity.this, "Something went wrong!");
            }
        });
    }


    private void showConfirmationDialog() {
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);

        ok.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i = new Intent(JobDescriptionActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        });

        faq.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i = new Intent(JobDescriptionActivity.this, FAQActivity.class);
            startActivity(i);
            finish();
        });

    }


    private void showSubscriptionDialog(String job_id, String skill_name) {
        Dialog dialog = new Dialog(JobDescriptionActivity.this);
        dialog.setContentView(R.layout.dialog_subscription_plan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_ok);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            //startActivity(new Intent(getApplicationContext(), ApplyJobPackageActivity.class));


            Intent i = new Intent(JobDescriptionActivity.this, PackageActivity.class);
            i.putExtra("job_id", job_id);
            i.putExtra("skill_name", skill_name);

            startActivity(i);
            //   finish();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            //showDateDialog();
        });
    }


//    private void showDateDialog(){
//        Dialog dialog = new Dialog(JobDescriptionActivity.this);
//        dialog.setContentView(R.layout.dialog_select_interview_date);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.show();
//
//        AppCompatButton date1 = dialog.findViewById(R.id.btn_select_date_1);
//        AppCompatButton date2 = dialog.findViewById(R.id.btn_select_date_2);
//        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
//        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);
//
//        date1.setOnClickListener(v -> {
//            datePickerDialog.show();
//        });
//
//        date2.setOnClickListener(v -> {
//            datePickerDialog.show();
//        });
//
//        confirm.setOnClickListener(v -> {
//            dialog.dismiss();
//            showConfirmationDialog();
//        });
//
//        cancel.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//
//
//        AppCompatButton ok = dialog.findViewById(R.id.btn_ok);
//        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel);
//
//        ok.setOnClickListener(v -> {
//            dialog.dismiss();
//            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
//        });
//
//        cancel.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//    }

//    private void showConfirmationDialog(){
//        Dialog dialog = new Dialog(JobDescriptionActivity.this);
//        dialog.setContentView(R.layout.dialog_interview_further_process);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.show();
//
//        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
//        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);
//
//        ok.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//        faq.setOnClickListener(v -> {
//            dialog.dismiss();
//        });
//
//    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


    public void getSimilarJobList() {

        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(this);
        progress_spinner.show();

        // binding.progressBardetail.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("skill_id", AppPrefrences.getSkillId(JobDescriptionActivity.this));
        map.put("user_id", AppPrefrences.getUserid(JobDescriptionActivity.this));
        map.put("job_id", job_id);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callJobSkillWiseList(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    //  binding.progressBardetail.setVisibility(View.GONE);
                    progress_spinner.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(JobDescriptionActivity.this);
                        binding.recyclerView7.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter = new JobSkillWiseListAdapter(JobDescriptionActivity.this, response.body().getData());
                        binding.recyclerView7.setAdapter(adapter);
                        binding.recyclerView7.getAdapter().notifyDataSetChanged();


                    } else {
                        //  binding.progressBardetail.setVisibility(View.GONE);
                        progress_spinner.dismiss();

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //    binding.progressBardetail.setVisibility(View.GONE);
                progress_spinner.dismiss();

                // Utils.showFailureDialog(JobDescriptionActivity.this, "Something went wrong!");
            }
        });
    }

    private Bitmap screenShot() {
        View rootView = getWindow().getDecorView().findViewById(R.id.nest); //Here also I have taken ScrollView too.

        Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(), rootView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        rootView.draw(canvas);
        return bitmap;
    }

    private void share(Bitmap bitmap) {
        String pathofBmp =
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        bitmap, "title", null);
        Uri uri = Uri.parse(pathofBmp);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "hello hello"));
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

}