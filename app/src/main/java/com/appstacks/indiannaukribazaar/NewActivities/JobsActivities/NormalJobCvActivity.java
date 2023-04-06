package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

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

import com.appstacks.indiannaukribazaar.FcmNotificationsSender;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityNormalJobCvBinding;
import com.appstacks.indiannaukribazaar.profile.resume.AddResmueActivity;
import com.appstacks.indiannaukribazaar.profile.resume.Resume;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NormalJobCvActivity extends AppCompatActivity {

    ActivityNormalJobCvBinding binding;
    private String jobid, userauthid, timeText;
    DatabaseReference normaljobRef;
    private static final int REQUEST_CODE = 1;
    private Uri uri;
    private String PdfsizeInString, pdfDate, pdfName,token, currentUserAuth;
    private ProgressDialog dialog;

    private DatabaseReference appliedJobsRef, allUserTokenRef;
    private StorageReference storageReference;
    private Resume resume;
    private UserDataModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNormalJobCvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);

        jobid = getIntent().getStringExtra("jobid");
        userauthid = getIntent().getStringExtra("postedUserAuth");

        currentUserAuth = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        normaljobRef = FirebaseDatabase.getInstance().getReference("allUserNormalJobs");
        appliedJobsRef = FirebaseDatabase.getInstance().getReference("UsersAppliedNormalJobs");
        allUserTokenRef = FirebaseDatabase.getInstance().getReference("AllUsersToken");

        storageReference = FirebaseStorage.getInstance().getReference("normalJobsUsersCv/");


        timeText = getIntent().getStringExtra("normalJobTime");

//        binding.textView75.setText("JobID:-} " + jobid + " {\n");
//        binding.textView78.setText("\nAuthID:-} " + userauthid + " {");

        fetchDetails();
        fetchToken();
        binding.cvTimeText.setText(timeText);

        binding.cvComlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                        token,
                        "Normal Jobs",
                        currentUserAuth + " Applied for Paid Jobs", getApplicationContext(), NormalJobCvActivity.this
                );
                notificationsSender.SendNotifications();
            }
        });

        binding.cvuploadClickBtn.setOnClickListener(v -> pickPdf());

        binding.cvpdfDeleteBtn.setOnClickListener(v -> {
            uri = null;
            binding.cvPdfInfo.setText("");
            binding.cvPdfName.setText("");
            binding.cvuploadClickBtn.setVisibility(View.VISIBLE);
            binding.uploadedCvLayout.setVisibility(View.GONE);
        });


        binding.cvApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri == null) {
                    Toast.makeText(NormalJobCvActivity.this, "Please Select any Cv/Resume", Toast.LENGTH_SHORT).show();
                } else if
                (binding.editTextTextPersonName.getText().toString().isEmpty()) {
                    binding.editTextTextPersonName.setError("empty");
                    Toast.makeText(NormalJobCvActivity.this, "Field Can't be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (uri != null) {
                        uploadPdf();
                    } else {
                        Toast.makeText(NormalJobCvActivity.this, "Select your resume in PDF format", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        binding.cvBackbtn.setOnClickListener(v -> onBackPressed());

        binding.backhomebtnCv.setOnClickListener(v -> {
            startActivity(new Intent(NormalJobCvActivity.this, FindJobsActivity.class));
            finishAffinity();

        });
        binding.findsimilarjobbtncv.setOnClickListener(v -> {
            startActivity(new Intent(NormalJobCvActivity.this, NormalJobsActivity.class));
            finishAffinity();
        });

    }

    private void fetchToken() {
        if (userauthid !=null){
            allUserTokenRef.child(userauthid).child("userToken").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
//                        model = snapshot.getValue(UserDataModel.class);
                        token = snapshot.getValue(String.class);
                        Toast.makeText(NormalJobCvActivity.this,token, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(NormalJobCvActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Toast.makeText(NormalJobCvActivity.this, "User Auth ID Not Found", Toast.LENGTH_SHORT).show();
        }


    }

    private void fetchDetails() {
        normaljobRef.child(jobid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserJobModel model = snapshot.getValue(UserJobModel.class);
                    assert model != null;
                    Glide.with(NormalJobCvActivity.this)
                            .load(model.getCompanyImageURL())
                            .placeholder(R.drawable.placeholder)
                            .into(binding.cvComlogo);

                    binding.cvJobtitle.setText(model.getJobTitle());
                    binding.cvComText.setText(model.getCompanyName());
                    binding.cvLocationText.setText(model.getJobLocation());
                    binding.cvJobtitle.setText(model.getJobTitle());


//                    binding.textView78.setText(model.getJobTitle());

                } else {
                    Toast.makeText(NormalJobCvActivity.this, "List Empty", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NormalJobCvActivity.this, "Error Founded" + error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
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
            binding.cvuploadClickBtn.setVisibility(View.GONE);
            binding.uploadedCvLayout.setVisibility(View.VISIBLE);
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
                        binding.cvPdfInfo.setText(pdfDate);
                        binding.cvPdfName.setText(pdfName);


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

        StorageReference reference = storageReference.child(jobid + "/").child("Cv/" + pdfName);
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
        resume = new Resume(downloadUrl, pdfName, PdfsizeInString, pdfDate);
        appliedJobsRef.child(jobid).child("cv").setValue(resume)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            appliedJobsRef.child(jobid)
                                    .child("information")
                                    .setValue(binding.editTextTextPersonName.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                                    token,
                                                    "Normal Jobs",
                                                    currentUserAuth + " Applied for Paid Jobs", getApplicationContext(), NormalJobCvActivity.this
                                            );
                                            notificationsSender.SendNotifications();
                                            binding.successFullLayout.setVisibility(View.VISIBLE);
                                            binding.constraintLayout9.setVisibility(View.GONE);
                                            binding.cvAftercompleteName.setText(pdfName);
                                            binding.cvAftercompleteInfo.setText(pdfDate);
                                            Toast.makeText(NormalJobCvActivity.this, "Resume Added", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(NormalJobCvActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(NormalJobCvActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}