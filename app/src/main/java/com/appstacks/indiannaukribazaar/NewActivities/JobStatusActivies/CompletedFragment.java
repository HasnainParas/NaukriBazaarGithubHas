package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.FragmentCompletedBinding;
import com.appstacks.indiannaukribazaar.databinding.FragmentWellDoneBinding;

public class CompletedFragment extends Fragment {

    FragmentCompletedBinding binding;
    public CompletedFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCompletedBinding.inflate(inflater, container, false);




        return binding.getRoot();
    }
}