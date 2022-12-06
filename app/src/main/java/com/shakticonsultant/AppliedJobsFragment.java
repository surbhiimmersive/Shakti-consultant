package com.shakticonsultant;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shakticonsultant.adapter.AppliedJobListAdapter;
import com.shakticonsultant.adapter.JobSkillWiseListAdapter;
import com.shakticonsultant.databinding.FragmentAppliedJobsBinding;
import com.shakticonsultant.responsemodel.JobAppliedListResponse;
import com.shakticonsultant.responsemodel.JobSkillWiseListResponse;
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
 * Use the {@link AppliedJobsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppliedJobsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppliedJobsFragment() {
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
    public static AppliedJobsFragment newInstance(String param1, String param2) {
        AppliedJobsFragment fragment = new AppliedJobsFragment();
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

        binding.filters.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), JobFiltersActivity.class));
        });
        binding.imageView21.setOnClickListener(v -> {


            getActivity().onBackPressed();
        });
        getAppliedJob();

        return binding.getRoot();
    }


    public void getAppliedJob() {
        binding.progressBarcategory.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(getActivity()));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<JobAppliedListResponse> resultCall = apiInterface.callAppliedJob(map);

        resultCall.enqueue(new Callback<JobAppliedListResponse>() {
            @Override
            public void onResponse(Call<JobAppliedListResponse> call, Response<JobAppliedListResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarcategory.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerAppliedJob.setLayoutManager(linearLayoutManager);
                        AppliedJobListAdapter adapter=new AppliedJobListAdapter(getActivity(),response.body().getData());
                        binding.recyclerAppliedJob.setAdapter(adapter);
                        binding.recyclerAppliedJob.getAdapter().notifyDataSetChanged();



                    } else {
                        binding.progressBarcategory.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<JobAppliedListResponse> call, Throwable t) {

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                binding.progressBarcategory.setVisibility(View.GONE);

                Utils.showFailureDialog(getActivity(), "Please try again sometime later..");
            }
        });
    }
}