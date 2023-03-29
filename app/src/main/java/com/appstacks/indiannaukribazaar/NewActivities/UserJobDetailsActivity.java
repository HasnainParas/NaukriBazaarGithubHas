package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityUserJobDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserJobDetailsActivity extends AppCompatActivity {

    private ActivityUserJobDetailsBinding binding;
    private DatabaseReference userJobRef, userRef;
    private String currentUser;
    private PersonalInformationModel model;
    private UserJobModel userJobModel;

    private String username, userAddress;
    private String uniqueKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uniqueKey = getIntent().getStringExtra("unikey");

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        userRef = FirebaseDatabase.getInstance().getReference();

binding.btnFindJobs.setOnClickListener(view -> {
    onBackPressed();
});
        // UserProfile
        userRef.child("UsersInfo").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    model = snapshot.getValue(PersonalInformationModel.class);
                    username = model.getFirstName() + " " + model.getLastName();
                    userAddress = model.getUserAddress();
                    Toast.makeText(UserJobDetailsActivity.this, username, Toast.LENGTH_SHORT).show();
                    binding.usernameuser.setText(username);
                    binding.useraddressuser.setText(userAddress);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserJobDetailsActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });


        // Job Data
        userJobRef.child(currentUser).child(uniqueKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userJobModel = snapshot.getValue(UserJobModel.class);

                    binding.etDescriptionuser.setText(userJobModel.getDescription());
                    binding.titleTextView.setText(userJobModel.getJobTitle());
                    binding.jobCompany.setText("Job vacancies from " + userJobModel.getCompanyName() + " company");
                    binding.userAddress4job.setText(userJobModel.getJobLocation());
                    binding.userWorkPlaceTv.setText(". " + userJobModel.getTypeOfWorkPlace());


                    Toast.makeText(UserJobDetailsActivity.this, "Yes Existed...", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(UserJobDetailsActivity.this, FindJobsActivity.class);
        startActivity(intent);
    }
}