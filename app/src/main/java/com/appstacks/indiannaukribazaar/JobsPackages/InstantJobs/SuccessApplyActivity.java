package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.databinding.ActivitySuccessApplyBinding;

public class SuccessApplyActivity extends AppCompatActivity {
    ActivitySuccessApplyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessApplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.newPostcardview.setOnClickListener(v -> {
            startActivity(new Intent(SuccessApplyActivity.this, InstantJobPostActivity.class));
            finish();
        });

        binding.cardViewHomeId.setOnClickListener(v -> {
            startActivity(new Intent(SuccessApplyActivity.this, FindJobsActivity.class));
            finish();
        });
    }


}