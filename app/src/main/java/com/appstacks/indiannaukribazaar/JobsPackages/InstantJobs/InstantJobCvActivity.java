package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

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
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.NormalJobCvActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantJobCvBinding;
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
import java.util.Objects;

public class InstantJobCvActivity extends AppCompatActivity {

    ActivityInstantJobCvBinding binding;

    private String jobid, userauthid, timeText;

    DatabaseReference instantjobRef;

    private static final int REQUEST_CODE = 1;

    private Uri uri;

    private String PdfsizeInString, pdfDate, pdfName, token, currentUserAuth, currentUserName;

    private ProgressDialog dialog;

    private DatabaseReference appliedInstantJobsRef, allUserTokenRef, alluserRef;

    private StorageReference storageReference;

    private Resume resume;

    private UserDataModel model;
    private String userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstantJobCvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // instant job activity
        dialog = new ProgressDialog(this);

        jobid = getIntent().getStringExtra("instantJobID");
        userauthid = getIntent().getStringExtra("instantJobUserAuth");

        currentUserAuth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        instantjobRef = FirebaseDatabase.getInstance().getReference("InstantJobs");
        appliedInstantJobsRef = FirebaseDatabase.getInstance().getReference("UsersAppliedInstantJobs");
        allUserTokenRef = FirebaseDatabase.getInstance().getReference("AllUsersToken");
        alluserRef = FirebaseDatabase.getInstance().getReference("AllUsers");

        storageReference = FirebaseStorage.getInstance().getReference("instantJobsUsersCv/");

        timeText = getIntent().getStringExtra("instantJobTime");

        fetchDetails();
        fetchToken();
        fetchCurrentUserName();
        binding.cvTimeTextinstant.setText(timeText);

        binding.cvuploadClickBtninstant.setOnClickListener(v -> pickPdf());

        binding.cvpdfDeleteBtninstant.setOnClickListener(v -> {
            uri = null;
            binding.cvPdfInfoinstant.setText("");
            binding.cvPdfNameinstant.setText("");
            binding.cvuploadClickBtninstant.setVisibility(View.VISIBLE);
            binding.uploadedCvLayoutinstant.setVisibility(View.GONE);
        });


        binding.cvApplyBtninstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri == null) {
                    Toast.makeText(InstantJobCvActivity.this, "Please Select any Cv/Resume", Toast.LENGTH_SHORT).show();
                } else if
                (binding.editTextInstant.getText().toString().isEmpty()) {
                    binding.editTextInstant.setError("empty");
                    Toast.makeText(InstantJobCvActivity.this, "Field Can't be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (uri != null) {
                        uploadPdf();
                    } else {
                        Toast.makeText(InstantJobCvActivity.this, "Select your resume in PDF format", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        binding.instantBackbtn.setOnClickListener(v -> onBackPressed());

        binding.backhomebtnCv.setOnClickListener(v -> {
            startActivity(new Intent(InstantJobCvActivity.this, FindJobsActivity.class));
            finishAffinity();

        });
        binding.findsimilarjobbtncvinstant.setOnClickListener(v -> {
            startActivity(new Intent(InstantJobCvActivity.this, SearchNapplyInstantJobActivity.class));
            finishAffinity();
        });

    }

    private void fetchCurrentUserName() {

        alluserRef.child(currentUserAuth).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    currentUserName = snapshot.child("fullName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void fetchToken() {
        if (userauthid != null) {
            allUserTokenRef.child(userauthid).child("userToken").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
//                        model = snapshot.getValue(UserDataModel.class);
                        token = snapshot.getValue(String.class);
                        Toast.makeText(InstantJobCvActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(InstantJobCvActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(InstantJobCvActivity.this, "User Auth ID Not Found", Toast.LENGTH_SHORT).show();
        }


    }

    private void fetchDetails() {
        instantjobRef.child(jobid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    InstantAddJobsModel model = snapshot.getValue(InstantAddJobsModel.class);
                    assert model != null;
                    Glide.with(InstantJobCvActivity.this)
                            .load(model.getInJobCompanyImgURL())
                            .placeholder(R.drawable.placeholder)
                            .into(binding.cvComlogoinstant);

                    binding.cvJobtitleinstant.setText(model.getInJobTitle());
                    binding.cvComTextinstant.setText(model.getInJobCompany());
                    binding.cvLocationTextinstant.setText(model.getInJobBudget());

//                    binding.textView78.setText(model.getJobTitle());

                } else {
                    Toast.makeText(InstantJobCvActivity.this, "List Empty", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InstantJobCvActivity.this, "Error Founded" + error.toException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
            binding.cvuploadClickBtninstant.setVisibility(View.GONE);
            binding.uploadedCvLayoutinstant.setVisibility(View.VISIBLE);
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
                        binding.cvPdfInfoinstant.setText(pdfDate);
                        binding.cvPdfNameinstant.setText(pdfName);


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

        StorageReference reference = storageReference
                .child(jobid)
                .child(currentUserAuth)
                .child("Cv/" + pdfName);
        reference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri uri = uriTask.getResult();
                    uploadPdfData(String.valueOf(uri), pdfName);


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                    }
                });

    }

    private void uploadPdfData(String downloadUrl, String pdfName) {
        resume = new Resume(downloadUrl, pdfName, PdfsizeInString, pdfDate);
        JobAppliedModel appliedModel = new JobAppliedModel(
                binding.editTextInstant.getText().toString(),
                currentUserAuth,jobid,
                getProfilePic(currentUserAuth));

        appliedInstantJobsRef
                .child(jobid)
                .child(currentUserAuth)
                .setValue(appliedModel)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        appliedInstantJobsRef
                                .child(jobid)
                                .child(currentUserAuth)
                                .child("cv").setValue(resume)
                                .addOnSuccessListener(unused -> {
                                    dialog.dismiss();
                                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                                            token,
                                            "Instant Jobs",
                                            currentUserName + " : " + " Applied for Instant Job", getApplicationContext(), InstantJobCvActivity.this
                                    );
                                    notificationsSender.SendNotifications();
                                    binding.successFullLayoutinstant.setVisibility(View.VISIBLE);
                                    binding.constraintLayout9instant.setVisibility(View.GONE);
                                    binding.cvAftercompleteNameinstant.setText(pdfName);
                                    binding.cvAftercompleteInfoinstant.setText(pdfDate);
                                    Toast.makeText(InstantJobCvActivity.this, "Cv Added", Toast.LENGTH_SHORT).show();

                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(InstantJobCvActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(InstantJobCvActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getProfilePic(String userId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UsersProfile");
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userImage = snapshot.child("UserImage").getValue(String.class);
//                    String budget = snapshot.child("Hourly Charges").getValue(String.class);
                    Toast.makeText(InstantJobCvActivity.this, "Image is exist hahahaha ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(InstantJobCvActivity.this, "Image is not exist hahahaha ", Toast.LENGTH_SHORT).show();

            }
        });


        return userImage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}