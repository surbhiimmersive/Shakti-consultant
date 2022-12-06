package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.ContactUsActivity;
import com.shakticonsultant.MainActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.CommonResponse;
import com.shakticonsultant.responsemodel.PackageDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyJobPackageListAdapter extends RecyclerView.Adapter<ApplyJobPackageListAdapter.ViewHolder> {

    List<PackageDatumResponse> list;
    Context context;
    DatePickerDialog datePickerDialog;
    int year,month,day;
    String job_id;
    String strdate1="",strdate2="";

    public ApplyJobPackageListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public ApplyJobPackageListAdapter(Context context, List<PackageDatumResponse> list,String job_id) {

        this.context = context;
        this.list = list;
        this.job_id=job_id;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package_list, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvHeading.setText(list.get(position).getHeading());
        viewHolder.tvPrice.setText(list.get(position).getPrice());
        viewHolder.tvJobs.setText(list.get(position).getNo_of_jobs());
        viewHolder.tvSubscriptiondays.setText(list.get(position).getSubscription_days());
        viewHolder.layout_diamond_package.setBackgroundColor(Color.parseColor(list.get(position).getBgcolor()));
viewHolder.layout_diamond_package.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(list.get(position).getPrice().equals("0")){

            showDateDialog();

        }else {


        }
    }
});

      //  viewHolder.tvDate.setText(list.get(position).getIcon());

      /*  Picasso.get()
                .load(ApiClient.Baseurl +list.get(position).getIcon())
                .resize(50, 50)
                .centerCrop()
                .into(viewHolder.imgIcon);
*/

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeading,tvTitle,tvPrice,tvSubscriptiondays,tvJobs;
        ImageView imgIcon;
        ConstraintLayout layout_diamond_package;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvHeading = (TextView) itemLayoutView.findViewById(R.id.textView53);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            tvSubscriptiondays = (TextView) itemLayoutView.findViewById(R.id.tvSubscriptiondays);
            tvJobs = (TextView) itemLayoutView.findViewById(R.id.tvJobs);
            layout_diamond_package = (ConstraintLayout) itemLayoutView.findViewById(R.id.layout_diamond_package);

        }


    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public  void clear(){
        int size = list.size();
        list.clear();

        notifyItemRangeRemoved(0, size);
    }

    private void showDateDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select_interview_date);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        AppCompatButton date1 = dialog.findViewById(R.id.btn_select_date_1);
        AppCompatButton date2 = dialog.findViewById(R.id.btn_select_date_2);
        AppCompatButton confirm = dialog.findViewById(R.id.btn_confirm_date);
        AppCompatButton cancel = dialog.findViewById(R.id.btn_cancel_date);
        date1.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                          //  currentdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            date1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            strdate1=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        }
                    }, year, month, day);

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        date2.setOnClickListener(v -> {
            //datePickerDialog.show();

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            //  currentdatejoining = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            date2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            strdate2=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        }
                    }, year, month, day);

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        confirm.setOnClickListener(v -> {


            if(strdate1.equals("")){

                Toast.makeText(context, "Please select preferred date1", Toast.LENGTH_SHORT).show();
            }else if(strdate2.equals("")){

                Toast.makeText(context, "Please select preferred date2", Toast.LENGTH_SHORT).show();
            }else {
                dialog.dismiss();
                getApplyJob(strdate1, strdate2);
            }
            //showConfirmationDialog();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }


    private void showConfirmationDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);

        ok.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i=new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((Activity)context).finish();

        });

        faq.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }


    public void getApplyJob(String date1,String date2) {
        //binding.progressContatc.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(context));
        map.put("job_id", job_id);
        map.put("interview_date_1", date1);
        map.put("interview_date_2", date2);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> resultCall = apiInterface.callApplyJob(map);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    //binding.progressContatc.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(context)
                                .setTitle(R.string.app_name)
                                .setMessage("Your job has been applied successfully.")
                                .setIcon(R.drawable.shakti_consultant_logo)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
               // binding.progressContatc.setVisibility(View.GONE);

                Utils.showFailureDialog(context, "Something went wrong!");
            }
        });
    }
}
