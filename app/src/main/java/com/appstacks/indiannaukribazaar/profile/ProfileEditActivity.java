package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.EditProfileActivity;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.profile.Education.Education;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityProfileEditBinding;
import com.appstacks.indiannaukribazaar.profile.Education.AddEducationActivity;
import com.appstacks.indiannaukribazaar.profile.Lanuages.AddlanguagesActivity;
import com.appstacks.indiannaukribazaar.profile.Lanuages.SelectedLanguages;
import com.appstacks.indiannaukribazaar.profile.appreciation.AddAppreciationActivity;
import com.appstacks.indiannaukribazaar.profile.appreciation.Appreciation;
import com.appstacks.indiannaukribazaar.profile.appreciation.EditAppreciationActivity;
import com.appstacks.indiannaukribazaar.profile.hourlycharges.AddHourlyChargesActivity;
import com.appstacks.indiannaukribazaar.profile.resume.AddResmueActivity;
import com.appstacks.indiannaukribazaar.profile.resume.Resume;
import com.appstacks.indiannaukribazaar.profile.skills.SkillsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileEditActivity extends AppCompatActivity {
    private ActivityProfileEditBinding binding;
    private ProfileUtils profileUtils;
    private DatabaseReference userRef;
    private String userId;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ArrayList<Feedback> listFeedback;
    private FeedbackAdapter adapterFeedback;
    private StorageReference storageReference;
    private Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);
        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        resume = new Resume();
        storageReference = FirebaseStorage.getInstance().getReference("UsersProfile/");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fetchWorkExperience();
        readFromDatabase();
        fetchSkills();
        fetchBio();
        fetchLanguage();
        fetchAppreciation();
        fetchResume();
        fetchHourlyCharges();


        //


        listFeedback = new ArrayList<>();
        listFeedback.add(new Feedback("5", "2022", "Android developer", "Mehboob is good android developer", "$100", "10/Hr", "10 Hours"));
        listFeedback.add(new Feedback("5", "2022", "Android developer", "Mehboob is good android developer", "$100", "10/Hr", "10 Hours"));

        adapterFeedback = new FeedbackAdapter(this, listFeedback);
        binding.recyclerFeedBack.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerFeedBack.setAdapter(adapterFeedback);
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
            intent.putExtra("hourly", "add");
            startActivity(intent);
        });
        binding.btnHourlyChargsEdit.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileEditActivity.this, AddHourlyChargesActivity.class);
            intent.putExtra("hourly", "edit");
            startActivity(intent);
        });

        binding.btnResumeDelete.setOnClickListener(view -> {


            deleteResume();


        });
    }

    private void deleteResume() {
        userRef.child(userId).child("Resume").removeValue().addOnSuccessListener(unused -> storageReference.child("Resumes/").child(userId+"/").delete().addOnSuccessListener(unused1 -> {
            binding.btnResumeDelete.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileEditActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(ProfileEditActivity.this, e + "", Toast.LENGTH_SHORT).show())).addOnFailureListener(e -> Toast.makeText(ProfileEditActivity.this, e + "", Toast.LENGTH_SHORT).show());
    }

    private void fetchHourlyCharges() {
        userRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Hourly Charges").exists()) {
                    binding.btnHourlyChargesAdd.setVisibility(View.INVISIBLE);
                    String data = snapshot.child("Hourly Charges").getValue(String.class);
                    binding.txtHourlyChargesInfo.setText(data + "$ Per Hour");
                    binding.txtHourlyRate.setText("$ " + data + ".00");
                } else {
                    Log.d("TAG", "no hourly charges");
                    binding.btnHourlyChargesAdd.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
            }
        });
    }

    private void fetchResume() {
        userRef.child(userId).child("Resume").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.btnResumeDelete.setVisibility(View.VISIBLE);
                    binding.btnResumeAdd.setVisibility(View.INVISIBLE);
                    Resume resume = snapshot.getValue(Resume.class);
                    assert resume != null;
                    binding.txtResumeFileName.setText(resume.getPdfTitle());
                    binding.txtResumeInfor.setText(resume.getSize() + "." + resume.getTime());
                } else {
                    binding.btnResumeAdd.setVisibility(View.VISIBLE);
                    binding.btnResumeDelete.setVisibility(View.INVISIBLE);
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
                    binding.btnAppreciationAdd.setVisibility(View.INVISIBLE);
                    Appreciation appreciation = snapshot.getValue(Appreciation.class);

                    assert appreciation != null;
                    binding.txtAppreciation.setText(appreciation.getAwardCategory());
                    binding.txtAppreciationInstitue.setText(appreciation.getAwardName());
                    binding.txtAppreciationDate.setText(appreciation.getAwardEndDate());


                } else {
                    binding.btnAppreciationAdd.setVisibility(View.VISIBLE);
                    Log.d("TAG", "No appreciation data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage());
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this, R.layout.grid_item, R.id.tvidd, list);

                    binding.gridLanguage.setAdapter(adapter);

                } else {
                    Log.d("TAG", "No languages found");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG",
                        error.getMessage());
            }
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

                    adapter = new ArrayAdapter<String>(ProfileEditActivity.this, R.layout.grid_item, R.id.tvidd, list);

                    binding.gridViewOuter.setAdapter(adapter);

                } else {

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
                    binding.btnWorkExperienceAdd.setVisibility(View.INVISIBLE);
                    AddWorkExperience workExperience = snapshot.getValue(AddWorkExperience.class);

                    assert workExperience != null;
                    binding.txtWorkExperience.setText(workExperience.getJobTitle());

                    assert workExperience != null;
                    binding.txtWorkExperienceCompany.setText(workExperience.getCompany());

                } else {
                    binding.btnWorkExperienceAdd.setVisibility(View.VISIBLE);
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
                    binding.btnEducationAdd.setVisibility(View.INVISIBLE);
                    Education education = snapshot.getValue(Education.class);
                    assert education != null;
                    binding.txtEducation.setText(education.getLevelOfEducation());
                    binding.txtEducationInstitue.setText(education.getInstituteName());

                    binding.txtEducationDate.setText(education.getStartDate() + " - " + education.getEndDate());
                    binding.txtEducationTimePeriod.setText(Integer.parseInt(education.getEndDate()) - Integer.parseInt(education.getStartDate()) + " years");


                } else {
                    binding.btnEducationAdd.setVisibility(View.VISIBLE);
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