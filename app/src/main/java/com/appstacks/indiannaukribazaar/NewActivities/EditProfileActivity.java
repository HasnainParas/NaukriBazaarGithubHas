package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appstacks.indiannaukribazaar.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAboutMeEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "About Me");
            startActivity(in);
        });

        binding.btnworkExperienceAdd.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Add Work" );
            startActivity(in);
        });

        binding.btnworkExperienceEdit.setOnClickListener(view -> {
            in=new Intent(EditProfileActivity.this,DetailsActivity.class);
            in.putExtra("profile","Edit Work");
            startActivity(in);
        });
        binding.btnEducationAdd.setOnClickListener(view -> {
            in =new Intent(EditProfileActivity.this,DetailsActivity.class);
            in.putExtra("profile","Add Education");
            startActivity(in);

        });
        binding.btnEducationEdit.setOnClickListener(view -> {
            in =new Intent(EditProfileActivity.this,DetailsActivity.class);
            in.putExtra("profile","Edit Education");
            startActivity(in);
        });
        binding.btnSkillEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in=new Intent(EditProfileActivity.this,DetailsActivity.class);
                in.putExtra("profile","Edit Skills");
                startActivity(in);
            }
        });


    }
}