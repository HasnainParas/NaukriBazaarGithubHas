package com.appstacks.indiannaukribazaar.NewFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstacks.indiannaukribazaar.Activities.ActivityMain;
import com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs.WelldoneActivity;
import com.appstacks.indiannaukribazaar.databinding.FragmentWellDoneBinding;

public class WellDoneFragment extends Fragment {

    FragmentWellDoneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWellDoneBinding.inflate(inflater, container, false);


        binding.goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityMain.class));
                getActivity().finishAffinity();
            }
        });


        return binding.getRoot();
    }
}