package com.appstacks.indiannaukribazaar.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.FirebaseModels.JobModel;
import com.appstacks.indiannaukribazaar.NewActivities.JobDetailsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.JobListLayoutBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.viewHolder> {

    ArrayList<JobModel> jobModelArrayList;
    Context context;

    public JobAdapter(ArrayList<JobModel> jobModelArrayList, Context context) {
        this.jobModelArrayList = jobModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_list_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        JobModel model = jobModelArrayList.get(position);

        holder.binding.companyname.setText(model.getCompanyName());
        holder.binding.jobtitle.setText(model.getJobTitle());
        holder.binding.jobType.setText(model.getJobType());
        holder.binding.salary.setText(model.getSalaryRange());

        try {
            Glide.with(context).load(model.getJobImg()).placeholder(R.drawable.avatar_placeholder).into(holder.binding.imgView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.binding.cardjobClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ky = model.getUniqueKey();
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("uniqueKey",model.getUniqueKey());
                intent.putExtra("title",model.getJobTitle());
                intent.putExtra("companyname",model.getCompanyName());
                intent.putExtra("type",model.getJobType());
                intent.putExtra("time",model.getJobTime());
                intent.putExtra("jobPostDate",   model.getJobPostDate());
                intent.putExtra("salary",model.getSalaryRange());
                intent.putExtra("applybefore",model.getApplyBefore());
                intent.putExtra("price",model.getJobPriceINR());
                intent.putExtra("jobdes",model.getJobDescription());
                intent.putExtra("roleandres",model.getRolesAndRESPONSIBILITIES());
                intent.putExtra("imageurl",model.getJobImg());
                intent.putExtra("location",model.getJobLocation());
                intent.putExtra("getRules", model.getRolesAndRESPONSIBILITIES());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);



            }
        });





    }

    @Override
    public int getItemCount() {
        return jobModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        JobListLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = JobListLayoutBinding.bind(itemView);

        }
    }

}
