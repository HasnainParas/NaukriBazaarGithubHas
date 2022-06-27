package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.JobModel;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.KycStartBrowsingActivity;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.WelldoneActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivitySpinWheelBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpinWheelActivity extends AppCompatActivity {

    DatabaseReference jobRef;
    ActivitySpinWheelBinding binding;
    DatabaseReference allUserRef;
    FirebaseAuth auth;
    String currentUserAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinWheelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        currentUserAuth = auth.getCurrentUser().getUid();

        allUserRef = FirebaseDatabase.getInstance().getReference("AllUsers").child(currentUserAuth);
//        binding.clickJob44.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                allUserRef.child("verification").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            boolean istrue = snapshot.getValue(Boolean.class);
//                            Intent intent;
//                            if (istrue) {
//                                intent = new Intent(
//                                        SpinWheelActivity.this, WelldoneActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                startActivity(intent);
//                                finish();
//
//                            } else {
//                                intent = new Intent(SpinWheelActivity.this, KycStartBrowsingActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                                startActivity(intent);
//                                finish();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//
//            }
//        });




//        jobRef = FirebaseDatabase.getInstance().getReference("AdminPanel").child("job");
//
//
//        jobRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//
//                    for (DataSnapshot s : snapshot.getChildren()) {
//                        JobModel data = s.getValue(JobModel.class);
//                        jobList.add(data);
//                        jobAdapter = new JobAdapter(jobList, getContext());
//
//                        binding.jobRecyclerView.setAdapter(jobAdapter);
//
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                        binding.jobRecyclerView.setLayoutManager(linearLayoutManager);
////                        binding.loadingFragment.setVisibility(View.GONE);
//
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });




    }



}