package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.JobAppliedModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityProposalsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProposalsActivity extends AppCompatActivity {

    ActivityProposalsBinding binding;
    ArrayList<JobAppliedModel> modelArrayList;
    ProposalsAdapter adapter;
    private DatabaseReference appliedRef;

    private String jobID;
    private JobAppliedModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProposalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        jobID = getIntent().getStringExtra("statusjobID");
        appliedRef = FirebaseDatabase.getInstance().getReference("UsersAppliedInstantJobs");


        if (jobID != null) {
//            Toast.makeText(this, jobID, Toast.LENGTH_SHORT).show();
//            binding.textView96.setText(jobID);

            modelArrayList = new ArrayList<>();

            appliedRef.child(jobID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            model = snap.getValue(JobAppliedModel.class);
//                            modelArrayList.clear();
                            modelArrayList.add(model);

                            adapter = new ProposalsAdapter(modelArrayList, ProposalsActivity.this);
                            binding.proposalrec.setLayoutManager(new LinearLayoutManager(ProposalsActivity.this));
                            binding.proposalrec.setAdapter(adapter);
                        }


                    }else {

                        Toast.makeText(ProposalsActivity.this, "Job Not Exist", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else {
            Toast.makeText(this, "User Not Posted Data", Toast.LENGTH_SHORT).show();
        }




    }
}