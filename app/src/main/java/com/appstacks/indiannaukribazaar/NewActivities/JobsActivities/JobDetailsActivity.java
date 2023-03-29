package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityJobDetailsBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

public class JobDetailsActivity extends AppCompatActivity {
    ActivityJobDetailsBinding binding;

    String jobImg;
    String jobTitle, companyName, jobType,uniqueKey, jobTime, jobPostDate,jobPostedTime;
    String applyBefore, jobPriceINR, jobDescription, rolesAndRESPONSIBILITIES,salaryRange,jobLocation;
    String key;
    DatabaseReference timeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uniqueKey = getIntent().getStringExtra("uniqueKey");
        jobTitle = getIntent().getStringExtra("title");
        companyName = getIntent().getStringExtra("companyname");
        jobType = getIntent().getStringExtra("type");
        jobTime = getIntent().getStringExtra("time");

        salaryRange = getIntent().getStringExtra("salary");
        applyBefore = getIntent().getStringExtra("applybefore");
        jobPriceINR = getIntent().getStringExtra("price");
        jobDescription = getIntent().getStringExtra("jobdes");
        rolesAndRESPONSIBILITIES = getIntent().getStringExtra("getRules");
        jobImg = getIntent().getStringExtra("imageurl");
        jobLocation = getIntent().getStringExtra("location");
        jobPostedTime = getIntent().getStringExtra("jobPostedTime");
        jobPostDate = getIntent().getStringExtra("jobPostDate");


        binding.jobtitle.setText(jobTitle);
        binding.companyname.setText(companyName);
        binding.locationJob.setText(jobLocation);
        binding.descriptionjob.setText(jobDescription);
        binding.salaryRange.setText(salaryRange);
        binding.applybefore.setText(applyBefore);
        binding.jobPriceINR.setText(jobPriceINR);
        binding.rolesAndResp.setText(rolesAndRESPONSIBILITIES);
        binding.jobPostedDate.setText(jobPostDate);




        try {
            Glide.with(JobDetailsActivity.this).load(jobImg)
                    .placeholder(R.drawable.avatar_placeholder)
                    .into(binding.icJob);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}