package com.appstacks.indiannaukribazaar.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAllGamesBinding;

import java.util.ArrayList;

public class AllGamesActivity extends AppCompatActivity {

    private ArrayList<GameItems> allGamesList;
    private ActivityAllGamesBinding binding;
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllGamesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        allGamesList = GamesList.allGames();


        adapter = new GameAdapter(this, allGamesList);
        binding.allGamesRecycler.setAdapter(adapter);
        binding.allGamesRecycler.setLayoutManager(new GridLayoutManager(this,5));


    }
}