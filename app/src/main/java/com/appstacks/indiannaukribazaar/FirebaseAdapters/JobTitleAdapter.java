package com.appstacks.indiannaukribazaar.FirebaseAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.N_JobsDetailActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.JobtitlesampleLayoutBinding;

import java.util.ArrayList;

public class JobTitleAdapter extends RecyclerView.Adapter<JobTitleAdapter.viewHolder> {

   private ArrayList<UserJobModel> userJobModel;
   private Context context;

    public JobTitleAdapter(ArrayList<UserJobModel> userJobModel, Context context) {
        this.userJobModel = userJobModel;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jobtitlesample_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UserJobModel model = userJobModel.get(position);

        holder.binding.titlesamjob.setText(model.getJobTitle());
        holder.binding.companyNdaddress.setText(model.getCompanyName() + " . " + model.getJobLocation());
        holder.binding.tview.setText(model.getEmploymentType());
        holder.binding.tview1.setText(model.getJobPosition());
        holder.binding.tview2.setText(model.getTypeOfWorkPlace());

        holder.binding.cardclickerUjOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, model.getUniqueKey(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, N_JobsDetailActivity.class);
                intent.putExtra("jobtile", model.getJobTitle());
                intent.putExtra("company", model.getCompanyName());
                intent.putExtra("location", model.getJobLocation());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("normaljobID",model.getUniqueKey());
                intent.putExtra("whoPostedJobUserID",model.getUserAuthId());
                context.startActivity(intent);
            }
        });

//
//        holder.binding.tview1.setOnClickListener(view -> {
//            view.getContext().startActivity(new Intent(view.getContext(), N_JobsDetailActivity.class));
//            context.startActivity(new Intent(context,N_JobsDetailActivity.class));

//        });
    }

    @Override
    public int getItemCount() {
        return userJobModel.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        JobtitlesampleLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = JobtitlesampleLayoutBinding.bind(itemView);

        }
    }

}