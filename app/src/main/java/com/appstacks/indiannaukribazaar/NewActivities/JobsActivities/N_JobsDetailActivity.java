package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantAddJobsModel;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.AdapterForListview;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityNjobsDetailBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class N_JobsDetailActivity extends AppCompatActivity {
    ActivityNjobsDetailBinding binding;

    String jobID, postedAuthID;
    String timeagoNormalJob;

    DatabaseReference normalJobsRef;
    private ArrayAdapter<String> eligibilitiesAdapter;
    private ArrayAdapter<String> facilitiesAdapter;

    private AdapterForListview eliAdapter;
    private AdapterForListview facAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNjobsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        normalJobsRef = FirebaseDatabase.getInstance().getReference("allUserNormalJobs");


        jobID = getIntent().getStringExtra("normaljobID");
        postedAuthID = getIntent().getStringExtra("whoPostedJobUserID");


        fetchEligibilities();
        fetchFacilities();


        binding.normalApplyJobBTn.setOnClickListener(v -> {
            Intent in = new Intent(N_JobsDetailActivity.this, NormalJobCvActivity.class);
            in.putExtra("jobid", jobID);
            in.putExtra("postedUserAuth", postedAuthID);
            in.putExtra("normalJobTime", binding.timeText.getText().toString());
            startActivity(in);

        });

        normalJobsRef.child(jobID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserJobModel model = snapshot.getValue(UserJobModel.class);

                    assert model != null;
//                    binding.jobTitleInstant.setText(model.getInJobTitle());
//                    binding.comTextInstant.setText(model.getInJobCompany());
//                    binding.comTextInstant.setText(model.getInJobCompany());
                    Glide.with(N_JobsDetailActivity.this)
                            .load(model.getCompanyImageURL())
                            .placeholder(R.drawable.placeholder)
                            .into(binding.comLogo);
                    timeagoNormalJob = calculateTimeAgoNormalJob(model.getJobPostedDate());
                    binding.timeText.setText(timeagoNormalJob);
                    binding.jobTitle.setText(model.getJobTitle());
                    binding.comText.setText(model.getCompanyName());
                    binding.locationText.setText("$" + model.getSalary());
                    binding.descriptiontext.setText(model.getDescription());

                    binding.locationTxtNormaljob.setText(model.getJobLocation());
                    binding.positionTxtViewNormalJob.setText(model.getJobPosition());
                    binding.qualificationNormalJobTv.setText(model.getQualifications());
                    binding.experienceNormalJobTv.setText(model.getExperience());
                    binding.jobTypeNormalJobTv.setText(model.getTypeOfWorkPlace());
                    binding.specializationNormalJobTv.setText(model.getSpecialization());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(N_JobsDetailActivity.this, error.getMessage() + " ", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchEligibilities() {
        normalJobsRef.child(jobID)
                .child("jobEligibilities")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            ArrayList<String> list1 = new ArrayList<>();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String obj = snap.getValue(String.class);
                                list1.add(obj);
//                                binding.injobdetailsListInstant.setVisibility(View.VISIBLE);
//                                binding.injoblistloaderInstant.setVisibility(View.GONE);
//                                binding.injoblistloadingTvInstant.setVisibility(View.GONE);
//                        holder.binding.injobdetailsList.setVerticalScrollBarEnabled(true);

                            }

//                            eligibilitiesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layoutinjobdetailskill, R.id.injobdetailskillTv, list1);
                            eliAdapter = new AdapterForListview(list1, N_JobsDetailActivity.this);
                            binding.eligibilityListView
                                    .setLayoutManager(new LinearLayoutManager(
                                            N_JobsDetailActivity.this));
                            binding.eligibilityListView.setAdapter(eliAdapter);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void fetchFacilities() {
        normalJobsRef.child(jobID)
                .child("jobFacilities")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            ArrayList<String> list2 = new ArrayList<>();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String objfaci = snap.getValue(String.class);
                                list2.add(objfaci);
//                                binding.injobdetailsListInstant.setVisibility(View.VISIBLE);
//                                binding.injoblistloaderInstant.setVisibility(View.GONE);
//                                binding.injoblistloadingTvInstant.setVisibility(View.GONE);
//                        holder.binding.injobdetailsList.setVerticalScrollBarEnabled(true);

                            }

//                            facilitiesAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                                    R.layout.layoutinjobdetailskill, R.id.injobdetailskillTv, list2);
//
//                            binding.facilitiesListView.setAdapter(facilitiesAdapter);
                            facAdapter = new AdapterForListview(list2, N_JobsDetailActivity.this);
//                            binding..setLayoutManager(new LinearLayoutManager );
                            binding.facilitiesListView
                                    .setLayoutManager(new LinearLayoutManager(
                                            N_JobsDetailActivity.this));

                            binding.facilitiesListView.setAdapter(facAdapter);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private String calculateTimeAgoNormalJob(String jobPostedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
            long time = sdf.parse(jobPostedDate).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }
}