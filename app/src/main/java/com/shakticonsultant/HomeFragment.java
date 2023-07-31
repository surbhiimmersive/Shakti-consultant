package com.shakticonsultant;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.shakticonsultant.adapter.ImageViewPagerAdapter;
import com.shakticonsultant.adapter.JobCategoryAdapter;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.FragmentHomeBinding;
import com.shakticonsultant.responsemodel.JobCategoryResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
import com.shakticonsultant.responsemodel.SliderDatumResponse;
import com.shakticonsultant.responsemodel.SliderResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    boolean homeclose = true;
    private List<SliderDatumResponse> sliderDatumResponses = new ArrayList<>();

    ImageSlider image_slider;
    private ImageViewPagerAdapter adapter;
    private ViewPagerAdapter viewPagerAdapter;
    private DotsAdapter dotsAdapter;
    private RecyclerView dotsRecyclerView;
    private ViewPager viewPager;
    private int[] imageList = new int[]{R.drawable.slider_img, R.drawable.slider_img};
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

        dotsRecyclerView = binding.dotsRecyclerView;
        viewPager = binding.viewpager;
       /*  adapter=new ImageViewPagerAdapter(getContext(),);
        viewPager.setAdapter(adapter);*/
        getJobCategory();
        getSliderApi();

        getLatestJob();
        setDots(0);
        AppPrefrences.setCLose(getActivity(), true);

        binding.openDrawer.setOnClickListener(v -> {
            ((MainActivity) getActivity()).openDrawer();
        });

        // for testing
       /* binding.textView39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JobsListActivity.class));
            }
        });*/

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
            getAllJob();

        });

        binding.btnLatestJob.setOnClickListener(v -> {
            setButtonSelected(binding.btnLatestJob, binding.btnAllJob);
            getLatestJob();

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


    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect) {
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }

    public void getJobCategory() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(getActivity());
        progress_spinner.show();


        // binding.progressBarcategory.setVisibility(View.VISIBLE);
        binding.tvhide.setVisibility(View.GONE);
        Map<String, String> map = new HashMap<>();
        // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobCategoryResponse> resultCall = apiInterface.callJobCategory();

        resultCall.enqueue(new Callback<JobCategoryResponse>() {
            @Override
            public void onResponse(Call<JobCategoryResponse> call, Response<JobCategoryResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    //  binding.progressBarcategory.setVisibility(View.GONE);
                    binding.tvhide.setVisibility(View.VISIBLE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerJobCategory.setLayoutManager(linearLayoutManager);
                        JobCategoryAdapter adapter = new JobCategoryAdapter(getActivity(), response.body().getData());
                        binding.recyclerJobCategory.setAdapter(adapter);
                        binding.recyclerJobCategory.getAdapter().notifyDataSetChanged();

                    } else {
                        progress_spinner.dismiss();

                        // binding.progressBarcategory.setVisibility(View.GONE);
                        binding.tvhide.setVisibility(View.VISIBLE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobCategoryResponse> call, Throwable t) {
                progress_spinner.dismiss();

                //binding.progressBarcategory.setVisibility(View.GONE);
                binding.tvhide.setVisibility(View.VISIBLE);

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //   Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }


    public void getSliderApi() {

        // pd_loading.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("position", "Main slider");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<SliderResponse> resultCall = apiInterface.callSliderApi(map);

        resultCall.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {

                if (response.isSuccessful()) {
                    //  pd_loading.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        sliderDatumResponses = response.body().getData();
                        if (sliderDatumResponses != null && sliderDatumResponses.size() > 0) {
                            /*for (int i = 0; i < sliderDatumResponses.size(); i++) {
                                AdModel bean = new AdModel();
                                bean.setImage(advertisingDatum.get(i).getImage());

                                arrayList.add(bean);
                            }*/

                            ImageViewPagerAdapter pagerAdapter = new ImageViewPagerAdapter(getActivity(), sliderDatumResponses);
                            viewPager.setAdapter(pagerAdapter);
                            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);

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


                        } else {
                            // Utils.showFailureDialog(getActivity(), "Please search valid keyword");
                        }
                    }
                }
            }


            @Override

            public void onFailure(Call<SliderResponse> call, Throwable t) {
                // pd_loading.setVisibility(View.GONE);
                // Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < sliderDatumResponses.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void getLatestJob() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(getActivity());
        progress_spinner.show();

        // binding.progressBarcategory.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        //  map.put("location", AppPrefrences.getLocation(getActivity()));
        map.put("location", "7");
        map.put("user_id", AppPrefrences.getUserid(getActivity()));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callLatestJob(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    //  binding.progressBarcategory.setVisibility(View.GONE);
                    //  binding.progressBarcategory.setVisibility(View.GONE);
                    progress_spinner.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        //  binding.recycLatestJob.setVisibility(View.VISIBLE);
                        //   binding.lEmpty.setVisibility(View.GONE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recycLatestJob.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter = new JobSkillWiseListAdapter(getActivity(), response.body().getData());
                        binding.recycLatestJob.setAdapter(adapter);
                        binding.recycLatestJob.getAdapter().notifyDataSetChanged();


                    } else {
                        //   binding.progressBarcategory.setVisibility(View.GONE);
                        // binding.lEmpty.setVisibility(View.VISIBLE);
                        //  binding.recycLatestJob.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                } else {
                    progress_spinner.dismiss();

                    //  binding.progressBarcategory.setVisibility(View.GONE);

                    // binding.progressBarcategory.setVisibility(View.GONE);
                    //  binding.lEmpty.setVisibility(View.VISIBLE);
                    //binding.recycLatestJob.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {
                //  binding.progressBarcategory.setVisibility(View.GONE);
                progress_spinner.dismiss();

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //  binding.progressBarcategory.setVisibility(View.GONE);
                //  // binding.lEmpty.setVisibility(View.VISIBLE);
                //  binding.recycLatestJob.setVisibility(View.GONE);

                //Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }

    public void getAllJob() {
        // binding.progressBarcategory.setVisibility(View.VISIBLE);
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(getActivity());
        progress_spinner.show();

        Map<String, String> map = new HashMap<>();
        //map.put("location", "7");
        map.put("user_id", AppPrefrences.getUserid(getActivity()));

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobSkillWiseListResponse> resultCall = apiInterface.callAllJobs(map);

        resultCall.enqueue(new Callback<JobSkillWiseListResponse>() {
            @Override
            public void onResponse(Call<JobSkillWiseListResponse> call, Response<JobSkillWiseListResponse> response) {

                if (response.isSuccessful()) {
                    progress_spinner.dismiss();
                    //binding.progressBarcategory.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {
                        // binding.lEmpty.setVisibility(View.GONE);
                        // binding.recycLatestJob.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recycLatestJob.setLayoutManager(linearLayoutManager);
                        JobSkillWiseListAdapter adapter = new JobSkillWiseListAdapter(getActivity(), response.body().getData());
                        binding.recycLatestJob.setAdapter(adapter);
                        binding.recycLatestJob.getAdapter().notifyDataSetChanged();


                    } else {
                        progress_spinner.dismiss();

                        // binding.progressBarcategory.setVisibility(View.GONE);
                        // binding.lEmpty.setVisibility(View.VISIBLE);
                        // binding.recycLatestJob.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                } else {
                    progress_spinner.dismiss();

                    // binding.progressBarcategory.setVisibility(View.GONE);
                    //binding.lEmpty.setVisibility(View.VISIBLE);
                    //binding.recycLatestJob.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<JobSkillWiseListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                //  binding.progressBarcategory.setVisibility(View.GONE);
                // binding.lEmpty.setVisibility(View.VISIBLE);
                progress_spinner.dismiss();

                // Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }


}