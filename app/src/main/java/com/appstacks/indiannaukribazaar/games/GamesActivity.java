package com.appstacks.indiannaukribazaar.games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.appstacks.indiannaukribazaar.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class GamesActivity extends AppCompatActivity {
    private ArrayList<SliderItems> list;
    private ArrayList<GameItems> gameItemsArrayList;
    private SliderView sliderView;
    private GamesSliderAdapter adapter;
    private GameAdapter gameAdapter;
    private RecyclerView gameRecyclerView;
    private TextView txtMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        sliderView = findViewById(R.id.imageSlider);

        initViews();
        txtMore.setOnClickListener(view -> {
            startActivity(new Intent(GamesActivity.this, AllGamesActivity.class));
        });
        sliderSet();
        initializeGames();


    }

    private void initializeGames() {

        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/B1fSpMkP51m/thumb.png", "https://www.gamezop.com/g/B1fSpMkP51m?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BkdJhTX50B/thumb.png", "https://www.gamezop.com/g/BkdJhTX50B?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BJ9bvzIKdJl/thumb.png", "https://www.gamezop.com/g/BJ9bvzIKdJl?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/H1AN6fkwqJ7/thumb.png", "https://www.gamezop.com/g/H1AN6fkwqJ7?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/HJP4afkvqJQ/thumb.png", "https://www.gamezop.com/g/HJP4afkvqJQ?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/B1JBaM1D9y7/thumb.png", "https://www.gamezop.com/g/B1JBaM1D9y7?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/BkzmafyPqJm/thumb.png", "https://www.gamezop.com/g/BkzmafyPqJm?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/SkJf58Ouf0/thumb.png", "https://www.gamezop.com/g/SkJf58Ouf0?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/Skz4pzkDqyX/thumb.png", "https://www.gamezop.com/g/Skz4pzkDqyX?id=4805"));
        gameItemsArrayList.add(new GameItems("https://static.gamezop.com/SkQwnwnbK/thumb.png", "https://www.gamezop.com/g/SkQwnwnbK?id=4805"));




        gameAdapter = new GameAdapter(this, gameItemsArrayList);

        gameRecyclerView.setAdapter(gameAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        gameRecyclerView.setLayoutManager(layoutManager);


    }

    private void sliderSet() {

        list.add(new SliderItems(R.drawable.ic_amazon));
        list.add(new SliderItems(R.drawable.document));
        list.add(new SliderItems(R.drawable.badge_shape));

        adapter = new GamesSliderAdapter(this, list);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void initViews() {

        gameRecyclerView = findViewById(R.id.games_RecyclerView);
        txtMore = findViewById(R.id.txtMore);
        list = new ArrayList<>();
        gameItemsArrayList = new ArrayList<>();
    }
}