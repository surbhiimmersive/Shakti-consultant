package com.shakticonsultant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shakticonsultant.adapter.AppliedJobListAdapter;
import com.shakticonsultant.databinding.FragmentAppliedJobsBinding;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppliedJobsActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppliedJobsActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppliedJobsActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppliedJobsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppliedJobsActivity newInstance(String param1, String param2) {
        AppliedJobsActivity fragment = new AppliedJobsActivity();
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

    FragmentAppliedJobsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppliedJobsBinding.inflate(getLayoutInflater());


        AppPrefrences.setCLose(getActivity(),false);

        binding.filters.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), JobFiltersActivity.class));
        });
        binding.imageView21.setOnClickListener(v -> {


            getActivity().onBackPressed();
            getActivity().overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        });
        getAppliedJob();

        return binding.getRoot();
    }


    public void getAppliedJob() {
        Dialog progress_spinner;
        progress_spinner = Utils.LoadingSpinner(getActivity());
        progress_spinner.show();

        // binding.progressBarcategory.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(getActivity()));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobAppliedListResponse> resultCall = apiInterface.callAppliedJob(map);

        resultCall.enqueue(new Callback<JobAppliedListResponse>() {
            @Override
            public void onResponse(Call<JobAppliedListResponse> call, Response<JobAppliedListResponse> response) {

                if (response.isSuccessful()) {
                   // binding.progressBarcategory.setVisibility(View.GONE);
                    progress_spinner.dismiss();
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.imageView23.setVisibility(View.VISIBLE);
                        binding.recyclerAppliedJob.setVisibility(View.VISIBLE);
                        binding.lEmpty.setVisibility(View.GONE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerAppliedJob.setLayoutManager(linearLayoutManager);
                        AppliedJobListAdapter adapter=new AppliedJobListAdapter(getActivity(),response.body().getData());
                        binding.recyclerAppliedJob.setAdapter(adapter);
                        binding.recyclerAppliedJob.getAdapter().notifyDataSetChanged();



                    } else {
                        progress_spinner.dismiss();

                        //  binding.progressBarcategory.setVisibility(View.GONE);
                        binding.imageView23.setVisibility(View.GONE);
                        binding.recyclerAppliedJob.setVisibility(View.GONE);
                        binding.lEmpty.setVisibility(View.VISIBLE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else
                {
                    progress_spinner.dismiss();

                    binding.imageView23.setVisibility(View.GONE);
                    binding.recyclerAppliedJob.setVisibility(View.GONE);
                    binding.lEmpty.setVisibility(View.VISIBLE);
                   // binding.progressBarcategory.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JobAppliedListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.imageView23.setVisibility(View.GONE);
                binding.recyclerAppliedJob.setVisibility(View.GONE);
                binding.lEmpty.setVisibility(View.VISIBLE);
                //binding.progressBarcategory.setVisibility(View.GONE);
                progress_spinner.dismiss();

               // Utils.showFailureDialog(getActivity(), "Please try again sometime later..");
            }
        });
    }
}