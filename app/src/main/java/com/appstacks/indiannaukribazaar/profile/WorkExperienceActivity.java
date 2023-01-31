package com.appstacks.indiannaukribazaar.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.EditProfileActivity;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityWorkExperienceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkExperienceActivity extends AppCompatActivity {
    ActivityWorkExperienceBinding binding;
    private DatabaseReference databaseReference;
    private ProgressDialog dialog;
    private String userId;
    private String decision;
    private DatabaseReference userRef;
    private AddWorkExperience workExperience;
    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkExperienceBinding.inflate(getLayoutInflater());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setContentView(binding.getRoot());
        profileUtils= new ProfileUtils(this);
        userRef=FirebaseDatabase.getInstance().getReference("UsersProfile");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        workExperience= new AddWorkExperience();


        dialog = new ProgressDialog(this);
        try {
            decision = getIntent().getStringExtra("et");
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage());
        }
        if (decision.equals("1")) {

            binding.txtaddworkexperience.setText("Chane work experience");
            binding.btnRemoveWorkEx.setVisibility(View.VISIBLE);
            fetchWorkExperience();
        } else if (decision.equals("2")) {
            binding.txtaddworkexperience.setText("Add work experience");
            binding.btnRemoveWorkEx.setVisibility(View.GONE);


        }

        binding.btnBackAddworkEx.setOnClickListener(view -> {
            finish();
        });
        binding.btnRemoveWorkEx.setOnClickListener(view -> {
            removeWorkExperience();
        });
        binding.btnSaveAddWorkEx.setOnClickListener(view -> {
            if (binding.editTextJobTitle.getText().toString().isEmpty()) {
                binding.editTextJobTitle.setError("Enter job title");
            } else if (binding.edittextCompany.getText().toString().isEmpty()) {
                binding.edittextCompany.setError("Enter company title");
            } else if (binding.etStartDateAddWork.getText().toString().isEmpty()) {
                binding.etStartDateAddWork.setError("Start date required");
            } else if (binding.etTellmeAbout.getText().toString().isEmpty()) {
                binding.etTellmeAbout.setError("Kindly provide a description");
            } else if (binding.checkboxPosition.isChecked() && !binding.etEndDateAddWork.getText().toString().isEmpty()) {
                Toast.makeText(WorkExperienceActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
            } else if (!binding.checkboxPosition.isChecked() && binding.etEndDateAddWork.getText().toString().isEmpty()) {
                Toast.makeText(WorkExperienceActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
            } else {


                String jobTitle = binding.editTextJobTitle.getText().toString();
                String company = binding.edittextCompany.getText().toString();
                String startDate = binding.etStartDateAddWork.getText().toString();
                String endDate = binding.etEndDateAddWork.getText().toString();
                String description = binding.etTellmeAbout.getText().toString();

                boolean isPosition = binding.checkboxPosition.isChecked();
                dialog.setTitle("Uploading");
                dialog.setMessage("Please wait while uploading");
                dialog.setCancelable(false);
                dialog.show();
                uploadWorkExperience(jobTitle, company, startDate, endDate, description, userId, isPosition);
            }
        });
    }

    private void removeWorkExperience() {

        BottomSheetDialog dialog = new BottomSheetDialog(WorkExperienceActivity.this, R.style.AppBottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.undo_changes_layout, (CardView) findViewById(R.id.UndoChanges));
        dialog.setContentView(bottomsheetView);
        dialog.show();
        TextView title = bottomsheetView.findViewById(R.id.textTitle_dialog);
        TextView subtitle = bottomsheetView.findViewById(R.id.textSubtitle_dialog);
        Button confirm = bottomsheetView.findViewById(R.id.btnContinue_dialog);
        Button cancel = bottomsheetView.findViewById(R.id.btnUndo_dialog);
        confirm.setText("Yes");
        cancel.setText("Cancel");
        cancel.setOnClickListener(view16 -> dialog.dismiss());
        title.setText("Remove Work Experience?");
        subtitle.setText("Are you sure you want to delete this work experience?");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(getString(R.string.user_profile)).child(userId).child("WorkExperience").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(WorkExperienceActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(WorkExperienceActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WorkExperienceActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void fetchWorkExperience() {
        userRef.child(userId).child("WorkExperience").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    workExperience = snapshot.getValue(AddWorkExperience.class);
                    binding.editTextJobTitle.setText(workExperience.getJobTitle());

                    assert workExperience != null;
                    binding.edittextCompany.setText(workExperience.getCompany());
                    binding.etStartDateAddWork.setText(workExperience.getStartDate());
                    binding.etEndDateAddWork.setText(workExperience.getEndDate());
                    binding.checkboxPosition.setChecked(workExperience.isPositionNow());
                    binding.etTellmeAbout.setText(workExperience.getDescription());

                    // saving work experience to shared preferences
                    profileUtils.saveWorkExperience(workExperience);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WorkExperienceActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadWorkExperience(String jobTitle, String company, String startDate, String endDate, String description, String userId, boolean isPosition) {

        AddWorkExperience data = new AddWorkExperience(jobTitle, company, startDate, endDate, description, userId, isPosition);

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("WorkExperience").setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {

                            dialog.dismiss();
                            Toast.makeText(WorkExperienceActivity.this, "Work experience added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(WorkExperienceActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}