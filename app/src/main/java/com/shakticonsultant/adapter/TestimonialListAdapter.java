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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.PackageDatumResponse;
import com.shakticonsultant.responsemodel.TestimonialDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class TestimonialListAdapter extends RecyclerView.Adapter<TestimonialListAdapter.ViewHolder> {

    List<TestimonialDatumResponse> list;
    Context context;
    DatePickerDialog datePickerDialog;
    int year,month,day;

    public TestimonialListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public TestimonialListAdapter(Context context, List<TestimonialDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_testimonial, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvUsername.setText(list.get(position).getUsername());
        Picasso.get()
                .load(ApiClient.Photourl+list.get(position).getIcon())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgicon);

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvUsername;
        ImageView imgicon;
        ConstraintLayout layout_diamond_package;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvTitle);
            tvUsername = (TextView) itemLayoutView.findViewById(R.id.tvUsername);
            imgicon = (ImageView) itemLayoutView.findViewById(R.id.imgicon);

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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
