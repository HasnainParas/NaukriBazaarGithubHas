package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters.InJobDetailsAdapter;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantAddJobsModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.UserjobsLayoutBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Locale;

public class ActiveUserJobsAdapter extends RecyclerView.Adapter<ActiveUserJobsAdapter.viewHolderSS> {

    ArrayList<InstantAddJobsModel> modelArrayList;
    Context context;
    private DatabaseReference jobsRef4proposals;
    private int size;

    public ActiveUserJobsAdapter(ArrayList<InstantAddJobsModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderSS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userjobs_layout, parent, false);
        return new ActiveUserJobsAdapter.viewHolderSS(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderSS holder, int position) {
        InstantAddJobsModel model = modelArrayList.get(position);
        jobsRef4proposals = FirebaseDatabase.getInstance().getReference("UsersAppliedInstantJobs");

        String jobID = model.getInJobID();

        Glide.with(context)
                .load(model.getInJobCompanyImgURL())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.comPictureID);
        holder.binding.statusJobTitle.setText(model.getInJobTitle());
        holder.binding.statusCompanytviewID.setText(model.getInJobCompany());
        holder.binding.statusjobDateTviewID.setText(calculateTimeAgoo(model.getPostedDate()));


        jobsRef4proposals.child(jobID).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    size = (int) snapshot.getChildrenCount();
                    holder.binding.statusjobProposals.setText(size + " Proposals");
                } else {
                    holder.binding.statusjobProposals.setText("0 Proposals");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.binding.proposalsViewBtn.setOnClickListener(v -> {
//            if (size == 0) {
//                Toast.makeText(context, "No one Applied Yet!", Toast.LENGTH_SHORT).show();
//            } else {
                Intent intent = new Intent(context, ProposalsActivity.class);
                intent.putExtra("statusjobID", model.getInJobID());
                context.startActivity(intent);
                Toast.makeText(context, model.getInJobID(), Toast.LENGTH_SHORT).show();
//            }
        });


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
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class viewHolderSS extends RecyclerView.ViewHolder {
        UserjobsLayoutBinding binding;

        public viewHolderSS(@NonNull View itemView) {
            super(itemView);
            binding = UserjobsLayoutBinding.bind(itemView);

        }
    }

}
