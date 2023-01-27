package com.appstacks.indiannaukribazaar.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityProfileEditBinding;
import com.appstacks.indiannaukribazaar.profile.Education.AddEducationActivity;

public class ProfileEditActivity extends AppCompatActivity {
    ActivityProfileEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnBioEdit.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, EditBioActivity.class));
        });

        binding.btnWorkExperienceAdd.setOnClickListener(view -> {
            Intent intent  = new Intent(ProfileEditActivity.this,WorkExperienceActivity.class);
            intent.putExtra("et","2");
            startActivity(intent);
        });

        binding.btnworkExperienceEdit.setOnClickListener(view -> {
           Intent intent  = new Intent(ProfileEditActivity.this,WorkExperienceActivity.class);
           intent.putExtra("et","1");
           startActivity(intent);
        });

        binding.btnEducationAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddEducationActivity.class);
            startActivity(intent);
        });
    }
}