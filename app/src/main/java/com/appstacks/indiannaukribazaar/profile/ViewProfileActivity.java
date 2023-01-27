package com.appstacks.indiannaukribazaar.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityViewProfileBinding;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity {
    ActivityViewProfileBinding binding;
    private ArrayList<Feedback> list;
    private FeedbackAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        list= new ArrayList<>();
        list.add(new Feedback("5","17 Feb","Android app development","Mehboob is such a nice guy he did very well and amazing. i will highly recomend him","$ 1,300","$45/Hr","74 Hrs"));
        list.add(new Feedback("5","17 Feb","Android app development","Mehboob is such a nice guy he did very well and amazing. i will highly recomend him","$ 1,300","$45/Hr","74 Hrs"));
        list.add(new Feedback("5","17 Feb","Android app development","Mehboob is such a nice guy he did very well and amazing. i will highly recomend him","$ 1,300","$45/Hr","74 Hrs"));

        adapter= new FeedbackAdapter(this,list);
        binding.recyclerFeedBack.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        binding.recyclerFeedBack.setAdapter(adapter);

    }
}