package com.shakticonsultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
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
        imageView.setRotation(360);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

       /* Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.slide_in_right);

// Start the animation like this
        imageView.startAnimation(animSlide);*/
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