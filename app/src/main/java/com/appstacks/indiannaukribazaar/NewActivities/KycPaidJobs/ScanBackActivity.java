package com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityScanBackBinding;
import com.appstacks.indiannaukribazaar.databinding.HandloadingDialogLayoutBinding;
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

import java.io.ByteArrayOutputStream;

public class ScanBackActivity extends AppCompatActivity {

    ActivityScanBackBinding binding;
    String selectedDoc, frontImage, backImage;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap photo;
    String user;
    StorageReference storageReference;
    DatabaseReference reference,userRef;
    ProgressDialog progressDialog;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDoc = getIntent().getStringExtra("selectedDocument");
        frontImage = getIntent().getStringExtra("frontImage");
        binding = ActivityScanBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        reference = FirebaseDatabase.getInstance().getReference("UsersInfo").child(user);
        userRef = FirebaseDatabase.getInstance().getReference("AllUsers").child(user);

        storageReference = FirebaseStorage.getInstance()
                .getReference("UserInfo")
                .child(user).child(selectedDoc);

        binding.icBck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.scanDocumentBtn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                }
            }
        });

        binding.icBck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.nextStebtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading. . .");
                progressDialog.setCancelable(false);
                loadingAlertDialog();
                loadingDialog.show();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] finalImage = baos.toByteArray();
                StorageReference path;
                path = storageReference.child(finalImage + "jpg");
                UploadTask uploadTask = path.putBytes(finalImage);

                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            backImage = String.valueOf(uri);

                                            uploadData(frontImage,backImage);
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(ScanBackActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    private void uploadData(String frontImage, String backImage) {

        reference.child("frontImage").setValue(frontImage);
        reference.child("backImage").setValue(backImage)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    task.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            userRef.child("verification").setValue(false);
                            loadingDialog.dismiss();
                            Toast.makeText(ScanBackActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ScanBackActivity.this, WelldoneActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ScanBackActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ScanBackActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            binding.idscanBckImg.setImageBitmap(photo);

            binding.scanDocumentBtn2.setVisibility(View.INVISIBLE);
            binding.nextStebtn2.setVisibility(View.VISIBLE);

        }


    }


    public void loadingAlertDialog(){

        HandloadingDialogLayoutBinding handloadingBinding = HandloadingDialogLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(ScanBackActivity.this)
                .setView(handloadingBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


}