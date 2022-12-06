package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.EmployeeHistoryActivity;
import com.shakticonsultant.JobDescriptionActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.JobCategoryDatumResponse;
import com.shakticonsultant.responsemodel.PackageDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    List<PackageDatumResponse> list;
    Context context;
    DatePickerDialog datePickerDialog;
    int year,month,day;

    public PackageListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public PackageListAdapter(Context context, List<PackageDatumResponse> list) {

        this.context = context;
        this.list = list;
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

           // showDateDialog();

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

                        }
                    }, year, month, day);

            //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

        confirm.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmationDialog();
        });

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }


    private void showConfirmationDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_interview_further_process);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        AppCompatButton ok = dialog.findViewById(R.id.btn_interview_ok);
        AppCompatButton faq = dialog.findViewById(R.id.btn_faq);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
        });

        faq.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }


}
