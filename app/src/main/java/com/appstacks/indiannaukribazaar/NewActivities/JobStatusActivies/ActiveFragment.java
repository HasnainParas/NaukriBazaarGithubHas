package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantAddJobsModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.FragmentActiveBinding;
import com.appstacks.indiannaukribazaar.databinding.FragmentWellDoneBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveFragment extends Fragment {

    FragmentActiveBinding binding;
    ArrayList<InstantAddJobsModel> modelArrayList;
    ActiveUserJobsAdapter activeUserJobsAdapter;
    DatabaseReference instantUserJobs;
    String currentUser;
    private InstantAddJobsModel model;

    public ActiveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentActiveBinding.inflate(inflater, container, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        instantUserJobs = FirebaseDatabase.getInstance().getReference("InstantJobsWithAuthID");

        modelArrayList = new ArrayList<>();

        instantUserJobs.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {

                        model = s.getValue(InstantAddJobsModel.class);
//                        modelArrayList.clear();
                        modelArrayList.add(model);
                        activeUserJobsAdapter = new ActiveUserJobsAdapter(modelArrayList, getContext());
                    }

                    binding.statusActiveRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.statusActiveRecyclerView.setAdapter(activeUserJobsAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}