package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.EditProfileActivity;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityEditBioBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditBioActivity extends AppCompatActivity {
    ActivityEditBioBinding binding;
    private DatabaseReference databaseReference, userRef;
    private String userId;
    private AboutMeDescription aboutme;
    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        profileUtils = new ProfileUtils(this);
        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");

        aboutme = new AboutMeDescription();
        binding.btnSaveAboutMe.setOnClickListener(view -> {


            if (binding.etTellmeAbout.getText().toString().isEmpty()) {
                binding.etTellmeAbout.setError("Kindly enter about yourself");
            } else {

                UploadAboutMe(binding.etTellmeAbout.getText().toString());
            }
        });

        binding.btnBackAboutMe.setOnClickListener(view -> {
            finish();
        });

        fetchAboutMe();


    }

    private void fetchAboutMe() {
        userRef.child(userId).child("AboutMe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("aboutMeDescription").exists()) {

                    aboutme = snapshot.getValue(AboutMeDescription.class);
                    assert aboutme != null;

                    binding.etTellmeAbout.setText(aboutme.getAboutMeDescription());
                } else {


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditBioActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadAboutMe(String aboutMeDescription) {

        AboutMeDescription data = new AboutMeDescription(aboutMeDescription);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("AboutMe").setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            profileUtils.saveUserBio(aboutMeDescription);
                            Toast.makeText(EditBioActivity.this, "Description Added", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(EditBioActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}