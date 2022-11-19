package com.shakticonsultant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DotsAdapter extends RecyclerView.Adapter<DotsAdapter.DotViewHolder> {

    Context context;
    int size, selectedPos;

    public DotsAdapter(Context context, int size, int selectedPos) {
        this.context = context;
        this.size = size;
        this.selectedPos = selectedPos;
    }

    @Override
    public DotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dots_list_items, null);
        DotViewHolder DotViewHolder = new DotViewHolder(context, view);
        return DotViewHolder;
    }

    @Override
    public void onBindViewHolder(DotViewHolder holder, int position) {
        if (position == selectedPos) {
            holder.dotImageView.setColorFilter(ContextCompat.getColor(context, R.color.main_text_color));
        } else {
            holder.dotImageView.setColorFilter(ContextCompat.getColor(context, R.color.home_indicator_bg));

        }

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class DotViewHolder extends RecyclerView.ViewHolder {

        ImageView dotImageView;

        public DotViewHolder(final Context context, View itemView) {
            super(itemView);
            dotImageView = (ImageView) itemView.findViewById(R.id.dotImageView);

        }
    }
}
