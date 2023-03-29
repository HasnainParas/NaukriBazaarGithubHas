package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityNormalJobCvBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NormalJobCvActivity extends AppCompatActivity {

    ActivityNormalJobCvBinding binding;
    String jobid, userauthid;
    DatabaseReference normaljobRef;
    private static final int REQUEST_CODE = 1;
    private Uri uri;
    private String PdfsizeInString, pdfDate, pdfName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNormalJobCvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        normaljobRef = FirebaseDatabase.getInstance().getReference("allUserJobs");

        jobid = getIntent().getStringExtra("jobid");
        userauthid = getIntent().getStringExtra("jobuserid");

//        binding.textView75.setText("JobID:-} " + jobid + " {\n");
//        binding.textView78.setText("\nAuthID:-} " + userauthid + " {");


        normaljobRef.child(jobid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserJobModel model = snapshot.getValue(UserJobModel.class);
                    assert model != null;
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
                    binding.successFullLayout.setVisibility(View.VISIBLE);
                    binding.constraintLayout9.setVisibility(View.GONE);
                    binding.cvAftercompleteName.setText(pdfName);
                    binding.cvAftercompleteInfo.setText(pdfDate);
                }

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

}