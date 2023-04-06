package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantJobBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.bumptech.glide.Glide;

public class InstantJobPostActivity extends AppCompatActivity {

    ActivityInstantJobBinding binding;
    private String userId;
    private String usernamein, useraddressin,userImageIn;
    private SharedPrefe sharedPrefe;
    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstantJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefe = new SharedPrefe(InstantJobPostActivity.this);
        profileUtils = new ProfileUtils(this);


        usernamein = getIntent().getStringExtra("usernameinstant");
        useraddressin = getIntent().getStringExtra("useraddressinstant");
        userImageIn = getIntent().getStringExtra("userImageIn");

        binding.userNameInstant.setText(usernamein);
        binding.locationInstant.setText(useraddressin);

        Glide.with(this)
                .load(userImageIn)
                .placeholder(R.drawable.profileplace).into(binding.userDPinstant);

        binding.backBtnisntant.setOnClickListener(v -> finish());


        binding.postBtnInstant.setOnClickListener(view -> {


            if (binding.editBoxTitleInstant.getText().toString().isEmpty()) {
                binding.editBoxTitleInstant.setError("Field Can't be empty");
            } else if (binding.editBoxDiscriptionInstant.getText().toString().isEmpty()) {
                binding.editBoxDiscriptionInstant.setError("Field Can't be empty");
            } else {
                Intent intent = new Intent(getApplicationContext(), AddingInstantJobDetailsActivity.class);
                String titleTxt = binding.editBoxTitleInstant.getText().toString();
                String descriptionTxt = binding.editBoxDiscriptionInstant.getText().toString();
                sharedPrefe.saveInstantJobTitle(titleTxt);
                sharedPrefe.saveInstantDescription(descriptionTxt);
                startActivity(intent);
                finish();
            }


        });


    }
}