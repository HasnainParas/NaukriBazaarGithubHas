package com.appstacks.indiannaukribazaar.NewActivities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.ProfileModels.SelectedLanguages;
import com.appstacks.indiannaukribazaar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.viewHolder> {
    private ArrayList<SelectedLanguages> list;
    private Context context;

    public LanguagesAdapter(ArrayList<SelectedLanguages> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_language_addedlayout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SelectedLanguages data = list.get(position);
        holder.oralLevel.setText(data.getOralLevel());
        holder.writtenLevel.setText(data.getWrittenLevel());
        holder.languageName.setText(data.getName());
        Picasso.get().load(data.getFlag()).into(holder.flag);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
          ImageView flag;
          TextView languageName,writtenLevel,oralLevel;
          ImageView btnDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            flag=itemView.findViewById(R.id.addedLanguageFlag);
            languageName=itemView.findViewById(R.id.addedLanguageName);
            writtenLevel=itemView.findViewById(R.id.addedlanguageWrittenlevel);
            oralLevel=itemView.findViewById(R.id.addedlangaugeOralLevel);
            btnDelete=itemView.findViewById(R.id.addedLanguageDelete);
        }
    }
}
