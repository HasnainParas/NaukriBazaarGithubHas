package com.appstacks.indiannaukribazaar.NewActivities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.NewActivities.Models.SelectedLanguages;
import com.appstacks.indiannaukribazaar.R;

import java.util.ArrayList;

public class SelectedLanguagesAdapter extends RecyclerView.Adapter<SelectedLanguagesAdapter.MyHolder>{
private Context context;
private ArrayList<SelectedLanguages> list;

    public SelectedLanguagesAdapter(Context context, ArrayList<SelectedLanguages> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_language_addedlayout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    SelectedLanguages data=    list.get(position);

    holder.flag.setImageDrawable(data.getFlag());
    holder.writtenLevel.setText(data.getWrittenLevel());
    holder.oralLevel.setText(data.getOralLevel());
    holder.language.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView flag;
        TextView language,oralLevel,writtenLevel;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            flag=itemView.findViewById(R.id.addedLanguageFlag);
            language=itemView.findViewById(R.id.addedLanguageName);
            oralLevel=itemView.findViewById(R.id.addedlangaugeOralLevel);
            writtenLevel=itemView.findViewById(R.id.addedlanguageWrittenlevel);
        }
    }
}
