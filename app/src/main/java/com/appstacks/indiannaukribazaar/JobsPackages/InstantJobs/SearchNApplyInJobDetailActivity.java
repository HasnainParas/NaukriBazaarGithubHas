package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters.InJobDetailsAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.N_JobsDetailActivity;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.NormalJobCvActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivitySearchNapplyInJobDetailBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SearchNApplyInJobDetailActivity extends AppCompatActivity {

    ActivitySearchNapplyInJobDetailBinding binding;

    private String randomIdJob, jobUserAuth;
    DatabaseReference inAllIntantJobsRef, skillRef;
    String timeago;
    private ArrayAdapter<String> adapter;


//    ArrayList<InstantAddJobsModel> detaislarrayList;
//    InJobDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchNapplyInJobDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        randomIdJob = getIntent().getStringExtra("randomid");
        jobUserAuth = getIntent().getStringExtra("jobuserAuth");
//        binding.checkingtext.setText(randomId);

        inAllIntantJobsRef = FirebaseDatabase.getInstance().getReference("InstantJobs");


        Toast.makeText(this, jobUserAuth + " ", Toast.LENGTH_SHORT).show();

        binding.ApplyJobBTnInstant.setOnClickListener(v -> {
            if (randomIdJob != null) {
                Intent in = new Intent(SearchNApplyInJobDetailActivity.this, InstantJobCvActivity.class);
                in.putExtra("instantJobID", randomIdJob);
                in.putExtra("instantJobUserAuth", jobUserAuth);
                in.putExtra("instantJobTime", binding.timeTextInstant.getText().toString());
                startActivity(in);
            }
        });


        if (randomIdJob != null) {
            inAllIntantJobsRef.child(randomIdJob).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        InstantAddJobsModel model = snapshot.getValue(InstantAddJobsModel.class);

                        assert model != null;
                        binding.jobTitleInstant.setText(model.getInJobTitle());
                        binding.comTextInstant.setText(model.getInJobCompany());

                        Glide.with(SearchNApplyInJobDetailActivity.this)
                                .load(model.getInJobCompanyImgURL())
                                .placeholder(R.drawable.placeholder)
                                .into(binding.comLogoInstant);
//                        jobUserAuth = model.getUserAuthID();
                        timeago = calculateTimeAgoo(model.getPostedDate());
                        binding.timeTextInstant.setText(timeago);

                        binding.descriptiontextInstant.setText(model.getInJobDes());
                        binding.budgetTextInstant.setText(model.getInJobBudget());
                        binding.positionTViewInstant.setText(model.getInJobPosition());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        binding.injobDetailBack.setOnClickListener(v -> onBackPressed());

        skills();


//        if (randomId != null) {
////            detaislarrayList.clear();
//            inAllJobsRef
//                    .child("InstantJobs")
//                    .child(randomId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()) {
////                    Toast.makeText(SearchNApplyInJobDetailActivity.this, "yahn tak mil rha hai", Toast.LENGTH_SHORT).show();
//                    InstantAddJobsModel data = snapshot.getValue(InstantAddJobsModel.class);
//
//                    detaislarrayList.add(data);
//                    adapter = new InJobDetailsAdapter(detaislarrayList, SearchNApplyInJobDetailActivity.this);
//
//                    binding.inJobDetailsRecyclerV.setAdapter(adapter);
//
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchNApplyInJobDetailActivity.this);
//                    binding.inJobDetailsRecyclerV.setLayoutManager(linearLayoutManager);
//                } else {
//                    Toast.makeText(SearchNApplyInJobDetailActivity.this, "Job Empty", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(SearchNApplyInJobDetailActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//        }


    }

    private String skills() {
        inAllIntantJobsRef.child(randomIdJob)
                .child("inSkills")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            ArrayList<String> list1 = new ArrayList<>();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String obj = snap.getValue(String.class);
                                list1.add(obj);
                                binding.injobdetailsListInstant.setVisibility(View.VISIBLE);
                                binding.injoblistloaderInstant.setVisibility(View.GONE);
                                binding.injoblistloadingTvInstant.setVisibility(View.GONE);
//                        holder.binding.injobdetailsList.setVerticalScrollBarEnabled(true);
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layoutinjobdetailskill, R.id.injobdetailskillTv, list1);
                            binding.injobdetailsListInstant.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return "";

    }

    private String calculateTimeAgoo(String postedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.getDefault());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
            long time = sdf.parse(postedDate).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        detaislarrayList.clear();
    }
}