package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.ProfileModels.Education;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddEducationBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
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

public class AddEducationActivity extends AppCompatActivity {
    private ActivityAddEducationBinding binding;
    private ProfileUtils profileUtils;
    private String education;
    private DatabaseReference databaseReference;
    private String userId;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dialog = new ProgressDialog(this);
        readFromDatabase();

        education = getIntent().getStringExtra("edu");

        if (education.equals("2")) {
            binding.btnRemoveEducation.setVisibility(View.GONE);
        }
        if (education.equals("1")) {
            binding.btnRemoveEducation.setVisibility(View.VISIBLE);

        }

        binding.edittextInstituteName.setOnClickListener(view -> {

            startActivity(new Intent(AddEducationActivity.this, InstituteNameActivity.class));

        });
        binding.btnBackAddEduca.setOnClickListener(view -> {
            finish();
        });


        binding.etFieldofStudy.setOnClickListener(view -> {
            startActivity(new Intent(AddEducationActivity.this, FieldOfStudyActivity.class));
        });

        binding.editlevelofEduca.setOnClickListener(view -> {
            startActivity(new Intent(AddEducationActivity.this, LevelOfEducationActivity.class));
        });
        binding.btnSaveAddEduca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.editlevelofEduca.getText().toString().isEmpty()) {
                    binding.editlevelofEduca.setError("Add your level of education by double tapping");
                } else if (binding.edittextInstituteName.getText().toString().isEmpty()) {
                    binding.edittextInstituteName.setError("Add your institution by double tapping");
                } else if (binding.etStartDateAddEdca.getText().toString().isEmpty()) {
                    binding.etStartDateAddEdca.setError("Enter your starting date");
                } else if (binding.etTellmeAbout.getText().toString().isEmpty()) {
                    binding.etTellmeAbout.setError("Kindly describe about your education");
                } else if (binding.checkboxPosition.isChecked() && !binding.etEndDateAddWork.getText().toString().isEmpty()) {
                    Toast.makeText(AddEducationActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
                } else if (!binding.checkboxPosition.isChecked() && binding.etEndDateAddWork.getText().toString().isEmpty()) {
                    Toast.makeText(AddEducationActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
                } else {

                    dialog.setTitle("Uploading");
                    dialog.setMessage("Please wait while uploading");
                    dialog.setCancelable(false);
                    dialog.show();
                    String levelOfEducation = binding.editlevelofEduca.getText().toString();
                    String instituteName = binding.edittextInstituteName.getText().toString();
                    String educationFieldOfStudy = binding.etFieldofStudy.getText().toString();
                    String educationStartDate = binding.etStartDateAddEdca.getText().toString();
                    String educationEndDate = binding.etEndDateAddWork.getText().toString();
                    String educationDescription = binding.etTellmeAbout.getText().toString();
                    boolean isPosition = binding.checkboxPosition.isChecked();

                    uploadEducation(levelOfEducation, instituteName, educationFieldOfStudy, educationStartDate, educationEndDate, educationDescription, isPosition);

                }
            }
        });

        binding.btnRemoveEducation.setOnClickListener(view -> {
            BottomSheetDialog dialog = new BottomSheetDialog(AddEducationActivity.this, R.style.AppBottomSheetDialogTheme);
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
            title.setText("Remove Education?");
            subtitle.setText("Are you sure you want to delete this Education?");

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child(getString(R.string.user_profile)).child(userId).child("Education").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete() && task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(AddEducationActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
                                finish();
                                updateValues();
                            } else {
                                Toast.makeText(AddEducationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddEducationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });
    }

    private void updateValues() {
        binding.editlevelofEduca.setText("");
        binding.edittextInstituteName.setText("");
        binding.etFieldofStudy.setText("");
        binding.etStartDateAddEdca.setText("");
        binding.etEndDateAddWork.setText("");
        binding.checkboxPosition.setChecked(false);
        binding.etTellmeAbout.setText("");
    }

    private void readFromDatabase() {
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Education").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Education education = snapshot.getValue(Education.class);
                    binding.editlevelofEduca.setText(education.getLevelOfEducation());
                    binding.edittextInstituteName.setText(education.getInstituteName());
                    binding.etFieldofStudy.setText(education.getFieldOfStudy());
                    binding.etStartDateAddEdca.setText(education.getStartDate());
                    binding.etEndDateAddWork.setText(education.getEndDate());
                    binding.checkboxPosition.setChecked(education.isPosition());
                    binding.etTellmeAbout.setText(education.getDescription());

                } else {
                    Toast.makeText(AddEducationActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddEducationActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.editlevelofEduca.setText(profileUtils.fetchLevelOfEducation());
        binding.edittextInstituteName.setText(profileUtils.fetchInstituteName());
        binding.etFieldofStudy.setText(profileUtils.fetchFieldStudy());

    }

    private void uploadEducation(String levelOfEducation, String instituteName, String filedOfStudy, String educationStartDate, String educationEndDate, String educationDescription, boolean isPosition) {
        Education education = new Education(levelOfEducation, instituteName, filedOfStudy, educationStartDate, educationEndDate, educationDescription, isPosition);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Education").setValue(education)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            //  dialog.dismiss();
//                            profileUtils.saveEducation(education);
                            Toast.makeText(AddEducationActivity.this, "Education Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // dialog.dismiss();
                        Toast.makeText(AddEducationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}