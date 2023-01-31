package com.appstacks.indiannaukribazaar.profile.Lanuages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.R;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageHolder>{
private ArrayList<SelectedLanguages> list;
private Context context;

    public LanguageAdapter(ArrayList<SelectedLanguages> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public LanguageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_language_list_1,parent,false);
        return new LanguageHolder(view);
    }
    public void filterList(ArrayList<SelectedLanguages> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageHolder holder, int position) {

    SelectedLanguages data=    list.get(position);
    holder.flag.setImageResource(data.getDrawable());
    holder.language.setText(data.getName());

    holder.itemView.setOnClickListener(view -> {
        Intent intent = new Intent(context,AddCompleteLanuageActivity.class);
        intent.putExtra("flag",data.getDrawable());
        intent.putExtra("lang",data.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LanguageHolder  extends RecyclerView.ViewHolder{
          private ImageView flag;
          private TextView language;
        public LanguageHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag);
            language=itemView.findViewById(R.id.language);
        }
    }
}
//        extends ArrayAdapter<String> {
//    private final Activity context;
//    private final String[] language;
//    private final int[] image;
//
//    public LanguageAdapter(Activity context, String[] language,int[] image) {
//        super(context, R.layout.sample_language_list_1,language);
//        this.context=context;
//        this.language=language;
//        this.image=image;
//    }
//    public View getView (int position, View view, ViewGroup viewGroup){
//        LayoutInflater inflater=context.getLayoutInflater();
//        @SuppressLint("ViewHolder") View rowView=inflater.inflate(R.layout.sample_language_list_1,null,true);
//        TextView titleText = (TextView) rowView.findViewById(R.id.language);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.flag);
//
//        titleText.setText(language[position]);
//        imageView.setImageResource(image[position]);
//
//
//        return rowView;
//    }
//}
