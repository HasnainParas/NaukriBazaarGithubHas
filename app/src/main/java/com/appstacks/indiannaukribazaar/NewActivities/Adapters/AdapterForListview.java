package com.appstacks.indiannaukribazaar.NewActivities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.adapter.AdapterTopicPick;

import java.util.List;

public class AdapterForListview extends RecyclerView.Adapter<AdapterForListview.viewHolderA> {
    private List<String> mData;
    Context context;

    public AdapterForListview(List<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderA onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layoutinjobdetailskill, parent, false);
        return new viewHolderA(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderA holder, int position) {
        holder.textView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class viewHolderA extends AdapterTopicPick.ViewHolder {
        TextView textView;
        public viewHolderA(View v) {
            super(v);
            textView = v.findViewById(R.id.injobdetailskillTv);
        }
    }
}
