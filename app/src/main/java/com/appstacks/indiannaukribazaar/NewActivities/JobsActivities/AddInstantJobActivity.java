package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddInstantJobBinding;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantJobBinding;

public class AddInstantJobActivity extends AppCompatActivity {

    ActivityAddInstantJobBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddInstantJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





    }

}