package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.JobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityPaidJobsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaidJobsActivity extends AppCompatActivity {

    ActivityPaidJobsBinding binding;

    DatabaseReference jobRef;
    ArrayList<JobModel> jobList;
    JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaidJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        jobRef = FirebaseDatabase.getInstance().getReference("AdminPanel").child("job");

        jobList = new ArrayList<>();

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        int size = (int) snapshot.getChildrenCount();

                        JobModel data = s.getValue(JobModel.class);
                        binding.jobsFound.setText(Integer.toString(size)+ " Jobs Found");
                        binding.loadingid.setVisibility(View.GONE);
                        binding.jobsFound.setVisibility(View.VISIBLE);
                        jobList.add(data);
                        jobAdapter = new JobAdapter(jobList, getApplicationContext());

                        binding.jobRecyclerView.setAdapter(jobAdapter);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        binding.jobRecyclerView.setLayoutManager(linearLayoutManager);
//                        binding.loadingFragment.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaidJobsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//
//        if (snapshot.exists()) {
//            listPoet.clear();
//            for (DataSnapshot s : snapshot.getChildren()) {
//                PoetModel data = s.getValue(PoetModel.class);
//
//                listPoet.add(data);
////                        Arrays.sort(new ArrayList[]{listPoet});
//
//                poetFragmentAdapter = new PoetAdapter(listPoet, getContext());
//
//                binding.poetRecyclerView.setAdapter(poetFragmentAdapter);
//
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//                binding.poetRecyclerView.setLayoutManager(gridLayoutManager);
//                binding.loadingFragment.setVisibility(View.GONE);
//
//            }




    }
}