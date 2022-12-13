package com.shakticonsultant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.shakticonsultant.adapter.FaqListAdapter;
import com.shakticonsultant.databinding.FragmentFAQBinding;
import com.shakticonsultant.databinding.FragmentProfileBinding;
import com.shakticonsultant.responsemodel.FaqsResponse;
import com.shakticonsultant.responsemodel.ProfileResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.shakticonsultant.utils.ConnectionDetector;
import com.shakticonsultant.utils.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    String name,email,mobile,img;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
ConnectionDetector cd;
    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());


        cd=new ConnectionDetector(getActivity());


        if (!cd.isConnectingToInternet()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Internet Connection not available..", Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.RED)
                    .show();
        }else {

getprofiledata();
            //  getFaqList();
            binding.imgBackArrow.setOnClickListener(v -> {
                getActivity().onBackPressed();
                getActivity().overridePendingTransition(R.anim.slide_in_left,
                        R.anim.slide_out_right);
            });
binding.btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent( getActivity(),GetPersonalInfoActivity.class));

        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

    }
});

binding.imgEdit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       Intent i=new Intent(getActivity(),EditProfileActivity.class);
       i.putExtra("name",name);
       i.putExtra("email",email);
       i.putExtra("mobile",mobile);
       i.putExtra("profile_img",img);
       startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

    }
});
binding.btnAcademicDetail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent( getActivity(),GetAcademicDetailsActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }
});
binding.btnEmployeeHistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent( getActivity(),GetEmployeeHistoryActivity.class));
        getActivity().overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

    }
});


        }
        return binding.getRoot();
    }



    public void getprofiledata () {
        // binding..setVisibility(View.VISIBLE);
        Map<String, String> map = new HashMap<>();

        map.put("user_id",AppPrefrences.getUserid(getActivity()));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileResponse> resultCall = apiInterface.callgetProfileApi(map);

        resultCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressAbout.setVisibility(View.GONE);
                    binding.consprofile.setVisibility(View.VISIBLE);
                    if (response.body().isSuccess() == true) {
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());

                        /*binding.textView33.setText(AppPrefrences.getName(getActivity()));
                        binding.textView34.setText(AppPrefrences.getMail(getActivity()));
                        binding.textView35.setText(AppPrefrences.getMobile(getActivity()));
                        Picasso.get()
                                .load(ApiClient.Photourl+ AppPrefrences.getProfileImg(getActivity()))
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView14);

*/

                        name=response.body().getData().getName();
                        email=response.body().getData().getEmail();
                        mobile=response.body().getData().getMobile();
                        img=response.body().getData().getProfile_image();
                        binding.textView33.setText(response.body().getData().getName());
                        binding.textView34.setText(response.body().getData().getEmail());
                        binding.textView35.setText(response.body().getData().getMobile());
                        Picasso.get()
                                .load(ApiClient.Photourl+ response.body().getData().getProfile_image())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(binding.imageView14);
                    } else {
                        // Utils.showFailureDialog(AboutUsActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // binding.progressAbout.setVisibility(View.GONE);
                // Utils.showFailureDialog(AboutUsActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getprofiledata();
    }
}