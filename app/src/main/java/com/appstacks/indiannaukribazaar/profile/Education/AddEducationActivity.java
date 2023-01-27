package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddEducationBinding;

public class AddEducationActivity extends AppCompatActivity {
ActivityAddEducationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnBackAddEduca.setOnClickListener(view -> {
            finish();
        });


        binding.editlevelofEduca.setOnClickListener(view -> {
          startActivity(new Intent(AddEducationActivity.this,LevelOfEducationActivity.class));
        });


    }
}