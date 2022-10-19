package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.AaForChecking;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.GridAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.LanguageGridAdapter;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.ProfileModels.Appreciation;
import com.appstacks.indiannaukribazaar.ProfileModels.Education;
import com.appstacks.indiannaukribazaar.ProfileModels.Resume;
import com.appstacks.indiannaukribazaar.ProfileModels.SelectedLanguages;
import com.appstacks.indiannaukribazaar.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    Intent in;
    private StorageReference storageReference, storagedlt;
    DatabaseReference userRef;
    String userId;
    String name ;

    AddWorkExperience workExperience = new AddWorkExperience();
    AboutMeDescription aboutme = new AboutMeDescription();
    Education education;
    ArrayList<SelectedLanguages> list;
    Appreciation appreciation = new Appreciation();

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> languagesList;
    GridAdapter gridAdapter;
    SelectedLanguages selectedLanguages;
    LanguageGridAdapter languageGridAdapter;
    Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSettingeditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, SettingActivity.class));
            }
        });
        storagedlt = FirebaseStorage.getInstance().getReference("Resumes/");
        languagesList = new ArrayList<>();
        selectedLanguages = new SelectedLanguages();
        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            storageReference = FirebaseStorage.getInstance().getReference();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.listvieww.setNestedScrollingEnabled(true);
                binding.gridLanguage.setNestedScrollingEnabled(true);
            }

            userRef.child(userId).child("Languages").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        languagesList.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            SelectedLanguages selectedLanguages = snapshot1.getValue(SelectedLanguages.class);
                            assert selectedLanguages != null;
                            languagesList.add(selectedLanguages.getName());
                            languageGridAdapter = new LanguageGridAdapter(EditProfileActivity.this, languagesList);
                            binding.gridLanguage.setAdapter(languageGridAdapter);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userRef.child(userId).child("Skills").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value = dataSnapshot.getValue(String.class);
                    arrayList.add(value);

                    gridAdapter = new GridAdapter(EditProfileActivity.this, arrayList);

                    binding.listvieww.setAdapter(gridAdapter);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            fetchAboutMe();
            fetchWorkExperience();
            fetchEducation();
            fetchSkills();
            fetchLanguage();
            fetchAppreciation();
            fetchResume();

        }


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
            in.putExtra("levelEducation", education.getLevelOfEducation());
            in.putExtra("institute", education.getInstituteName());
            in.putExtra("educationStartDate", education.getStartDate());
            in.putExtra("educationEndDate", education.getEndDate());
            in.putExtra("isPositionEducation", education.isPosition());
            in.putExtra("fieldOfStudy", education.getFieldOfStudy());
            in.putExtra("educationDesc", education.getDescription());
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
                in.putExtra("awardName", appreciation.getAwardName());
                in.putExtra("awardCategory", appreciation.getAwardCategory());
                in.putExtra("awardDate", appreciation.getAwardEndDate());
                in.putExtra("awardDesc", appreciation.getAwardDescription());
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

    private void fetchResume() {
        userRef.child(userId).child("Resume").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    resume = snapshot.getValue(Resume.class);
                    binding.btnResumeAdd.setVisibility(View.GONE);
                    binding.view7.setVisibility(View.VISIBLE);
                    binding.imagePDF.setVisibility(View.VISIBLE);
                    binding.txtResumeFileName.setVisibility(View.VISIBLE);
                    binding.txtResumeInfor.setVisibility(View.VISIBLE);
                    binding.btnResumeDelete.setVisibility(View.VISIBLE);

                    assert resume != null;
                    binding.txtResumeFileName.setText(resume.getPdfTitle());
                    binding.txtResumeInfor.setText(resume.getSize() + " . " + resume.getTime());

                    binding.btnResumeDelete.setOnClickListener(view -> {

                        Toast.makeText(EditProfileActivity.this, resume.getPdfTitle() + "\n" + userId, Toast.LENGTH_SHORT).show();

                        userRef.child(userId).child("Resume").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditProfileActivity.this, "deleted from real", Toast.LENGTH_SHORT).show();

                                storagedlt.child(userId + "/").child("pdf/").child(resume.getPdfTitle()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(EditProfileActivity.this, "Comple", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditProfileActivity.this, e + "", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfileActivity.this, e + "", Toast.LENGTH_SHORT).show();

                            }
                        });



                    });


                } else {

                    binding.btnResumeAdd.setVisibility(View.VISIBLE);
                    binding.view7.setVisibility(View.GONE);
                    binding.imagePDF.setVisibility(View.GONE);
                    binding.txtResumeFileName.setVisibility(View.GONE);
                    binding.txtResumeInfor.setVisibility(View.GONE);
                    binding.btnResumeDelete.setVisibility(View.GONE);
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

                    list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        SelectedLanguages languages = snap.getValue(SelectedLanguages.class);
                        assert languages != null;
                        list.add(languages);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchSkills() {

        userRef.child(userId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.view4.setVisibility(View.VISIBLE);
                    ArrayList<Object> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Object obj = snap.getValue(Object.class);
                        list.add(obj);

                    }


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
                    binding.btnAppreciationAdd.setVisibility(View.GONE);
                    binding.btnAppreciationEdit.setVisibility(View.VISIBLE);
                    binding.txtAppreciation.setVisibility(View.VISIBLE);
                    binding.txtAppreciationDate.setVisibility(View.VISIBLE);
                    binding.txtAppreciationInstitue.setVisibility(View.VISIBLE);
                    binding.view6.setVisibility(View.VISIBLE);
                    appreciation = snapshot.getValue(Appreciation.class);

                    assert appreciation != null;
                    binding.txtAppreciation.setText(appreciation.getAwardName());
                    binding.txtAppreciationInstitue.setText(appreciation.getAwardCategory());
                    binding.txtAppreciationDate.setText(appreciation.getAwardEndDate());


                } else {
                    binding.btnAppreciationAdd.setVisibility(View.VISIBLE);
                    binding.btnAppreciationEdit.setVisibility(View.GONE);
                    binding.txtAppreciation.setVisibility(View.GONE);
                    binding.txtAppreciationDate.setVisibility(View.GONE);
                    binding.txtAppreciationInstitue.setVisibility(View.GONE);
                    binding.view6.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchEducation() {
        userRef.child(userId).child("Education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.btnEducationAdd.setVisibility(View.GONE);
                    binding.view3.setVisibility(View.VISIBLE);
                    binding.btnEducationEdit.setVisibility(View.VISIBLE);
                    binding.txtEducation.setVisibility(View.VISIBLE);
                    binding.txtEducationInstitue.setVisibility(View.VISIBLE);
                    binding.txtEducationDate.setVisibility(View.VISIBLE);
                    binding.txtEducationTimePeriod.setVisibility(View.VISIBLE);

                    education = snapshot.getValue(Education.class);

                    assert education != null;
                    binding.txtEducation.setText(education.getFieldOfStudy());
                    binding.txtEducationInstitue.setText(education.getInstituteName());
                    binding.txtEducationDate.setText(education.getStartDate() + " - " + education.getEndDate());
                    if (!education.getEndDate().isEmpty()) {
                        int years = Integer.parseInt(education.getEndDate()) - Integer.parseInt(education.getStartDate());
                        binding.txtEducationTimePeriod.setText(String.valueOf(years));
                    } else {
                        binding.txtEducationTimePeriod.setText(education.isPosition() ? "Currently Studying" : " ");
                    }
                } else {
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
                Toast.makeText(EditProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}