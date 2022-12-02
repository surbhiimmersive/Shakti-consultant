package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.JobCategoryDatumResponse;
import com.shakticonsultant.responsemodel.PackageDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    List<PackageDatumResponse> list;
    Context context;

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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvHeading.setText(list.get(position).getHeading());
        viewHolder.tvPrice.setText(list.get(position).getPrice());
        viewHolder.tvJobs.setText(list.get(position).getNo_of_jobs());
        viewHolder.tvSubscriptiondays.setText(list.get(position).getSubscription_days());
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

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvHeading = (TextView) itemLayoutView.findViewById(R.id.textView53);
            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            tvSubscriptiondays = (TextView) itemLayoutView.findViewById(R.id.tvSubscriptiondays);
            tvJobs = (TextView) itemLayoutView.findViewById(R.id.tvJobs);


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
