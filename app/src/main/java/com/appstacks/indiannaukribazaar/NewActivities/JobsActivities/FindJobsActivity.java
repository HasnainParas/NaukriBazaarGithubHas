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

import com.appstacks.indiannaukribazaar.FirebaseAdapters.FindjobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.SearchNapplyInstantJobActivity;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantJobPostActivity;
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
    private FirebaseAuth auth;
    private DatabaseReference userRef, userJobRef, allUserJobs;
    private String currentUser;
    private PersonalInformationModel model;
    private String username, userAddress;
    private int size;

    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Hashtag
        auth = FirebaseAuth.getInstance();

        profileUtils= new ProfileUtils(this);

        currentUser = auth.getCurrentUser().getUid();



        userRef = FirebaseDatabase.getInstance().getReference();

        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        allUserJobs = FirebaseDatabase.getInstance().getReference("allUserJobs");

        Glide.with(this).load(profileUtils.fetchUserImage()).placeholder(R.drawable.profileplace).into(binding.imageView13);

        allUserJobs.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        int size = (int) snapshot.getChildrenCount();
                        UserJobModel data = s.getValue(UserJobModel.class);
                        binding.allusrJobSize.setText(Integer.toString(size));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        userJobRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    size = (int) snapshot.getChildrenCount();
                    if (size > 2) {
                        Toast.makeText(FindJobsActivity.this, "Size is 2 ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        userRef.child("UsersInfo").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    model = snapshot.getValue(PersonalInformationModel.class);
                    username = model.getFirstName() + " " + model.getLastName();
                    userAddress = model.getUserAddress();
                    Toast.makeText(FindJobsActivity.this, username, Toast.LENGTH_SHORT).show();
                    binding.usernameid.setText("Hello\n" + username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FindJobsActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
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

        jobpostBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(FindJobsActivity.this, AddNormalJobPostActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("useraddress", userAddress);
            if (size >= 10) {
                Toast.makeText(FindJobsActivity.this, "Your Limit is Finished", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(intent);
                dialog.dismiss();
            }
            Toast.makeText(FindJobsActivity.this, Integer.toString(size), Toast.LENGTH_SHORT).show();
            binding.textView49.setText(Integer.toString(size));

        });

        instantJobBtn.setOnClickListener(view -> {
            Intent intentin = new Intent(FindJobsActivity.this, InstantJobPostActivity.class);
            intentin.putExtra("usernameinstant", username);
            intentin.putExtra("useraddressinstant", userAddress);
//            startActivity(new Intent(FindJobsActivity.this, InstantJobActivity.class));
            startActivity(intentin);
            dialog.dismiss();
            finish();

        });


        cancenBtn.setOnClickListener(view -> dialog.dismiss());

    }


}
