package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.databinding.ActivityNjobsDetailBinding;

public class N_JobsDetailActivity extends AppCompatActivity {
    ActivityNjobsDetailBinding binding;

    String jobtitle, companytxt, location, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNjobsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        jobtitle = getIntent().getStringExtra("jobtile");
        companytxt = getIntent().getStringExtra("company");
        location = getIntent().getStringExtra("location");
        description = getIntent().getStringExtra("description");

        binding.jobTitle.setText(jobtitle);
        binding.comText.setText(companytxt);
        binding.locationText.setText(location);
        binding.descriptiontext.setText(description);


    }
}