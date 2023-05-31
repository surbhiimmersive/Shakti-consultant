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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.AppliedJobActivity;
import com.shakticonsultant.MainActivity;
import com.shakticonsultant.R;
import com.shakticonsultant.RejectedApplicationActivity;
import com.shakticonsultant.ScheduleInterviewActivity;
import com.shakticonsultant.SpecificFacultyJobActivity;
import com.shakticonsultant.responsemodel.JobSkillDatumResponse;
import com.shakticonsultant.responsemodel.NotificationDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationDatumResponse> list;
    Context context;

    public NotificationAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public NotificationAdapter(Context context, List<NotificationDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_notification_layout, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


      //  viewHolder.tvTitle.setText(list.get(position).getName());
        viewHolder.tvdate.setText(list.get(position).getDate_time());

       String msg =list.get(position).getMessage().replace("\\n", "\n"); // The first backslash is doubled to find actual backslashes

        viewHolder.tvmsg.setText(msg);
      //  viewHolder.tvDate.setText(list.get(position).getIcon());

viewHolder.consnotification.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(list.get(position).getType().equals("1")){
            Intent i=new Intent(context, MainActivity.class);
            context.startActivity(i);

        } else if(list.get(position).getType().equals("2")){
            Intent i=new Intent(context, AppliedJobActivity.class);
            context.startActivity(i);

        }else if(list.get(position).getType().equals("4")){
            Intent i=new Intent(context, RejectedApplicationActivity.class);
            context.startActivity(i);

        }else if(list.get(position).getType().equals("5")){
            Intent i=new Intent(context, ScheduleInterviewActivity.class);
            context.startActivity(i);

        } else if(list.get(position).getType().equals("3")){
            Intent i=new Intent(context, AppliedJobActivity.class);
            context.startActivity(i);

        }
    }
});

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvmsg,tvdate;
        ConstraintLayout consnotification;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

          //  tvTitle = (TextView) itemLayoutView.findViewById(R.id.textView69);
            consnotification = (ConstraintLayout) itemLayoutView.findViewById(R.id.consnotification);
            tvdate = (TextView) itemLayoutView.findViewById(R.id.textView68);
            tvmsg = (TextView) itemLayoutView.findViewById(R.id.textView70);


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
