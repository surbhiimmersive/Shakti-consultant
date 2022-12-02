package com.shakticonsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.shakticonsultant.R;
import com.shakticonsultant.responsemodel.SliderDatumResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewPagerAdapter extends PagerAdapter {
    private  Context context;
   // private int[] imageList = new int[] {R.drawable.slider_img,R.drawable.slider_img};
    List<SliderDatumResponse> advertisingDatum;


    public ImageViewPagerAdapter(Context context, List<SliderDatumResponse> advertisingDatum){
      this.context=context;
        this.advertisingDatum = advertisingDatum;

    }


    @Override
    public int getCount() {
        return advertisingDatum.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
       /* imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageList[position]);*/
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Picasso.get()
                .load(ApiClient.Photourl+advertisingDatum.get(position).getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageView);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}