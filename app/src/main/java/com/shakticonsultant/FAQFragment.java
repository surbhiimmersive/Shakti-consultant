package com.shakticonsultant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shakticonsultant.databinding.FragmentFAQBinding;

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

        binding.layoutFaq1.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq1, binding.faq1Question, binding.faq1Answer, binding.faq1Arrow);
        });

        binding.layoutFaq2.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq2, binding.faq2Question, binding.faq2Answer, binding.faq2Arrow);
        });

        binding.layoutFaq3.setOnClickListener(v -> {
            changeFAQView(binding.layoutFaq3, binding.faq3Question, binding.faq3Answer, binding.faq3Arrow);
        });

        binding.imgBackArrow.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        return binding.getRoot();
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