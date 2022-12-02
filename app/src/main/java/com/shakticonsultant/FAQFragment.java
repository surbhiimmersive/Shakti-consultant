package com.shakticonsultant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.FaqListAdapter;
import com.shakticonsultant.adapter.PackageListAdapter;
import com.shakticonsultant.databinding.FragmentFAQBinding;
import com.shakticonsultant.responsemodel.FaqsResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
ConnectionDetector cd;
    public FAQFragment() {
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
    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
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

    FragmentFAQBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFAQBinding.inflate(getLayoutInflater());

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

            getFaqList();
            binding.imgBackArrow.setOnClickListener(v -> {
                getActivity().onBackPressed();
            });



        }
        return binding.getRoot();
    }


    public void getFaqList() {
        binding.progressBarFaq.setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();
        // map.put("user_id", AppPrefrences.getUserID(NotificationActivity.this));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<FaqsResponse> resultCall = apiInterface.callFaqsList();

        resultCall.enqueue(new Callback<FaqsResponse>() {
            @Override
            public void onResponse(Call<FaqsResponse> call, Response<FaqsResponse> response) {

                if (response.isSuccessful()) {
                    binding.progressBarFaq.setVisibility(View.GONE);

                    //  lemprtNotification.setVisibility(View.GONE);
                    if (response.body().isSuccess()==true) {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerFaqs.setLayoutManager(linearLayoutManager);
                        FaqListAdapter adapter=new FaqListAdapter(getActivity(),response.body().getData());
                        binding.recyclerFaqs.setAdapter(adapter);
                      //  binding.recyclerFaqs.getAdapter().notifyDataSetChanged();

                    } else {

                        binding.progressBarFaq.setVisibility(View.GONE);

                        Utils.showFailureDialog(getActivity(), "No Data Found");
                    }
                }
            }

            @Override
            public void onFailure(Call<FaqsResponse> call, Throwable t) {
                binding.progressBarFaq.setVisibility(View.GONE);

                //  lemprtNotification.setVisibility(View.VISIBLE);
                //    pd_loading.setVisibility(View.GONE);
                Utils.showFailureDialog(getActivity(), "Something went wrong!");
            }
        });
    }
    private void changeFAQView(ConstraintLayout layout, TextView question, TextView answer, ImageView arrow){
        if (arrow.getTag().equals("arrow_down")){
            layout.setBackgroundColor(Color.parseColor("#BB274D"));
            question.setTextColor(Color.parseColor("#FFFFFF"));
            answer.setVisibility(View.VISIBLE);
            answer.setTextColor(Color.parseColor("#FFFFFF"));
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            arrow.setTag("arrow_up");
        }
        else {
            layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            question.setTextColor(Color.parseColor("#000000"));
            answer.setVisibility(View.GONE);
//            answer.setTextColor(Color.parseColor("#FFFFFF"));
            arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_colored);
            arrow.setTag("arrow_down");
        }
    }


}