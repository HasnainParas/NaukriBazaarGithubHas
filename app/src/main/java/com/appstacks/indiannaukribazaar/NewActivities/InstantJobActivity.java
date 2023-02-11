package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.AddInstantJobActivity;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantJobBinding;
public class InstantJobActivity extends AppCompatActivity {

    ActivityInstantJobBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstantJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.postBtnInstant.setOnClickListener(view -> {
            startActivity(new Intent(InstantJobActivity.this, AddInstantJobActivity.class));
        });


    }
}