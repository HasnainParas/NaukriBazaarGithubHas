package com.appstacks.indiannaukribazaar.profile.resume;

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

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddResmueBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddResmueActivity extends AppCompatActivity {
private ActivityAddResmueBinding binding;
private static final int REQUEST_CODE=1;
private Uri uri;
private String PdfsizeInString,pdfDate,pdfName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddResmueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnBackAddResume.setOnClickListener(view -> {
            finish();
        });

        binding.cardUploadResume.setOnClickListener(view -> {
            pickPdf();
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


}