package com.appstacks.indiannaukribazaar.games;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appstacks.indiannaukribazaar.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class GamesSliderAdapter extends SliderViewAdapter<GamesSliderAdapter.MyViewHolder>{
private Context context;
private ArrayList<SliderItems > list;


    public GamesSliderAdapter(Context context, ArrayList<SliderItems> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(R.layout.games_slider_layout,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        SliderItems items=       list.get(position);

       // viewHolder.imageView.setImageResource(items.getImage());
 Glide.with(context)
         .load(items.getImage())
         .fitCenter()
         .into(viewHolder.imageView)
         ;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_auto_image_slider);

        }
    }
}
