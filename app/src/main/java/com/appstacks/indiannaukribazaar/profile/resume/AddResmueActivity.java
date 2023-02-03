package com.appstacks.indiannaukribazaar.profile.resume;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddResmueBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddResmueActivity extends AppCompatActivity {
    private ActivityAddResmueBinding binding;
    private static final int REQUEST_CODE = 1;
    private Uri uri;
    private String PdfsizeInString, pdfDate, pdfName;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String userId;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddResmueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));
        storageReference = FirebaseStorage.getInstance().getReference(getString(R.string.user_profile));
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dialog = new ProgressDialog(this);

        binding.btnBackAddResume.setOnClickListener(view -> {
            finish();
        });

        binding.cardUploadResume.setOnClickListener(view -> {
            pickPdf();
        });

        binding.btnSaveAddResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null) {
                    uploadPdf();
                } else {
                    Toast.makeText(AddResmueActivity.this, "Select your resume in PDF format", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.removeResume.setOnClickListener(view -> {
            uri = null;
            binding.resumeInfo.setText("");
            binding.resumeName.setText("");
            binding.cardUploadResume.setVisibility(View.VISIBLE);
            binding.cardResumeAdded.setVisibility(View.GONE);
        });
    }

    private void pickPdf() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // We will be redirected to choose pdf
        galleryIntent.setType("application/pdf");
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            binding.cardUploadResume.setVisibility(View.GONE);
            binding.cardResumeAdded.setVisibility(View.VISIBLE);
            uri = data.getData();
            String stringUri = uri.toString();

            if (stringUri.startsWith("content://")) {
                Cursor cursor = null;
                try {


                    cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                        long size = cursor.getLong(sizeIndex);
                        PdfsizeInString = getReadableFileSize(size);
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        @SuppressLint("SimpleDateFormat")
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                        pdfDate = df.format(Calendar.getInstance().getTime());
                        Toast.makeText(this, "selected " + pdfName + " adn size is " + PdfsizeInString, Toast.LENGTH_SHORT).show();
                        binding.resumeInfo.setText(pdfDate);
                        binding.resumeName.setText(pdfName);


                    }
                } finally {

                    cursor.close();
                }

            }
        }
    }

    public static String getReadableFileSize(long size) {
        final int BYTES_IN_KILOBYTES = 1024;
        final java.text.DecimalFormat dec = new java.text.DecimalFormat("###.#");
        final String KILOBYTES = " KB";
        final String MEGABYTES = " MB";
        final String GIGABYTES = " GB";
        float fileSize = 0;
        String suffix = KILOBYTES;

        if (size > BYTES_IN_KILOBYTES) {
            fileSize = size / BYTES_IN_KILOBYTES;
            if (fileSize > BYTES_IN_KILOBYTES) {
                fileSize = fileSize / BYTES_IN_KILOBYTES;
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES;
                    suffix = GIGABYTES;
                } else {
                    suffix = MEGABYTES;
                }
            }
        }
        return String.valueOf(dec.format(fileSize) + suffix);
    }


    private void uploadPdf() {
        dialog.setTitle("Uploading");
        dialog.setMessage("Please wait while uploading");
        dialog.setCancelable(false);
        dialog.show();

        StorageReference reference = storageReference.child("Resumes").child(userId).child("pdf/" + pdfName);
        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        uploadPdfData(String.valueOf(uri), pdfName);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                    }
                });

    }

    private void uploadPdfData(String downloadUrl, String pdfName) {
        Resume resume = new Resume(downloadUrl, pdfName, PdfsizeInString, pdfDate);
        databaseReference.child(userId).child("Resume").setValue(resume)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(AddResmueActivity.this, "Resume Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AddResmueActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}