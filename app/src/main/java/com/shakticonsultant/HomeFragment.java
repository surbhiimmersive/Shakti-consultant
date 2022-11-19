package com.shakticonsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.shakticonsultant.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    ImageSlider image_slider;
    private ImageViewPagerAdapter adapter;
   private ViewPagerAdapter viewPagerAdapter;
   private  DotsAdapter dotsAdapter;
   private  RecyclerView dotsRecyclerView;
   private ViewPager viewPager;
    private int[] imageList = new int[] {R.drawable.slider_img,R.drawable.slider_img};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());


        dotsRecyclerView= binding.dotsRecyclerView;
        viewPager= binding.viewpager;
         adapter=new ImageViewPagerAdapter(getContext());
        viewPager.setAdapter(adapter);

        setDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % imageList.length;
                Log.d("onPageSelected", position + "");
                setDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        binding.openDrawer.setOnClickListener(v -> {
            ((MainActivity) getActivity()).openDrawer();
        });



        // for testing
        binding.textView39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JobsListActivity.class));
            }
        });

        binding.imageView16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NotificationActivity.class));
            }
        });

        binding.imageViewSearch.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SearchJobActivity.class));
        });


        binding.btnAllJob.setOnClickListener(v -> {
           setButtonSelected(binding.btnAllJob, binding.btnLatestJob);

        });


        binding.btnLatestJob.setOnClickListener(v -> {
            setButtonSelected(binding.btnLatestJob, binding.btnAllJob);

            });


        return binding.getRoot();
    }

    private void setDots(int selectedPos) {
        if (getContext() != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            dotsRecyclerView.setLayoutManager(linearLayoutManager);
            dotsAdapter = new DotsAdapter(getContext(), imageList.length, selectedPos);
            dotsRecyclerView.setAdapter(dotsAdapter);
        }
    }


    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect){
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }
}