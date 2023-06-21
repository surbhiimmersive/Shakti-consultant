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
import com.shakticonsultant.SpecificFacultyJobActivity;
import com.shakticonsultant.responsemodel.JobCategoryDatumResponse;
import com.shakticonsultant.responsemodel.JobSkillDatumResponse;
import com.shakticonsultant.responsemodel.JobSkillResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JobSkillListAdapter extends RecyclerView.Adapter<JobSkillListAdapter.ViewHolder> {

    List<JobSkillDatumResponse> list;
    Context context;


    public JobSkillListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public JobSkillListAdapter(Context context, List<JobSkillDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jobskill_list, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.tvTitle.setText(list.get(position).getTitle());
      //  viewHolder.tvDate.setText(list.get(position).getIcon());

        Picasso.get()
                .load(ApiClient.Photourl+list.get(position).getIcon())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(viewHolder.imgIcon);

viewHolder.lJobCategory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

Intent i=new Intent(context,SpecificFacultyJobActivity.class);
i.putExtra("skill_id",list.get(position).getId());
i.putExtra("skill_name",list.get(position).getTitle());
context.startActivity(i);
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
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

            tvTitle = (TextView) itemLayoutView.findViewById(R.id.textView46);
            imgIcon = (ImageView) itemLayoutView.findViewById(R.id.imageView20);
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
