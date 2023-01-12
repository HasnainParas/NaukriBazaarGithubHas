package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobTitleAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.JobModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityFullTimeJobBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FullTimeJobActivity extends AppCompatActivity {

    ActivityFullTimeJobBinding binding;
    ArrayList<UserJobModel> jobModelArrayList;
    JobTitleAdapter jobTitleAdapter;
    DatabaseReference userJobRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullTimeJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        jobModelArrayList = new ArrayList<>();

        userJobRef = FirebaseDatabase.getInstance().getReference("allUserJobs");

        userJobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        UserJobModel data = s.getValue(UserJobModel.class);
//                        jobModelArrayList.clear();
                        jobModelArrayList.add(data);
                        jobTitleAdapter = new JobTitleAdapter(jobModelArrayList, getApplicationContext());

                        binding.userjobrecycler.setAdapter(jobTitleAdapter);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        binding.userjobrecycler.setLayoutManager(linearLayoutManager);
//                        binding.loadingFragment.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FullTimeJobActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}