package com.appstacks.indiannaukribazaar.profile.appreciation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddAppreciationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAppreciationActivity extends AppCompatActivity {
private ActivityAddAppreciationBinding binding;
private ProgressDialog dialog;
private DatabaseReference databaseReference;
private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddAppreciationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog= new ProgressDialog(this);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.btnBackAddappreciation.setOnClickListener(view -> {
        
        finish();
    });
        
        binding.btnSaveAddAppreciation.setOnClickListener(view -> {
            checkValidation();
        });
    }

    private void checkValidation() {

        if (binding.editTextAwardName.getText().toString().isEmpty()) {
            binding.editTextAwardName.setError("Enter award name");
        } else if (binding.edittextCategory.getText().toString().isEmpty()) {
            binding.edittextCategory.setError("Enter your category");
        } else if (binding.etEndDateappreciation.getText().toString().isEmpty()) {
            binding.etEndDateappreciation.setError("Enter date");
        } else if (binding.etTellmeAppreciation.getText().toString().isEmpty()) {
            Toast.makeText(AddAppreciationActivity.this, "Kindly provide a description", Toast.LENGTH_SHORT).show();
        } else {

            String awardName = binding.editTextAwardName.getText().toString();
            String awardCategory = binding.edittextCategory.getText().toString();
            String awardEndDate = binding.etEndDateappreciation.getText().toString();
            String awardDescription = binding.etTellmeAppreciation.getText().toString();


            uploadAppreciation(awardName, awardCategory, awardEndDate, awardDescription);
        }

    }

    private void uploadAppreciation(String awardName, String awardCategory, String awardEndDate, String awardDescription) {

        dialog.setTitle("Adding appreciation");
        dialog.setMessage("Please wait while uploading...");
        dialog.setCancelable(false);
        dialog.show();

        Appreciation appreciation = new Appreciation(awardName, awardCategory, awardEndDate, awardDescription);

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Appreciation")
                .setValue(appreciation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(AddAppreciationActivity.this, "Appreciation Added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(AddAppreciationActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AddAppreciationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}