package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobTitleAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.databinding.ActivityNormalJobsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NormalJobsActivity extends AppCompatActivity {

    private ActivityNormalJobsBinding binding;
    private ArrayList<UserJobModel> jobModelArrayList;
    private JobTitleAdapter jobTitleAdapter;
    private DatabaseReference userJobRef;
    private UserJobModel data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNormalJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        jobModelArrayList = new ArrayList<>();

        userJobRef = FirebaseDatabase.getInstance().getReference("allUserJobs");

        userJobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.noJobsLayout.setVisibility(View.GONE);
                    binding.userjobrecycler.setVisibility(View.VISIBLE);
                    for (DataSnapshot s : snapshot.getChildren()) {
                        data = s.getValue(UserJobModel.class);
//                        jobModelArrayList.clear();
                        if (!data.getUserAuthId().equals(FirebaseAuth.getInstance().getUid())) {
                            jobModelArrayList.add(data);
                            binding.noJobsLayout.setVisibility(View.GONE);
                            binding.userjobrecycler.setVisibility(View.VISIBLE);
                        } else {
                            binding.noJobsLayout.setVisibility(View.VISIBLE);
                            binding.userjobrecycler.setVisibility(View.GONE);
                        }

                    }


                    jobTitleAdapter = new JobTitleAdapter(jobModelArrayList, NormalJobsActivity.this);


                    binding.userjobrecycler.setLayoutManager(new LinearLayoutManager(NormalJobsActivity.this));
                    binding.userjobrecycler.setAdapter(jobTitleAdapter);
//                        binding.loadingFragment.setVisibility(View.GONE);

                } else {
                    binding.noJobsLayout.setVisibility(View.VISIBLE);
                    binding.userjobrecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NormalJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}