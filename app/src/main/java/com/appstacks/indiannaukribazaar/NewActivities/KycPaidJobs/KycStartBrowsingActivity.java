package com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appstacks.indiannaukribazaar.Activities.ActivityMain;
import com.appstacks.indiannaukribazaar.R;

public class KycStartBrowsingActivity extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_start_browsing);

        btnStart = findViewById(R.id.buttonstart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                startActivity(new Intent(
                        KycStartBrowsingActivity.this,StartProfileVerification.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}