package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters.InJobDetailsAdapter;
import com.appstacks.indiannaukribazaar.databinding.ActivitySearchNapplyInJobDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchNApplyInJobDetailActivity extends AppCompatActivity {

    ActivitySearchNapplyInJobDetailBinding binding;
    String randomId;
    DatabaseReference inAllJobsRef;
    ArrayList<InstantAddJobsModel> detaislarrayList;
    InJobDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchNapplyInJobDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        randomId = getIntent().getStringExtra("randomid");
//        binding.checkingtext.setText(randomId);

        binding.injobDetailBack.setOnClickListener(v -> onBackPressed());

        inAllJobsRef = FirebaseDatabase.getInstance().getReference();

        detaislarrayList = new ArrayList<>();
        if (randomId != null) {
//            detaislarrayList.clear();
            inAllJobsRef
                    .child("InstantJobs")
                    .child(randomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
//                    Toast.makeText(SearchNApplyInJobDetailActivity.this, "yahn tak mil rha hai", Toast.LENGTH_SHORT).show();
                    InstantAddJobsModel data = snapshot.getValue(InstantAddJobsModel.class);

                    detaislarrayList.add(data);
                    adapter = new InJobDetailsAdapter(detaislarrayList, SearchNApplyInJobDetailActivity.this);

                    binding.inJobDetailsRecyclerV.setAdapter(adapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchNApplyInJobDetailActivity.this);
                    binding.inJobDetailsRecyclerV.setLayoutManager(linearLayoutManager);
                } else {
                    Toast.makeText(SearchNApplyInJobDetailActivity.this, "Job Empty", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchNApplyInJobDetailActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        detaislarrayList.clear();
    }
}