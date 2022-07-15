package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddPostsBinding;

public class AddPostsActivity extends AppCompatActivity {

    ActivityAddPostsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.icBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }


        });

        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddJobsActivity.class));
            }
        });


    }
}