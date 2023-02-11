package com.appstacks.indiannaukribazaar.profile.appreciation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityEditAppreciationBinding;

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

public class EditAppreciationActivity extends AppCompatActivity {
    private ActivityEditAppreciationBinding binding;
    private ProgressDialog dialog;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAppreciationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));

        fetchAppreciation();
        binding.btnBackAddappreciation.setOnClickListener(view -> {
            finish();
        });


        binding.btnSaveAddAppreciation.setOnClickListener(view -> {

            BottomSheetDialog dialog = new BottomSheetDialog(EditAppreciationActivity.this, R.style.AppBottomSheetDialogTheme);
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
            title.setText("Undo Changes?");
            subtitle.setText("Are you sure you want to change what you entered?");

            confirm.setOnClickListener(view1 -> {
                checkValidation();
                dialog.dismiss();
            });

        });

        binding.btnRemoveAppreciation.setOnClickListener(view -> {
            removeAppreciation();
        });

    }

    private void fetchAppreciation() {

        databaseReference.child(userId).child("Appreciation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Appreciation appreciation = snapshot.getValue(Appreciation.class);

                    assert appreciation != null;
                    binding.editTextAwardName.setText(appreciation.getAwardName());
                    binding.edittextCategory.setText(appreciation.getAwardCategory());
                    binding.etEndDateappreciation.setText(appreciation.getAwardEndDate());
                    binding.etTellmeAppreciation.setText(appreciation.getAwardDescription());


                } else {
                    Toast.makeText(EditAppreciationActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditAppreciationActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeAppreciation() {
        BottomSheetDialog dialog = new BottomSheetDialog(EditAppreciationActivity.this, R.style.AppBottomSheetDialogTheme);
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
        title.setText("Remove Appreciation?");
        subtitle.setText("Are you sure you want to delete this award?");

        confirm.setOnClickListener(view -> {
            databaseReference.child(userId).child("Appreciation").removeValue().addOnCompleteListener(task -> {
                if (task.isComplete() && task.isSuccessful()) {

                    Toast.makeText(EditAppreciationActivity.this, "Appreciation removed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
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
            Toast.makeText(EditAppreciationActivity.this, "Kindly provide a description", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(EditAppreciationActivity.this, "Appreciation Added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(EditAppreciationActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(EditAppreciationActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}