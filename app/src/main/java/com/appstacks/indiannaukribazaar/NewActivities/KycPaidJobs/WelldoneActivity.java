package com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.appstacks.indiannaukribazaar.NewActivities.SpinWheelActivity;
import com.appstacks.indiannaukribazaar.NewFragments.FindJobsFragments;
import com.appstacks.indiannaukribazaar.R;

public class WelldoneActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welldone);

        Button goBackBTn = findViewById(R.id.goBackBtn);


//        goBackBTn.setOnClickListener();


        goBackBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelldoneActivity.this,SpinWheelActivity.class));
                finishAffinity();
            }
        });
    }



//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.welldoneContainer, fragment);
//        transaction.addToBackStack(null);
//        transaction.commitAllowingStateLoss();
//
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}