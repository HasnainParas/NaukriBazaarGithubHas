package com.appstacks.indiannaukribazaar.NewActivities.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.NewActivities.AddJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.adapter.AdapterTopicPick;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.viewHolder> implements Filterable{


    ArrayList<CompanyModel>list;
    Context context;
    ArrayList<CompanyModel>backup;

    public CompanyAdapter(ArrayList<CompanyModel> list, Context context) {
        this.list = list;
        this.context = context;

        backup =new ArrayList<>(list);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      View view =  LayoutInflater.from(context).inflate(R.layout.sample_company,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        CompanyModel model = list.get(position);

        holder.companyLogo.setImageResource(model.getImage());
        holder.title.setText(model.getTitle());
        holder.internet.setText(model.getInternet());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyWord) {

            ArrayList<CompanyModel>filterData = new ArrayList<>();

            if (keyWord.toString().isEmpty()){

                filterData.addAll(backup);
            }
            else {

                for (CompanyModel obj: backup){

                    if (obj.getTitle().toString().toLowerCase()
                            .contains(keyWord.toString().toLowerCase()))filterData.add(obj);

                }

            }
            FilterResults results = new FilterResults();
            results.values = filterData;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((ArrayList<CompanyModel>)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class viewHolder extends RecyclerView.ViewHolder{
     TextView title,internet;
     ImageView companyLogo;



     public viewHolder(@NonNull View itemView) {
         super(itemView);

         companyLogo = itemView.findViewById(R.id.imgGoogle);
         title = itemView.findViewById(R.id.companyTitle);
         internet = itemView.findViewById(R.id.internet);




     }
 }


}