package com.appstacks.indiannaukribazaar.profile.Lanuages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddCompleteLanuageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

public class AddCompleteLanuageActivity extends AppCompatActivity {
    private ActivityAddCompleteLanuageBinding binding;
    private RadioGroup radioGroup;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCompleteLanuageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference();
        Dialog dialog = new Dialog(AddCompleteLanuageActivity.this);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        dialog.setContentView(R.layout.level_layout);
        progressDialog = new ProgressDialog(this);
        radioGroup = dialog.findViewById(R.id.radioGroup_level);
        int flag = getIntent().getIntExtra("flag", 0);
        String language = getIntent().getStringExtra("lang");
        binding.btnBackLanguageDetails.setOnClickListener(view -> {
            finish();
        });
        binding.flagToSet.setImageResource(flag);
        binding.txtLanguageToSet.setText(language);

        binding.txtOral.setOnClickListener(view -> {

            bottomDialogShow("oral");


        });
        binding.txtWritten.setOnClickListener(view -> {
            bottomDialogShow("write");
        });


        binding.btnSaveLanguageDetails.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                Drawable drawable = binding.flagToSet.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                String language = binding.txtLanguageToSet.getText().toString();
                if (binding.levelToSet.getText().equals("Choose your speaking skill level")) {
                    Toast.makeText(AddCompleteLanuageActivity.this, "Select language's oral level", Toast.LENGTH_SHORT).show();
                } else if (binding.levelToSet.getText().equals("Choose your writing skill level")) {
                    Toast.makeText(AddCompleteLanuageActivity.this, "Select language's written level", Toast.LENGTH_SHORT).show();
                } else {
                    String oralLevel = binding.levelToSet.getText().toString();
                    String writtenLevel = binding.writtenLevel.getText().toString();
                    boolean isfirst= binding.btnFirstLanguage.isChecked();
                    uploadFlagOfLanguage(bitmap, language, oralLevel, writtenLevel,isfirst);


                }
            }
        });


    }

    private void uploadFlagOfLanguage(Bitmap bitmap, String language, String oralLevel, String writtenLevel,boolean isFirst) {
        progressDialog.setTitle("Adding Language");
        progressDialog.setMessage("Please wait uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String uID = UUID.randomUUID().toString();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child(getString(R.string.user_profile)).child("Languages").child(userId).child("LanguageFlag" + uID);
        UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String flagDownloadUrl = String.valueOf(uri);
                                    uploadLangauge(language, flagDownloadUrl, oralLevel, writtenLevel,isFirst,uID);
                                }
                            });
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddCompleteLanuageActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddCompleteLanuageActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void uploadLangauge(String language, String flagDownloadUrl, String levelOfOral, String levelOfWritten,boolean isFirst,String uID) {


        SelectedLanguages selectedLanguages = new SelectedLanguages(language, flagDownloadUrl, levelOfOral, levelOfWritten, uID, userId,isFirst);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Languages").child(uID)
                .setValue(selectedLanguages)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(AddCompleteLanuageActivity.this, "Language Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddCompleteLanuageActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void bottomDialogShow(String which) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.level_layout);

        radioGroup = bottomSheetDialog.findViewById(R.id.radioGroup_level);
        assert radioGroup != null;
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton radioButton = radioGroup.findViewById(i);
            Toast.makeText(AddCompleteLanuageActivity.this, "" + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
            //TODO    level of oral


            String level = radioButton.getText().toString();
            if (which.equals("oral"))
                binding.levelToSet.setText(level);
            else
                binding.writtenLevel.setText(level);
            bottomSheetDialog.dismiss();

        });
        bottomSheetDialog.show();
    }

}