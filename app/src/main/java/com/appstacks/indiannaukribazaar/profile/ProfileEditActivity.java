package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.profile.Education.Education;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityProfileEditBinding;
import com.appstacks.indiannaukribazaar.profile.Education.AddEducationActivity;
import com.appstacks.indiannaukribazaar.profile.Lanuages.AddlanguagesActivity;
import com.appstacks.indiannaukribazaar.profile.appreciation.AddAppreciationActivity;
import com.appstacks.indiannaukribazaar.profile.appreciation.EditAppreciationActivity;
import com.appstacks.indiannaukribazaar.profile.hourlycharges.AddHourlyChargesActivity;
import com.appstacks.indiannaukribazaar.profile.resume.AddResmueActivity;
import com.appstacks.indiannaukribazaar.profile.skills.SkillsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;
    private ProfileUtils profileUtils;
    private DatabaseReference userRef;
    private String userId;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);
        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fetchWorkExperience();
         readFromDatabase();
         fetchSkills();
        fetchBio();
        binding.btnBioEdit.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, EditBioActivity.class));
        });

        binding.btnWorkExperienceAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, WorkExperienceActivity.class);
            intent.putExtra("et", "2");
            startActivity(intent);
        });

        binding.btnworkExperienceEdit.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, WorkExperienceActivity.class);
            intent.putExtra("et", "1");
            startActivity(intent);
        });

        binding.btnEducationAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddEducationActivity.class);
            intent.putExtra("edu", "2");
            startActivity(intent);
        });

        binding.btnEducationEdit.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddEducationActivity.class);
            intent.putExtra("edu", "1");
            startActivity(intent);
        });

        binding.btnSkillEdit.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, SkillsActivity.class));
        });

        binding.btnLanguageEdit.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, AddlanguagesActivity.class));
        });

        binding.btnAppreciationAdd.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, AddAppreciationActivity.class));
        });
        binding.btnAppreciationEdit.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, EditAppreciationActivity.class));
        });

        binding.btnResumeAdd.setOnClickListener(view -> {
            startActivity(new Intent(ProfileEditActivity.this, AddResmueActivity.class));
        });

        binding.btnHourlyChargesAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddHourlyChargesActivity.class);
            intent.putExtra("hourly","add");
            startActivity(intent);
        });
        binding.btnHourlyChargsEdit.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddHourlyChargesActivity.class);
            intent.putExtra("hourly","edit");
            startActivity(intent);
        });
    }


    private void fetchSkills() {

        userRef.child(userId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list.add(obj);


                    }

                    adapter = new ArrayAdapter<String>(ProfileEditActivity.this,R.layout.grid_item,R.id.tvidd,list);

                    binding.gridViewOuter.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void fetchBio() {

        userRef.child(userId).child("AboutMe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("aboutMeDescription").exists()) {

                    AboutMeDescription aboutme = snapshot.getValue(AboutMeDescription.class);
                    assert aboutme != null;

                    binding.txtAboutMeDescription.setText(aboutme.getAboutMeDescription());
                } else {


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileEditActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void fetchWorkExperience() {
        userRef.child(userId).child("WorkExperience").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    AddWorkExperience workExperience = snapshot.getValue(AddWorkExperience.class);
                    binding.txtWorkExperience.setText(workExperience.getJobTitle());

                    assert workExperience != null;
                    binding.txtWorkExperienceCompany.setText(workExperience.getCompany());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readFromDatabase() {
        userRef.child(userId).child("Education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Education education = snapshot.getValue(Education.class);
                    binding.txtEducation.setText(education.getLevelOfEducation());
                    binding.txtEducationInstitue.setText(education.getInstituteName());

                    binding.txtEducationDate.setText(education.getStartDate() + " - " + education.getEndDate());
                    binding.txtEducationTimePeriod.setText(Integer.parseInt(education.getEndDate()) - Integer.parseInt(education.getStartDate()) + " years");


                } else {
                    Toast.makeText(ProfileEditActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileEditActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}