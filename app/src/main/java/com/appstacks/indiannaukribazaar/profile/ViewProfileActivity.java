package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.ProfileModels.Followers;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityViewProfileBinding;
import com.appstacks.indiannaukribazaar.profile.Education.Education;
import com.appstacks.indiannaukribazaar.profile.Lanuages.SelectedLanguages;
import com.appstacks.indiannaukribazaar.profile.appreciation.Appreciation;
import com.appstacks.indiannaukribazaar.profile.resume.Resume;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity {
    ActivityViewProfileBinding binding;
    private ArrayList<Feedback> list;
    private FeedbackAdapter adapter;
    private String appliedUserId, currentUserId;
    private DatabaseReference userRef, allUser;
    private static final String TAG = "ViewProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userRef = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));
        allUser = FirebaseDatabase.getInstance().getReference("UsersInfo");
        appliedUserId = getIntent().getStringExtra("userId");

        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchBio();
        fetchWorkExperience();
        fetchEducation();
        fetchSkills();
        fetchLanguage();
        fetchAppreciation();
        fetchResume();
        fetchHourlyCharges();
        fetchUserImage();
        fetchNameAddress();

        fetchFollowers();

        userRef.child(currentUserId).child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(appliedUserId).exists()) {
                    binding.imgFollowing.setVisibility(View.GONE);
                    binding.txtFollow.setText("Following");

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.btnFollowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkIfFollowed(currentUserId, appliedUserId);


            }
        });
        list = new ArrayList<>();
        list.add(new Feedback("5", "17 Feb", "Android app development", "Mehboob is such a nice guy he did very well and amazing. i will highly recomend him", "$ 1,300", "$45/Hr", "74 Hrs"));
        list.add(new Feedback("5", "17 Feb", "Android app development", "Mehboob is such a nice guy he did very well and amazing. i will highly recomend him", "$ 1,300", "$45/Hr", "74 Hrs"));
        list.add(new Feedback("5", "17 Feb", "Android app development", "Mehboob is such a nice guy he did very well and amazing. i will highly recomend him", "$ 1,300", "$45/Hr", "74 Hrs"));

        adapter = new FeedbackAdapter(this, list);
//        binding.recyclerFeedBack.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//        binding.recyclerFeedBack.setAdapter(adapter);

    }

    private void fetchFollowers() {
        userRef.child(appliedUserId).child("Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int follower = (int) (snapshot.getChildrenCount());
                    binding.txtFollowerProfile.setText(follower + " Follower");
                }else{
                    binding.txtFollowerProfile.setText(0 + " Follower");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkIfFollowed(String currentUserId, String appliedUserId) {

        userRef.child(currentUserId).child("Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(appliedUserId).exists()) {
                    binding.imgFollowing.setVisibility(View.GONE);
                    binding.txtFollow.setText("Following");
                    Toast.makeText(ViewProfileActivity.this, "You are following the profile", Toast.LENGTH_SHORT).show();
                } else {
                    userRef.child(currentUserId).child("Following").child(appliedUserId).setValue(new Followers(appliedUserId))
                            .addOnCompleteListener(task -> {
                                if (task.isComplete() && task.isSuccessful()) {
                                    Toast.makeText(ViewProfileActivity.this, "You Followed a profile", Toast.LENGTH_SHORT).show();
                                    userRef.child(appliedUserId).child("Followers").child(currentUserId).setValue(new Followers(currentUserId));
                                    binding.imgFollowing.setVisibility(View.GONE);
                                    binding.txtFollow.setText("Following");
                                } else {
                                    Toast.makeText(ViewProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(e -> {

                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchNameAddress() {
        allUser.child(appliedUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    PersonalInformationModel model = snapshot.getValue(PersonalInformationModel.class);

                    binding.txtNameeditProfile.setText(model.getFirstName() + " " + model.getLastName());
                    binding.txtLocationProfile.setText(model.getCity());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchWorkExperience() {
        userRef.child(appliedUserId).child("WorkExperience").addValueEventListener(new ValueEventListener() {

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

    private void fetchUserImage() {

        userRef.child(appliedUserId).child("UserImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url = snapshot.getValue(String.class);
                Glide.with(getApplicationContext()).load(url).into(binding.circleImageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchBio() {

        userRef.child(appliedUserId).child("AboutMe").addValueEventListener(new ValueEventListener() {
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


    private void fetchHourlyCharges() {
        userRef.child(appliedUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Hourly Charges").exists()) {
                    String data = snapshot.child("Hourly Charges").getValue(String.class);
                    binding.txtHourlyChargesInfo.setText(data + "$ Per Hour");
                    binding.txtHourlyRate.setText("$ " + data + ".00");
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
        userRef.child(appliedUserId).child("Resume").addValueEventListener(new ValueEventListener() {
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

        userRef.child(appliedUserId).child("Appreciation").addValueEventListener(new ValueEventListener() {
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
        userRef.child(appliedUserId).child("Education").addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(ViewProfileActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewProfileActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchSkills() {

        userRef.child(appliedUserId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ArrayList<String> list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list.add(obj);


                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewProfileActivity.this, R.layout.grid_item, R.id.tvidd, list);

                    binding.listvieww.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void fetchLanguage() {
        userRef.child(appliedUserId).child("Languages").addValueEventListener(new ValueEventListener() {
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewProfileActivity.this, R.layout.grid_item, R.id.tvidd, list);

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
}