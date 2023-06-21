package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.CheckoutActivity;
import com.shakticonsultant.PackageActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.PackageDatumResponse;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    List<PackageDatumResponse> list;
    PackageDatumResponse module;
    Context context;
    OnItemClickListener listener;

    public PackageListAdapter(Context context) {
        this.context = context;
        this.list = list;
    }

    public PackageListAdapter(Context context, List<PackageDatumResponse> list, OnItemClickListener listener) {

        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public PackageListAdapter(Context context, PackageDatumResponse module) {

        this.context = context;
        this.module = module;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package_list, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        if (module == null) {
            viewHolder.tvTitle.setText(list.get(position).getTitle());
            viewHolder.tvHeading.setText(list.get(position).getHeading());
            viewHolder.tvPrice.setText(list.get(position).getPrice());
            viewHolder.tvJobs.setText(list.get(position).getNo_of_jobs());
            viewHolder.tvSubscriptiondays.setText(list.get(position).getSubscription_days());

            viewHolder.layout_diamond_package.setBackgroundColor(Color.parseColor(list.get(position).getBgcolor()));

        } else {
            viewHolder.tvTitle.setText(module.getTitle());
            viewHolder.tvHeading.setText(module.getHeading());
            viewHolder.tvPrice.setText(module.getPrice());
            viewHolder.tvJobs.setText(module.getNo_of_jobs());
            viewHolder.tvSubscriptiondays.setText(module.getSubscription_days());

            viewHolder.layout_diamond_package.setBackgroundColor(Color.parseColor(module.getBgcolor()));
        }


/*
        viewHolder.layout_diamond_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(position).getPackage_balance().equals("0")){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.shakti_consultant_logo);
                    builder.setMessage("Please upgrade your package.");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            context.startActivity(new Intent(context, PaymentInrigationNew.class));

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    android.app.AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
*/
        viewHolder.tvdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder logoutDialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                        .setTitle(R.string.app_name)

                        .setMessage("Details  \n" + list.get(position).getDescription())
                        .setIcon(R.drawable.shakti_consultant_logo)
                        .setPositiveButton(Html.fromHtml("<font color='#BB274D'>Ok</font>"), new DialogInterface.OnClickListener() {
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
                if (list.get(position).getId() == (5)) {
                    // getSubscriptionApi(list.get(position).getId());
                } else {

                    if (module == null) {
                        listener.onItemClick(list.get(position).getId(), list.get(position).getPrice());
                        // context.startActivity(new Intent(context, PackageActivity.class).putExtra("package_id", list.get(position).getId()));
                    } else {
                        listener.onItemClick(module.getId(), module.getPrice());
                        // context.startActivity(new Intent(context, PackageActivity.class).putExtra("package_id", module.getId()));
                    }
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

        TextView tvHeading, tvTitle, tvPrice, tvSubscriptiondays, tvJobs, tvdetail;
        ImageView imgIcon;
        ConstraintLayout layout_diamond_package;
        LinearLayout l1;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvHeading = (TextView) itemLayoutView.findViewById(R.id.textView53);
            l1 = (LinearLayout) itemLayoutView.findViewById(R.id.linear);
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
        return module == null ? list.size() : 1;
    }

    public void clear() {
        int size = list.size();
        list.clear();

        notifyItemRangeRemoved(0, size);
    }

    public interface OnItemClickListener {
        void onItemClick(int package_id, String amount);
    }
}


