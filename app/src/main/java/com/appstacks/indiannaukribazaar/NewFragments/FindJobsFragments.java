package com.appstacks.indiannaukribazaar.NewFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.FindjobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.NewActivities.AddJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.FragmentFindJobsFragmentsBinding;
import com.appstacks.indiannaukribazaar.databinding.FragmentJobBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FindJobsFragments extends Fragment {

    FragmentFindJobsFragmentsBinding binding;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentFindJobsFragmentsBinding.inflate(getLayoutInflater(),container,false);
        // Inflate the layout for this fragment








        binding.addjobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "hhhhh", Toast.LENGTH_SHORT).show();
            }
        });

        binding.paidJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new JobFragment());




            }
        });


        ArrayList<FindJobModel> list = new ArrayList<>();
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer","Google inc, California, USA","120k","Remote","Full time"));
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer","Google inc, California, USA","120k","Remote","Full time"));


        FindjobAdapter adapter  = new FindjobAdapter(list,getContext());

        binding.recycerviewFindJobs.setAdapter(adapter);


       binding.recycerviewFindJobs.setHasFixedSize(true);
        binding.recycerviewFindJobs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
       binding.recycerviewFindJobs.setAdapter(adapter);

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