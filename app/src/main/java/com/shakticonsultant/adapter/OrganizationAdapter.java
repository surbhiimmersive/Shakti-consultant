package com.shakticonsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shakticonsultant.R;

import java.util.ArrayList;
import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ViewHolder> {

    public List<String> list=new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public OrganizationAdapter(Context context, ArrayList<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item_organization_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myTextView.setText(list.get(position));
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return list.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}