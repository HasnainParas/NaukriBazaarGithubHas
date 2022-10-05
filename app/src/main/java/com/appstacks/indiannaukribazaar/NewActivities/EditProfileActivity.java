package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.databinding.ActivityEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    DatabaseReference userProfile;
    FirebaseAuth auth;
    Intent in;
    String userID;
    AboutMeDescription aboutme = new AboutMeDescription();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        userProfile = FirebaseDatabase.getInstance().getReference("UsersProfile");

        userID = auth.getCurrentUser().getUid();


        userProfile.child(userID).child("AboutMe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("aboutMeDescription").exists()) {
                    binding.txtAboutMeDescription.setVisibility(View.VISIBLE);
                    binding.view1.setVisibility(View.VISIBLE);
                    aboutme = snapshot.getValue(AboutMeDescription.class);
                    assert aboutme != null;
                    binding.txtAboutMeDescription.setText(aboutme.getAboutMeDescription());
                } else {
                    binding.txtAboutMeDescription.setVisibility(View.GONE);
                    binding.view1.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnAboutMeEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "About Me");
            startActivity(in);
        });

        binding.btnworkExperienceAdd.setOnClickListener(view -> {

            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Add Work");
            startActivity(in);
        });

        binding.btnworkExperienceEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Edit Work");
            startActivity(in);
        });
        binding.btnEducationAdd.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Add Education");
            startActivity(in);

        });
        binding.btnEducationEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Edit Education");
            startActivity(in);
        });
        binding.btnSkillEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(EditProfileActivity.this, DetailsActivity.class);
                in.putExtra("profile", "Edit Skills");
                startActivity(in);
            }
        });
        binding.btnLanguageEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "Edit language");
            startActivity(in);
        });
        binding.btnAppreciationAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(EditProfileActivity.this, DetailsActivity.class);
                in.putExtra("profile", "Add Appre");
                startActivity(in);
            }

        });
        binding.btnAppreciationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(EditProfileActivity.this, DetailsActivity.class);
                in.putExtra("profile", "Edit Appre");
                startActivity(in);
            }
        });

        binding.btnResumeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(EditProfileActivity.this, DetailsActivity.class);
                in.putExtra("profile", "Add cv");
                startActivity(in);
            }
        });

    }
}