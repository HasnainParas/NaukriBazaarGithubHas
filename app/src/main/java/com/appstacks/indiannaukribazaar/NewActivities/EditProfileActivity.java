package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.ProfileModels.Education;
import com.appstacks.indiannaukribazaar.databinding.ActivityEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    Intent in;
    private StorageReference storageReference;
    DatabaseReference userRef;
    String userId;
    AddWorkExperience workExperience = new AddWorkExperience();
    AboutMeDescription aboutme = new AboutMeDescription();
    Education education;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");

        fetchAboutMe();
        fetchWorkExperience();
        fetchEducation();


        binding.btnAboutMeEdit.setOnClickListener(view -> {
            in = new Intent(EditProfileActivity.this, DetailsActivity.class);
            in.putExtra("profile", "About Me");
            in.putExtra("aboutMeDesc", aboutme.getAboutMeDescription());
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
            in.putExtra("jobTitle", workExperience.getJobTitle());
            in.putExtra("company", workExperience.getCompany());
            in.putExtra("startDate", workExperience.getStartDate());
            in.putExtra("endDate", workExperience.getEndDate());
            in.putExtra("positionNow", workExperience.isPositionNow());
            in.putExtra("jobDesc", workExperience.getDescription());
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
            in.putExtra("levelEducation",education.getLevelOfEducation());
            in.putExtra("institute",education.getInstituteName());
            in.putExtra("educationStartDate",education.getStartDate());
            in.putExtra("educationEndDate",education.getEndDate());
            in.putExtra("isPositionEducation",education.isPosition());
            in.putExtra("fieldOfStudy",education.getFieldOfStudy());
            in.putExtra("educationDesc",education.getDescription());
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

    private void fetchEducation() {
        userRef.child(userId).child("Education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    binding.btnEducationAdd.setVisibility(View.GONE);
                    binding.view3.setVisibility(View.VISIBLE);
                    binding.btnEducationEdit.setVisibility(View.VISIBLE);
                    binding.txtEducation.setVisibility(View.VISIBLE);
                    binding.txtEducationInstitue.setVisibility(View.VISIBLE);
                    binding.txtEducationDate.setVisibility(View.VISIBLE);
                    binding.txtEducationTimePeriod.setVisibility(View.VISIBLE);

                   education=  snapshot.getValue(Education.class);

                    assert education != null;
                    binding.txtEducation.setText(education.getFieldOfStudy());
                    binding.txtEducationInstitue.setText(education.getInstituteName());
                    binding.txtEducationDate.setText(education.getStartDate() + " - " + education.getEndDate());
                    if (!education.getEndDate().isEmpty()){
                        int years = Integer.parseInt(education.getEndDate()) - Integer.parseInt(education.getStartDate());
                        binding.txtEducationTimePeriod.setText(String.valueOf(years));
                    }else{
                        binding.txtEducationTimePeriod.setText(education.isPosition() ? "Currently Studying": " ");
                    }
                }else{
                    binding.btnEducationAdd.setVisibility(View.VISIBLE);
                    binding.view3.setVisibility(View.GONE);
                    binding.btnEducationEdit.setVisibility(View.GONE);
                    binding.txtEducation.setVisibility(View.GONE);
                    binding.txtEducationInstitue.setVisibility(View.GONE);
                    binding.txtEducationDate.setVisibility(View.GONE);
                    binding.txtEducationTimePeriod.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWorkExperience() {
        userRef.child(userId).child("WorkExperience").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    binding.btnworkExperienceEdit.setVisibility(View.VISIBLE);

                    binding.btnworkExperienceAdd.setVisibility(View.GONE);
                    binding.txtWorkExperience.setVisibility(View.VISIBLE);

                    binding.view2.setVisibility(View.VISIBLE);
                    binding.btnworkExperienceEdit.setVisibility(View.VISIBLE);
                    binding.txtWorkExperienceCompany.setVisibility(View.VISIBLE);
                    binding.txtWorkExperienceDate.setVisibility(View.VISIBLE);
                    binding.txtWorkExperienceTimePeriod.setVisibility(View.VISIBLE);

                    workExperience = snapshot.getValue(AddWorkExperience.class);
                    binding.txtWorkExperience.setText(workExperience.getJobTitle());

                    assert workExperience != null;
                    binding.txtWorkExperienceCompany.setText(workExperience.getCompany());
                    binding.txtWorkExperienceDate.setText(workExperience.getStartDate() + " - " + workExperience.getEndDate() + ",");
                    if (!workExperience.getEndDate().isEmpty()) {
                        int year = Integer.parseInt(workExperience.getEndDate()) - Integer.parseInt(workExperience.getStartDate());
                        binding.txtWorkExperienceTimePeriod.setText(String.valueOf(year + " years"));
                    } else {
                        binding.txtWorkExperienceTimePeriod.setText(workExperience.isPositionNow() ? "Currently works here " : "");
                    }

                } else {
                    binding.btnworkExperienceAdd.setVisibility(View.VISIBLE);
                    binding.txtWorkExperience.setVisibility(View.GONE);
                    binding.view2.setVisibility(View.GONE);
                    binding.btnworkExperienceEdit.setVisibility(View.GONE);
                    binding.txtWorkExperienceCompany.setVisibility(View.GONE);
                    binding.txtWorkExperienceDate.setVisibility(View.GONE);
                    binding.txtWorkExperienceTimePeriod.setVisibility(View.GONE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAboutMe() {
        userRef.child(userId).child("AboutMe").addValueEventListener(new ValueEventListener() {
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

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}