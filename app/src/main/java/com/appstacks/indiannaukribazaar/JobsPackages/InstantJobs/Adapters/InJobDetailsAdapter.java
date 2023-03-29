package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.Adapters;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.InstantAddJobsModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.InjobdetailSampleLayoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class InJobDetailsAdapter extends RecyclerView.Adapter<InJobDetailsAdapter.viewHolder> {
    ArrayList<InstantAddJobsModel> arrayList;
    Context context;
    private DatabaseReference reference;
    private ArrayAdapter<String> adapter;
    long creationTimestamp;


    public InJobDetailsAdapter(ArrayList<InstantAddJobsModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.injobdetail_sample_layout, parent, false);
        return new InJobDetailsAdapter.viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        InstantAddJobsModel model = arrayList.get(position);
        reference = FirebaseDatabase.getInstance().getReference("InstantJobs");

        holder.binding.injobDetailTitle.setText(model.getInJobTitle());
        holder.binding.injobdetailCom.setText(model.getInJobCompany());
        holder.binding.injobdetailDesCription.setText(model.getInJobDes());
        holder.binding.projectBudgetDetailsInJob.setText(model.getInJobBudget());

//        holder.binding.injobdetailDate.setText("Posted on " + model.getPostedDate());

        String timeago = calculateTimeAgo(model.getPostedDate());
        holder.binding.injobdetailDate.setText(timeago);


//        String timeaaaage = calculate(model.getPostedDate());
//        holder.binding.injobdetailDate.setText(timeaaaage);

//        try {
//            creationTimestamp = Long.parseUnsignedLong(model.getPostedDate());
//        } catch (NumberFormatException e) {
//            Toast.makeText(context, e.getLocalizedMessage() + " ", Toast.LENGTH_SHORT).show();
//        }
//
//        long currentTimestamp = System.currentTimeMillis();
//        long diffInMillis = currentTimestamp - creationTimestamp;
//
//
//
//        int days = (int) TimeUnit.MILLISECONDS.toDays(diffInMillis);
//        int hours = (int) TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
//        int months = days / 30;

//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(creationTimestamp * 1000); // Multiply by 1000 to convert seconds to milliseconds
//
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int days = calendar.get(Calendar.DAY_OF_MONTH);
//        int months = calendar.get(Calendar.MONTH) + 1;

//
//        String accountCreationDate = "";
//        if (months > 0) {
//            accountCreationDate = months + " month" + (months == 1 ? "" : "s") + " ago";
//        } else if (days > 0) {
//            accountCreationDate = days + " day" + (days == 1 ? "" : "s") + " ago";
//        } else if (hours > 0) {
//            accountCreationDate = hours + " hour" + (hours == 1 ? "" : "s") + " ago";
//        } else {
//            accountCreationDate = "Just now";
//        }
//
//        holder.binding.injobdetailDate.setText(accountCreationDate);
//





        reference.child(model.getInJobID()).child("inSkills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    ArrayList<String> list1 = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list1.add(obj);
                        holder.binding.injobdetailsList.setVisibility(View.VISIBLE);
                        holder.binding.injoblistloader.setVisibility(View.GONE);
                        holder.binding.injoblistloadingTv.setVisibility(View.GONE);
                        holder.binding.injobdetailsList.setVerticalScrollBarEnabled(true);

                    }

                    adapter = new ArrayAdapter<String>(context.getApplicationContext(), R.layout.layoutinjobdetailskill, R.id.injobdetailskillTv, list1);

                    holder.binding.injobdetailsList.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String calculateTimeAgo(String postedDate) {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
            long time = sdf.parse(postedDate).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        InjobdetailSampleLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = InjobdetailSampleLayoutBinding.bind(itemView);

        }
    }


}
