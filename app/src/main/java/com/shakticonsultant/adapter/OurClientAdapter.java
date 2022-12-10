package com.shakticonsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.OurClientDatumResponse;
import com.shakticonsultant.responsemodel.SliderDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class OurClientAdapter extends PagerAdapter {
    private  Context context;
   // private int[] imageList = new int[] {R.drawable.slider_img,R.drawable.slider_img};
    List<OurClientDatumResponse> list;

    LayoutInflater mLayoutInflater;
    public OurClientAdapter(Context context, List<OurClientDatumResponse> advertisingDatum){
      this.context=context;
        this.list = advertisingDatum;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_our_client, container, false);

        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView tvName = (TextView) itemView.findViewById(R.id.tvName);


       /* imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageList[position]);*/

        tvName.setText(list.get(position).getId());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get()
                .load(ApiClient.Photourl+list.get(position).getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView);
        //container.addView(imageView,0);

        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}