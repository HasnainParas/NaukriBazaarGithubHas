package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.JobAdapter;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantAddJobsModel;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantSearchJobFrontModel;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.SearchNApplyInJobDetailActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ApplyinJobSampleBinding;

import java.util.ArrayList;

public class SearchNApplyINjobAdapter extends RecyclerView.Adapter<SearchNApplyINjobAdapter.viewHolder> {

    ArrayList<InstantAddJobsModel> modelArrayList;
    Context context;

    public SearchNApplyINjobAdapter(ArrayList<InstantAddJobsModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.applyin_job_sample, parent, false);
        return new SearchNApplyINjobAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        InstantAddJobsModel model = modelArrayList.get(position);


        holder.binding.jobInApplyTitle.setText(model.getInJobTitle());
        holder.binding.applyINcomTv.setText(model.getInJobCompany());
//        holder.binding.applyInJobSkillsTV.setText(model.getInJobPosition());
        holder.binding.priceTv.setText(model.getInJobBudget());
        holder.binding.applyInJobSkillsTV.setText(model.getInJobID());

        holder.binding.frontInstantJobLayoutClick.setOnClickListener(v -> {
            String id = model.getInJobID();
//            Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
            Intent in = new Intent(context.getApplicationContext(), SearchNApplyInJobDetailActivity.class);
            in.putExtra("randomid", id);

            context.startActivity(in);
//            ((Activity) context).finish();
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ApplyinJobSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ApplyinJobSampleBinding.bind(itemView);
        }
    }

}
