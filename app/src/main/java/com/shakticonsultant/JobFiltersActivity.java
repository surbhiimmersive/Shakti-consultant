package com.shakticonsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.shakticonsultant.databinding.ActivityJobFiltersBinding;

public class JobFiltersActivity extends AppCompatActivity implements View.OnClickListener {

   ActivityJobFiltersBinding binding;
   private MaterialCardView previousCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobFiltersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        previousCard = binding.cardviewSalary;

        binding.cardviewSalary.setOnClickListener(this);
        binding.cardviewCities.setOnClickListener(this);
        binding.cardviewExperience.setOnClickListener(this);

        binding.imgSearch.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SearchJobActivity.class));
        });

        binding.imgBackArrow.setOnClickListener(v -> {
            onBackPressed();
        });


    }

    private void setCardViewSelected(MaterialCardView target){
        target.setStrokeColor(Color.parseColor("#FFFFFF"));
        target.setStrokeWidth(4);
    }

    private void setCardViewUnSelected(MaterialCardView target){
        target.setStrokeColor(Color.parseColor("#DADADA"));
        target.setStrokeWidth(1);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.cardviewSalary.getId()){
            setCardViewSelected(binding.cardviewSalary);

            binding.headerTitle.setText("Salary");

            binding.salaryView.setVisibility(View.VISIBLE);
            binding.citiesView.setVisibility(View.GONE);
            binding.experienceView.setVisibility(View.GONE);

            binding.imgSearch.setVisibility(View.VISIBLE);

            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewSalary;
        }

        if(v.getId() == binding.cardviewCities.getId()){
            setCardViewSelected(binding.cardviewCities);

            binding.headerTitle.setText("Cities");

            binding.salaryView.setVisibility(View.GONE);
            binding.citiesView.setVisibility(View.VISIBLE);
            binding.experienceView.setVisibility(View.GONE);

            binding.imgSearch.setVisibility(View.GONE);


            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewCities;

        }

        if(v.getId() == binding.cardviewExperience.getId()){
            setCardViewSelected(binding.cardviewExperience);

            binding.headerTitle.setText("Experience");

            binding.salaryView.setVisibility(View.GONE);
            binding.citiesView.setVisibility(View.GONE);
            binding.experienceView.setVisibility(View.VISIBLE);

            binding.imgSearch.setVisibility(View.VISIBLE);

            if (v.getId() != previousCard.getId()){
                setCardViewUnSelected(previousCard);
            }

            previousCard = binding.cardviewExperience;

        }

    }
}