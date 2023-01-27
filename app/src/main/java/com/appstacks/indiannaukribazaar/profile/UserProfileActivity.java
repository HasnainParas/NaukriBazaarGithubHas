package com.appstacks.indiannaukribazaar.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.appstacks.indiannaukribazaar.databinding.ActivityUserProfile2Binding;


public class UserProfileActivity extends AppCompatActivity {
ActivityUserProfile2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUserProfile2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEditprofile.setOnClickListener(view -> {
            startActivity(new Intent(UserProfileActivity.this,ProfileEditActivity.class));
        });
    }
}