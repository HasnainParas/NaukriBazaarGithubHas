package com.appstacks.indiannaukribazaar.NewActivities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.profile.skills.Skills;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Skills> list;

    LayoutInflater inflater;


    public GridAdapter(Context context, ArrayList<Skills> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){

            convertView = inflater.inflate(R.layout.grid_item,null);

        }

        TextView textView = convertView.findViewById(R.id.tvidd);

        textView.setText(list.get(position).getSkill());

        return convertView;
    }
}