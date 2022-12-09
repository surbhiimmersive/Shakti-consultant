package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
        viewHolder.tvdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder logoutDialog = new AlertDialog.Builder(context)
                        .setTitle(R.string.app_name)
                        .setMessage("Details  \n" +
                                "* Package valid for one year \n" +
                                "* Live job application status \n" +
                                "* Be a priority applicant and increase your chance of get a job fast \n" +
                                "* 100*% Job and Salary security \n" +
                                "* Get first priority over one lacs + Users  \n" +
                                "* You can apply upto 150 jobs \n" +
                                "* Check the name of the company before applying on any jobs \n" +
                                "* Receive profile based relevant jobs through App notifications, SMS and emails \n" +
                                "* Selfy resume, Online test and Interviews \n" +
                                "* No Hidden charges or any other charges, Subscription will not applicable for job applied before subscription package")
                        .setIcon(R.drawable.shakti_consultant_logo)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                logoutDialog.show();

            }
        });
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

        TextView tvHeading,tvTitle,tvPrice,tvSubscriptiondays,tvJobs,tvdetail;
        ImageView imgIcon;
        ConstraintLayout layout_diamond_package;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvHeading = (TextView) itemLayoutView.findViewById(R.id.textView53);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            tvSubscriptiondays = (TextView) itemLayoutView.findViewById(R.id.tvSubscriptiondays);
            tvJobs = (TextView) itemLayoutView.findViewById(R.id.tvJobs);
            tvdetail = (TextView) itemLayoutView.findViewById(R.id.tvdetail);
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



    }



