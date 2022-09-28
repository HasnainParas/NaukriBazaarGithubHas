package com.appstacks.indiannaukribazaar.FirebaseAdapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appstacks.indiannaukribazaar.R;

public class LanguageAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] language;
    private final int[] image;

    public LanguageAdapter(Activity context, String[] language,int[] image) {
        super(context, R.layout.sample_language_list_1,language);
        this.context=context;
        this.language=language;
        this.image=image;
    }
    public View getView (int position, View view, ViewGroup viewGroup){
        LayoutInflater inflater=context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView=inflater.inflate(R.layout.sample_language_list_1,null,true);
        TextView titleText = (TextView) rowView.findViewById(R.id.language);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.flag);

        titleText.setText(language[position]);
        imageView.setImageResource(image[position]);


        return rowView;
    }
}
