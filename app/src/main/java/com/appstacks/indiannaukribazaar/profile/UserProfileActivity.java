package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;

import android.widget.Toast;



import com.appstacks.indiannaukribazaar.NewActivities.SettingActivity;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityUserProfile2Binding;
import com.appstacks.indiannaukribazaar.profile.Education.Education;

import com.appstacks.indiannaukribazaar.profile.Lanuages.SelectedLanguages;
import com.appstacks.indiannaukribazaar.profile.appreciation.Appreciation;

import com.appstacks.indiannaukribazaar.profile.resume.Resume;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfile2Binding binding;
    private ProfileUtils profileUtils;
    private static final String TAG = "UserProfileActivity";
    private DatabaseReference userRef;
    private String userId;
    private ArrayList<Feedback> list;
   private FeedbackAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfile2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileUtils = new ProfileUtils(this);

        binding.btnEditprofile.setOnClickListener(view -> {
            startActivity(new Intent(UserProfileActivity.this, ProfileEditActivity.class));
        });

        userRef = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fetchBio();
        fetchWorkExperience();
        fetchEducation();
        fetchSkills();
        fetchLanguage();
        fetchAppreciation();
        fetchResume();
        fetchHourlyCharges();
fetchUserImage();


//     checkForpreferences();


        // dummy data to recycler view
        list= new ArrayList<>();
        list.add(new Feedback("5","2022","Android developer","Mehboob is good android developer","$100","10/Hr","10 Hours"));
        list.add(new Feedback("5","2022","Android developer","Mehboob is good android developer","$100","10/Hr","10 Hours"));

        adapter= new FeedbackAdapter(this,list);
        binding.recyclerFeedBack.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerFeedBack.setAdapter(adapter);

        //

        binding.btnSettingProfile.setOnClickListener(view -> {
            startActivity(new Intent(UserProfileActivity.this, SettingActivity.class));
        });
    }

    private void fetchHourlyCharges() {
        userRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Hourly Charges").exists()) {
                    String data = snapshot.child("Hourly Charges").getValue(String.class);
                    binding.txtHourlyChargesInfo.setText(data + "$ Per Hour");
                    binding.txtHourlyRate.setText("$ "+data + ".00");
                } else {
                    Log.d(TAG, "no hourly charges");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    private void fetchResume() {
        userRef.child(userId).child("Resume").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Resume resume = snapshot.getValue(Resume.class);
                    assert resume != null;
                    binding.txtResumeFileName.setText(resume.getPdfTitle());
                    binding.txtResumeInfor.setText(resume.getSize() + "." + resume.getTime());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void fetchAppreciation() {

        userRef.child(userId).child("Appreciation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Appreciation appreciation = snapshot.getValue(Appreciation.class);

                    assert appreciation != null;
                    binding.txtAppreciation.setText(appreciation.getAwardCategory());
                    binding.txtAppreciationInstitue.setText(appreciation.getAwardName());
                    binding.txtAppreciationDate.setText(appreciation.getAwardEndDate());


                } else {
                    Log.d(TAG, "No appreciation data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    private void fetchEducation() {
        userRef.child(userId).child("Education").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Education education = snapshot.getValue(Education.class);
                    assert education != null;
                    binding.txtEducation.setText(education.getLevelOfEducation());
                    binding.txtEducationInstitue.setText(education.getInstituteName());

                    binding.txtEducationDate.setText(education.getStartDate() + " - " + education.getEndDate());
                    //binding.txtEducationTimePeriod.setText(Integer.parseInt(education.getEndDate()) - Integer.parseInt(education.getStartDate()) + " years");


                } else {
                    Toast.makeText(UserProfileActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSkills() {

        userRef.child(userId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ArrayList<String> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list.add(obj);


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.grid_item, R.id.tvidd, list);

                    binding.listvieww.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void fetchLanguage() {
        userRef.child(userId).child("Languages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    ArrayList<String> list = new ArrayList<>();
                    list.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        SelectedLanguages languages = snap.getValue(SelectedLanguages.class);
                        assert languages != null;
                        list.add(languages.getName());


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfileActivity.this, R.layout.grid_item, R.id.tvidd, list);

                    binding.gridLanguage.setAdapter(adapter);

                } else {
                    Log.d(TAG, "No languages found");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,
                        error.getMessage());
            }
        });

    }
//    private void checkForpreferences() {
//        if (profileUtils.fetchUserBio() != null)
//            binding.txtAboutMeDescription.setText(profileUtils.fetchUserBio());
//
//        else
//            binding.txtAboutMeDescription.setText("User Bio");
//
//        if (profileUtils.fetchWorkExperience() != null) {
//
//            binding.txtWorkExperience.setText(profileUtils.fetchWorkExperience().getJobTitle());
//            binding.txtWorkExperienceCompany.setText(profileUtils.fetchWorkExperience().getCompany());
//            binding.txtWorkExperienceDate.setText(profileUtils.fetchWorkExperience().getStartDate() + " - " + profileUtils.fetchWorkExperience().getEndDate());
//            //binding.txtWorkExperienceTimePeriod.setText(Integer.parseInt(profileUtils.fetchWorkExperience().getEndDate()) -  Integer.parseInt(profileUtils.fetchWorkExperience().getStartDate()));
//        } else {
//            binding.txtWorkExperience.setText("Job title");
//            binding.txtWorkExperienceCompany.setText("Company name");
//            binding.txtWorkExperienceDate.setText("Start date - End date");
//            binding.txtWorkExperienceTimePeriod.setText("Total time");
//        }
//
//
//        if (profileUtils.fetchEducation() != null) {
//
//            binding.txtEducation.setText(profileUtils.fetchEducation().getFieldOfStudy());
//            binding.txtEducationInstitue.setText(profileUtils.fetchEducation().getInstituteName());
//            binding.txtEducationDate.setText(profileUtils.fetchEducation().getStartDate() + " - " + profileUtils.fetchEducation().getEndDate());
//
//        } else {
//            binding.txtEducation.setText("Field of Study");
//            binding.txtEducationInstitue.setText("Institute name");
//            binding.txtEducationDate.setText("Date");
//        }
//
//
//        if (profileUtils.fetchSkills() != null) {
//            ArrayList<String> gridSkillsList = profileUtils.fetchSkills();
//            ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this,R.layout.grid_item,R.id.tvidd, gridSkillsList);
//            binding.listvieww.setAdapter(gridAdapter);
//        }else{
//            Log.d(TAG,"No Skills found");
//        }
//
//        if (profileUtils.fetchLanguages() != null){
//            ArrayList<String> languages = profileUtils.fetchSkills();
//            ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this,R.layout.grid_item,R.id.tvidd, languages);
//           // binding.gridLanguage.setAdapter(gridAdapter);
//        }else{
//            Log.d(TAG,"No languages found");
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(getApplicationContext()).load(profileUtils.fetchUserImage()).placeholder(R.drawable.userimg).into(binding.circleImageView);
//        checkForpreferences();
    }

    private void fetchWorkExperience() {
        userRef.child(userId).child("WorkExperience").addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    AddWorkExperience workExperience = snapshot.getValue(AddWorkExperience.class);
                    assert workExperience != null;
                    binding.txtWorkExperience.setText(workExperience.getJobTitle());

                    binding.txtWorkExperienceCompany.setText(workExperience.getCompany());
                    binding.txtWorkExperienceDate.setText(workExperience.getStartDate() + " - " + workExperience.getEndDate());


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
                Log.d(TAG, error.getMessage());
            }
        });

    }
    private void fetchUserImage() {

        userRef.child(userId).child("UserImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(url).into(binding.circleImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}