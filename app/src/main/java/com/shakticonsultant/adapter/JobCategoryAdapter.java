package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.JobsListActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.JobCategoryDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JobCategoryAdapter extends RecyclerView.Adapter<JobCategoryAdapter.ViewHolder> {

    List<JobCategoryDatumResponse> list;
    Context context;

    public JobCategoryAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public JobCategoryAdapter(Context context, List<JobCategoryDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_category, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        viewHolder.tvTitle.setText(list.get(position).getTitle());
      //  viewHolder.tvDate.setText(list.get(position).getIcon());

       /* Picasso.get()
                .load(ApiClient.Photourl+list.get(position).getIcon())
                .resize(50, 50)
                .centerCrop()
                .into(viewHolder.imgIcon);*/
        Picasso.get()
                .load(ApiClient.Photourl+list.get(position).getIcon())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgIcon);


        viewHolder.lJobCategory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

Intent i=new Intent(context,JobsListActivity.class);
i.putExtra("category_id",list.get(position).getId());
i.putExtra("category_name",list.get(position).getTitle());
context.startActivity(i);
        ((Activity)context).overridePendingTransition(R.anim.enter,
                R.anim.exit);
     //   context.startActivity(new Intent(context, JobsListActivity.class));

    }
});
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView imgIcon;
        LinearLayout lJobCategory;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvName);
            imgIcon = (ImageView) itemLayoutView.findViewById(R.id.imgicon);
            lJobCategory = (LinearLayout) itemLayoutView.findViewById(R.id.lJobCategory);


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
