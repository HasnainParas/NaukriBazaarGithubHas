package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.Activities.ActivityMain;
import com.appstacks.indiannaukribazaar.FirebaseAdapters.FindjobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.SearchNapplyInstantJobActivity;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantJobPostActivity;
import com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies.JobStatusActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityFindJobsBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class FindJobsActivity extends AppCompatActivity {

    private ActivityFindJobsBinding binding;
    private DatabaseReference userRef, userProfileRef;
    private DatabaseReference allUserNormalJobs, instantJobsRef;
    private String currentUser;
    private PersonalInformationModel model;
    private String username, userAddress, userDpURL;
    private int size;

    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileUtils = new ProfileUtils(this);

        //Hashtag

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //FirebaseInstance
        userRef = FirebaseDatabase.getInstance().getReference("UsersInfo");

        userProfileRef = FirebaseDatabase.getInstance().getReference("UsersProfile");

        allUserNormalJobs = FirebaseDatabase.getInstance().getReference("allUserNormalJobs");

        instantJobsRef = FirebaseDatabase.getInstance().getReference("InstantJobs");




        //Funtion
        normalJobSize();

        instantJobSize();

        userDetailsFetch();

        recentJobsFetch();

        //buttonsClicks
         binding.addJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(FindJobsActivity.this, PaidJobsActivity.class));
                bottomDialog();

            }
        });
        binding.jobsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindJobsActivity.this, NormalJobsActivity.class));
            }
        });

        binding.instantJobService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(FindJobsActivity.this, SearchNapplyInstantJobActivity.class));
                finish();
//                Intent intent = new Intent(FindJobsActivity.this, AddPostsActivity.class);
//                intent.putExtra("username", username);
//                intent.putExtra("useraddress", userAddress);
//                if (size >= 3) {
//                    Toast.makeText(FindJobsActivity.this, "Your Limit is Finished", Toast.LENGTH_SHORT).show();
//                } else {
//                    startActivity(intent);
//                }
//                Toast.makeText(FindJobsActivity.this, Integer.toString(size), Toast.LENGTH_SHORT).show();
//                binding.textView49.setText(Integer.toString(size));


            }
        });



    }

    private void recentJobsFetch() {

        ArrayList<FindJobModel> list = new ArrayList<>();
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer", "Google inc, California, USA", "120k", "Remote", "Full time"));
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer", "Google inc, California, USA", "120k", "Remote", "Full time"));


        FindjobAdapter adapter = new FindjobAdapter(list, this);

        binding.recycerviewFindJobs.setAdapter(adapter);


        binding.recycerviewFindJobs.setHasFixedSize(true);
        binding.recycerviewFindJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recycerviewFindJobs.setAdapter(adapter);
    }

    private void userDetailsFetch() {
        userRef.
                child(currentUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            model = snapshot.getValue(PersonalInformationModel.class);
                            username = model.getFirstName() + " " + model.getLastName();
                            userAddress = model.getUserAddress();
//                    Toast.makeText(FindJobsActivity.this, username, Toast.LENGTH_SHORT).show();
                            binding.usernameid.setText("Hello\n" + username);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FindJobsActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

        userProfileRef
                .child(currentUser)
                .child("UserImage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userDpURL = snapshot.getValue(String.class);
                            Glide.with(FindJobsActivity.this)
                                    .load(userDpURL)
                                    .placeholder(R.drawable.profileplace)
                                    .into(binding.profileDPFindJob);
                        } else {
                            Toast.makeText(FindJobsActivity.this, "ImageURL Not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FindJobsActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void normalJobSize() {
        allUserNormalJobs.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        int size = (int) snapshot.getChildrenCount();
                        binding.normalJobsSize.setText(Integer.toString(size));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void instantJobSize() {
        instantJobsRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        int size = (int) snapshot.getChildrenCount();
                        binding.instantJobSize.setText(Integer.toString(size));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void bottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(FindJobsActivity.this, R.style.AppBottomSheetDialogTheme);

        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.post_job_request, (CardView) findViewById(R.id.UndoChanges));
        dialog.setContentView(bottomsheetView);
        dialog.show();
        dialog.setCancelable(false);

        Button jobpostBtn = bottomsheetView.findViewById(R.id.jobPostBtn);
        Button instantJobBtn = bottomsheetView.findViewById(R.id.instantJobBtn);
        Button jobStatus = bottomsheetView.findViewById(R.id.jobStatusBtn);
        ImageView cancenBtn = bottomsheetView.findViewById(R.id.postjobCancelBtn);

        jobStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindJobsActivity.this, JobStatusActivity.class));
                dialog.dismiss();
            }
        });

        jobpostBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(FindJobsActivity.this, AddNormalJobPostActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("useraddress", userAddress);
            intent.putExtra("userimage", userDpURL);
            if (size >= 10) {
                Toast.makeText(FindJobsActivity.this, "Your Limit is Finished", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(intent);
                dialog.dismiss();
            }
//            Toast.makeText(FindJobsActivity.this, Integer.toString(size), Toast.LENGTH_SHORT).show();
//            binding.textView49.setText(Integer.toString(size));

        });

        instantJobBtn.setOnClickListener(view -> {
            Intent intentin = new Intent(FindJobsActivity.this, InstantJobPostActivity.class);
            intentin.putExtra("usernameinstant", username);
            intentin.putExtra("useraddressinstant", userAddress);
            intentin.putExtra("userImageIn", userDpURL);

//            startActivity(new Intent(FindJobsActivity.this, InstantJobActivity.class));
            startActivity(intentin);
            dialog.dismiss();

        });


        cancenBtn.setOnClickListener(view -> dialog.dismiss());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FindJobsActivity.this,ActivityMain.class));
        finishAffinity();
        
    }
}
