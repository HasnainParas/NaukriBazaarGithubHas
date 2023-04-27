package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityJobStatusBinding;

public class JobStatusActivity extends AppCompatActivity {

    ActivityJobStatusBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityJobStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadFragment(new ActiveFragment());


        binding.activeBtnLayout.setOnClickListener(v -> {
            binding.activeBtnLayout.setBackgroundResource(R.drawable.activebtn_bg);
            binding.completedBtnLayout.setBackgroundResource(R.drawable.completedbtn_bg);
            binding.activeTextview.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.completedTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
            loadFragment(new ActiveFragment());
        });
        binding.completedBtnLayout.setOnClickListener(v -> {
            binding.completedBtnLayout.setBackgroundResource(R.drawable.activebtn_bg);
            binding.activeBtnLayout.setBackgroundResource(R.drawable.completedbtn_bg);
            binding.completedTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.activeTextview.setTextColor(ContextCompat.getColor(this, R.color.black));
            loadFragment(new CompletedFragment());
        });

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.statusFramelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}