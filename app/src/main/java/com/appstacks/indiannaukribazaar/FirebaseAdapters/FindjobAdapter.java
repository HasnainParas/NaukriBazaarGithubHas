package com.appstacks.indiannaukribazaar.FirebaseAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.FirebaseModels.FindJobModel;
import com.appstacks.indiannaukribazaar.R;


import java.util.ArrayList;

public class FindjobAdapter extends RecyclerView.Adapter<FindjobAdapter.viewHoldr> {

    ArrayList<FindJobModel>list;
    Context context;

    public FindjobAdapter(ArrayList<FindJobModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHoldr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_sample_find_jobs,parent,false);

        return new viewHoldr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHoldr holder, int position) {


        FindJobModel model = list.get(position);

        holder.jobimg2.setImageResource(model.getJobImg2());
        holder.jobTitle2.setText(model.getJobTitle2());
        holder.address.setText(model.getAddress());
        holder.salary2.setText(model.getSalary2());
        holder.jobType2.setText(model.getJobType2());
        holder.time2.setText(model.getTime2());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHoldr extends RecyclerView.ViewHolder{

    ImageView jobimg2;
    TextView jobTitle2,address,salary2,jobType2,time2;

    public viewHoldr(@NonNull View itemView) {
        super(itemView);

        jobimg2 = itemView.findViewById(R.id.jobimg2);
        jobTitle2= itemView.findViewById(R.id.jobTitle2);
        address = itemView.findViewById(R.id.txt_workplace);
        salary2 = itemView.findViewById(R.id.salary2);
        jobType2 = itemView.findViewById(R.id.jobtype2);
        time2 =  itemView.findViewById(R.id.time2);


    }
}


}
