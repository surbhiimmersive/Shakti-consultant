package com.shakticonsultant;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.FaqListAdapter;
import com.shakticonsultant.adapter.ScheduleInterviewAdapter;
import com.shakticonsultant.databinding.ActivityScheduleInterviewBinding;
import com.shakticonsultant.databinding.FragmentFAQBinding;
import com.shakticonsultant.databinding.FragmentScheduleInterviewBinding;
import com.shakticonsultant.responsemodel.FaqsResponse;
import com.shakticonsultant.responsemodel.ScheduleInterviewResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleInterviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleInterviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentScheduleInterviewBinding binding;
    DatePickerDialog datePickerDialog;
    ConnectionDetector cd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ScheduleInterviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleInterviewFragment newInstance(String param1, String param2) {
        ScheduleInterviewFragment fragment = new ScheduleInterviewFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentScheduleInterviewBinding.inflate(getLayoutInflater());

        /*binding.layoutFaq1.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq1, binding.faq1Question, binding.faq1Answer, binding.faq1Arrow);
        });

        binding.layoutFaq2.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq2, binding.faq2Question, binding.faq2Answer, binding.faq2Arrow);
        });

        binding.layoutFaq3.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq3, binding.faq3Question, binding.faq3Answer, binding.faq3Arrow);
        });
*/

        cd=new ConnectionDetector(getActivity());


        if (!cd.isConnectingToInternet()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {

            getScheduleInterviewList("1");

            binding.imgBackArrow.setOnClickListener(v -> {
                getActivity().onBackPressed();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
            binding.btnToday.setOnClickListener(v -> {
                setButtonSelected(binding.btnToday, binding.btnUpcoming);
                getScheduleInterviewList("1");

            });
            binding.btnUpcoming.setOnClickListener(v -> {
                setButtonSelected(binding.btnUpcoming, binding.btnToday);
                getScheduleInterviewList("1");


            });
        }
        return binding.getRoot();
    }


    private void setButtonSelected(AppCompatButton buttonToSelect, AppCompatButton buttonToDeselect){
        buttonToSelect.setBackgroundResource(R.drawable.custom_button_bg);
        buttonToSelect.setTextColor(Color.parseColor("#FFFFFF"));

        buttonToDeselect.setBackgroundResource(R.drawable.cutom_button_unselected_bg);
        buttonToDeselect.setTextColor(Color.parseColor("#000000"));
    }



    public void getScheduleInterviewList(String type) {
        binding.progressBarpackage.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", AppPrefrences.getUserid(getActivity()));
        map.put("type", type);


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ScheduleInterviewResponse> resultCall = apiInterface.callInterviewSchedule(map);

        resultCall.enqueue(new Callback<ScheduleInterviewResponse>() {
            @Override
            public void onResponse(Call<ScheduleInterviewResponse> call, Response<ScheduleInterviewResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarpackage.setVisibility(View.GONE);
                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {
                        binding.tvEmpty.setVisibility(View.GONE);
                        binding.imgEmpty.setVisibility(View.GONE);
                        binding.recyclerpackage.setVisibility(View.VISIBLE);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerpackage.setLayoutManager(linearLayoutManager);
                        ScheduleInterviewAdapter adapter=new ScheduleInterviewAdapter(getActivity(),response.body().getData());
                        binding.recyclerpackage.setAdapter(adapter);
                        binding.recyclerpackage.getAdapter().notifyDataSetChanged();

                    } else {
                        binding.progressBarpackage.setVisibility(View.GONE);
                        binding.tvEmpty.setVisibility(View.VISIBLE);
                        binding.imgEmpty.setVisibility(View.VISIBLE);
                        binding.recyclerpackage.setVisibility(View.GONE);

                        //lemprtNotification.setVisibility(View.VISIBLE);
                        // Utils.showFailureDialog(NotificationActivity.this, "No Data Found");
                    }
                }else{
                    binding.progressBarpackage.setVisibility(View.GONE);
                    binding.tvEmpty.setVisibility(View.VISIBLE);
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.recyclerpackage.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ScheduleInterviewResponse> call, Throwable t) {
                binding.progressBarpackage.setVisibility(View.GONE);
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.imgEmpty.setVisibility(View.VISIBLE);
                binding.recyclerpackage.setVisibility(View.GONE);
                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }


}