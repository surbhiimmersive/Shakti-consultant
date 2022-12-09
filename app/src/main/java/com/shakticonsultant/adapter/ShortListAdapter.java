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
import com.shakticonsultant.responsemodel.FavouriteResponse;
import com.shakticonsultant.responsemodel.JobAppliedListDatumResponse;
import com.shakticonsultant.responsemodel.JobShortListDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShortListAdapter extends RecyclerView.Adapter<ShortListAdapter.ViewHolder> {

    List<JobShortListDatumResponse> list;
    Context context;

    public ShortListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public ShortListAdapter(Context context, List<JobShortListDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_shortlisted_job_layout, null);
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
        //viewHolder.txtDate.setText("Applied Date- "+list.get(position).getApplied_date());

        viewHolder.imageView19.setBackground(context.getDrawable(R.drawable.ic_like_c));

        viewHolder.imageView19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFavouriteAPi(list.get(position).getJob_id(),viewHolder);


            }
        });
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
        ImageView imageView19;
        Button btn_View;
     //   LinearLayout lJobCategory;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvTitle = (TextView) itemLayoutView.findViewById(R.id.tvNameShoet);
            tvPackage = (TextView) itemLayoutView.findViewById(R.id.tvPshortackage);
            tvYear = (TextView) itemLayoutView.findViewById(R.id.tvshortYear);
            tvLocation = (TextView) itemLayoutView.findViewById(R.id.tvshortlocation);
            txtDate = (TextView) itemLayoutView.findViewById(R.id.txtDate);
            btn_View = (Button) itemLayoutView.findViewById(R.id.btn_View);
            imageView19 = (ImageView) itemLayoutView.findViewById(R.id.imageView19);

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

    public void getFavouriteAPi(String jobid, ViewHolder viewHolder) {
        //  binding.progressBarcategory.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        //map.put("location", "7");
        map.put("user_id", AppPrefrences.getUserid(context));
        map.put("job_id", jobid);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FavouriteResponse> resultCall = apiInterface.callFavouriteApi(map);

        resultCall.enqueue(new Callback<FavouriteResponse>() {
            @Override
            public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {

                if (response.isSuccessful()) {
                    //binding.progressBarcategory.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        if(response.body().getFavorite()==1) {
                            viewHolder.imageView19.setBackground(context.getDrawable(R.drawable.ic_like_c));
                        }else{

                            viewHolder.imageView19.setBackground(context.getDrawable(R.drawable.black_like));

                        }
                    } else {
                        // binding.progressBarcategory.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else
                {


                }
            }

            @Override
            public void onFailure(Call<FavouriteResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //binding.progressBarcategory.setVisibility(View.GONE);

                // Utils.showFailureDialog(context, "Something went wrong!");
            }
        });
    }
}
