package com.appstacks.indiannaukribazaar.games;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private Context context;
    private ArrayList<GameItems> list;

    public GameAdapter(Context context, ArrayList<GameItems> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items_games_layout, parent, false);
        return new GameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {

        GameItems items = list.get(position);
        Glide.with(context).load(items.getGameBanner())
                .into(holder.banner);

        holder.btnPlay.setOnClickListener(view -> {

            Intent intent = new Intent(context,FullGameActivity.class);
            intent.putExtra("url",items.getGameUrl());
            context.startActivity(intent);
            //Toast.makeText(context, "" + items.getGameUrl(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GameHolder extends RecyclerView.ViewHolder {
        private ImageView banner;
        private Button btnPlay;

        public GameHolder(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.game_banner);
            btnPlay = itemView.findViewById(R.id.btn_play);
        }
    }
}
