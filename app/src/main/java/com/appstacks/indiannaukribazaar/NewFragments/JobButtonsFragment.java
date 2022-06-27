package com.appstacks.indiannaukribazaar.NewFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstacks.indiannaukribazaar.Activities.ActivityMain;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.KycStartBrowsingActivity;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.WelldoneActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.FragmentJobBinding;
import com.appstacks.indiannaukribazaar.databinding.FragmentJobButtonsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class JobButtonsFragment extends Fragment {

    FragmentJobButtonsBinding binding;
    DatabaseReference allUserRef;
    FirebaseAuth auth;
    String currentUserAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJobButtonsBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        currentUserAuth = auth.getCurrentUser().getUid();

        allUserRef = FirebaseDatabase.getInstance().getReference("AllUsers").child(currentUserAuth);


        binding.clickJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allUserRef.child("verification").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            boolean istrue = snapshot.getValue(Boolean.class);
                            Intent intent;
                            if (istrue) {
                                intent = new Intent(getActivity(), WelldoneActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);

                            } else {
                                intent = new Intent(getActivity(), KycStartBrowsingActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        return binding.getRoot();
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}