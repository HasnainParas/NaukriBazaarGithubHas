package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.FindjobAdapter;
import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityFindJobsBinding;

import java.util.ArrayList;

public class FindJobsActivity extends AppCompatActivity {

    ActivityFindJobsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.addjobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(FindJobsActivity.this,AddJobsActivity.class));

            }
        });

        binding.paidJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(FindJobsActivity.this,PaidJobsActivity.class));




            }
        });


        ArrayList<FindJobModel> list = new ArrayList<>();
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer","Google inc, California, USA","120k","Remote","Full time"));
        list.add(new FindJobModel(R.drawable.ic_name,
                "Product Designer","Google inc, California, USA","120k","Remote","Full time"));


        FindjobAdapter adapter  = new FindjobAdapter(list,this);

        binding.recycerviewFindJobs.setAdapter(adapter);


        binding.recycerviewFindJobs.setHasFixedSize(true);
        binding.recycerviewFindJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recycerviewFindJobs.setAdapter(adapter);


    }



//    private void loadFragment(Fragment fragment) {
//        // load fragment
//
//
//    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.containerFjobs, fragment);
//        binding.containerFjobs.setVisibility(View.GONE);
//        transaction.addToBackStack(null);
//        transaction.commitAllowingStateLoss();
//    }



    }
