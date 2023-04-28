package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters.SearchNApplyINjobAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.databinding.ActivitySearchNapplyInstantJobBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchNapplyInstantJobActivity extends AppCompatActivity {

    ActivitySearchNapplyInstantJobBinding binding;
    ArrayList<InstantAddJobsModel> arrayList;
    SearchNApplyINjobAdapter adapter;
    DatabaseReference instantAllJobRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchNapplyInstantJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        instantAllJobRef = FirebaseDatabase.getInstance().getReference("InstantJobs");


//        DatabaseReference child = inJobRef.child(authUserID);

        arrayList = new ArrayList<>();


        instantAllJobRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        InstantAddJobsModel data = s.getValue(InstantAddJobsModel.class);
//                        jobModelArrayList.clear();
                        if (!data.getUserAuthID().equals(FirebaseAuth.getInstance().getUid()))

                            arrayList.add(data);
                        adapter = new SearchNApplyINjobAdapter(arrayList, SearchNapplyInstantJobActivity.this);

                        binding.recyclerViewInJob.setAdapter(adapter);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchNapplyInstantJobActivity.this);
                        binding.recyclerViewInJob.setLayoutManager(linearLayoutManager);
//                        binding.loadingFragment.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchNapplyInstantJobActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SearchNapplyInstantJobActivity.this, FindJobsActivity.class));
    }
}