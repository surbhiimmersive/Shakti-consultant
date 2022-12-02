package com.shakticonsultant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.FaqsDatumResponse;
import com.shakticonsultant.responsemodel.JobCategoryDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.ViewHolder> {

    List<FaqsDatumResponse> list;
    Context context;

    public FaqListAdapter(Context context) {

        this.context = context;
        this.list = list;
    }

    public FaqListAdapter(Context context, List<FaqsDatumResponse> list) {

        this.context = context;
        this.list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq_list, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        viewHolder.tvquestion.setText(list.get(position).getQuestion());
        viewHolder.tvanswer.setText(list.get(position).getAnswer());
        viewHolder.dummy.setText(list.get(position).getAnswer());

        viewHolder.imgArrow.setVisibility(View.VISIBLE);
        viewHolder.dummy.setVisibility(View.VISIBLE);


        viewHolder.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.tvanswer.setVisibility(View.VISIBLE);
                viewHolder.imgArrow.setVisibility(View.GONE);
                viewHolder.imgup.setVisibility(View.VISIBLE);
                viewHolder.tvanswer.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.tvquestion.setTextColor(Color.parseColor("#FFFFFF"));
                viewHolder.dummy.setTextColor(Color.parseColor("#BB274D"));
                viewHolder.layout_faq_1.setBackgroundColor(Color.parseColor("#BB274D"));
                viewHolder.dummy.setVisibility(View.VISIBLE);

            }
        });

        viewHolder.imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.tvanswer.setVisibility(View.GONE);
                viewHolder.imgArrow.setVisibility(View.VISIBLE);
                viewHolder.imgup.setVisibility(View.GONE);
                viewHolder.dummy.setVisibility(View.VISIBLE);
                viewHolder.dummy.setTextColor(Color.parseColor("#FFFFFF"));

                viewHolder.tvanswer.setTextColor(Color.parseColor("#000000"));
                viewHolder.tvquestion.setTextColor(Color.parseColor("#000000"));
                viewHolder.layout_faq_1.setBackgroundColor(Color.parseColor("#FFFFFF"));


            }
        });

      //  viewHolder.tvDate.setText(list.get(position).getIcon());

       /* Picasso.get()
                .load(ApiClient.Baseurl +list.get(position).q())
                .resize(50, 50)
                .centerCrop()
                .into(viewHolder.imgIcon);
*/

    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvquestion,tvanswer,dummy;
        ImageView imgArrow,imgup;
        LinearLayout layout_faq_1;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvquestion = (TextView) itemLayoutView.findViewById(R.id.question);
            dummy = (TextView) itemLayoutView.findViewById(R.id.dummy);
            tvanswer = (TextView) itemLayoutView.findViewById(R.id.answer);
            imgArrow = (ImageView) itemLayoutView.findViewById(R.id.imgArrow);
            imgup = (ImageView) itemLayoutView.findViewById(R.id.imguparrpw);
            layout_faq_1 = (LinearLayout) itemLayoutView.findViewById(R.id.layout_faq_1);


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
