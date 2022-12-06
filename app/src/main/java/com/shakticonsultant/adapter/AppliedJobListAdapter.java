package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.JobDescriptionActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.JobAppliedListDatumResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListDatumResponse;

import java.util.List;

public class AppliedJobListAdapter extends RecyclerView.Adapter<AppliedJobListAdapter.ViewHolder> {

    List<JobAppliedListDatumResponse> list;
    Context context;

    public AppliedJobListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public AppliedJobListAdapter(Context context, List<JobAppliedListDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_applied_job_layout, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tvPackage.setText(list.get(position).getStarting_salary()+" "+list.get(position).getPay_according());
        viewHolder.tvLocation.setText(list.get(position).getLocation());
        viewHolder.tvYear.setText(list.get(position).getWork_experience());
        viewHolder.txtDate.setText("Applied Date- "+list.get(position).getApplied_date());

        viewHolder.btn_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(context, JobDescriptionActivity.class);
                i.putExtra("job_id",list.get(position).getJob_id());
                i.putExtra("skill_name",list.get(position).getTitle());

                context.startActivity(i);
            }
        });
      //  viewHolder.tvDate.setText(list.get(position).getIcon());

/*
viewHolder.lJobCategory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

Intent i=new Intent(context,SpecificFacultyJobActivity.class);
i.putExtra("skill_id",list.get(position).getId());
i.putExtra("skill_name",list.get(position).getTitle());
context.startActivity(i);
    }
});*/
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvPackage,tvYear,tvLocation,txtDate;
        ImageView imgIcon;
        Button btn_View;
     //   LinearLayout lJobCategory;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTitle = (TextView) itemLayoutView.findViewById(R.id.textView44);
            tvPackage = (TextView) itemLayoutView.findViewById(R.id.tvPackage);
            tvYear = (TextView) itemLayoutView.findViewById(R.id.tvYear);
            tvLocation = (TextView) itemLayoutView.findViewById(R.id.tvlocation);
            txtDate = (TextView) itemLayoutView.findViewById(R.id.txtDate);
            btn_View = (Button) itemLayoutView.findViewById(R.id.btn_View);

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
