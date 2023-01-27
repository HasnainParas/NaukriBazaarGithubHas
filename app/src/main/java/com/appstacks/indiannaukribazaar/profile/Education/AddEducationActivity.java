package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddEducationBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;

public class AddEducationActivity extends AppCompatActivity {
    private ActivityAddEducationBinding binding;
    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);


        binding.edittextInstituteName.setOnClickListener(view -> {

            startActivity(new Intent(AddEducationActivity.this, InstituteNameActivity.class));

        });
        binding.btnBackAddEduca.setOnClickListener(view -> {
            finish();
        });


        binding.etFieldofStudy.setOnClickListener(view -> {
            startActivity(new Intent(AddEducationActivity.this, FieldOfStudyActivity.class));
        });

        binding.editlevelofEduca.setOnClickListener(view -> {
            startActivity(new Intent(AddEducationActivity.this,LevelOfEducationActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.editlevelofEduca.setText(profileUtils.fetchLevelOfEducation());
        binding.edittextInstituteName.setText(profileUtils.fetchInstituteName());
        binding.etFieldofStudy.setText(profileUtils.fetchFieldStudy());

    }
}