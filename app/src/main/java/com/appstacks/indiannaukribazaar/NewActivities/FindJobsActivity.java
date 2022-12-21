package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.FindjobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.FirebaseModels.JobModel;
import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityFindJobsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class FindJobsActivity extends AppCompatActivity {

    ActivityFindJobsBinding binding;
    FirebaseAuth auth;
    DatabaseReference userRef, userJobRef, allUserJobs;
    String currentUser;
    PersonalInformationModel model;
    String username, userAddress;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //Hashtag
        auth = FirebaseAuth.getInstance();

        currentUser = auth.getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference();

        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        allUserJobs = FirebaseDatabase.getInstance().getReference("allUserJobs");


        allUserJobs.addValueEventListener(new ValueEventListener() {
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


        binding.addjobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindJobsActivity.this, AddPostsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("useraddress", userAddress);
                if (size >= 3) {
                    Toast.makeText(FindJobsActivity.this, "Your Limit is Finished", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
                Toast.makeText(FindJobsActivity.this, Integer.toString(size), Toast.LENGTH_SHORT).show();
                binding.textView49.setText(Integer.toString(size));

            }
        });

        binding.paidJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindJobsActivity.this, PaidJobsActivity.class));


            }
        });
        binding.fullTimeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindJobsActivity.this, FullTimeJobActivity.class));

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


}
