package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.Activities.ActivityMain;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityUserProfileBinding;
import com.appstacks.indiannaukribazaar.databinding.HandloadingDialogLayoutBinding;
import com.appstacks.indiannaukribazaar.model.DeviceInfo;
import com.appstacks.indiannaukribazaar.utils.Tools;
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
import com.google.firebase.messaging.FirebaseMessaging;

public class User_ProfileActivity extends AppCompatActivity {

    ActivityUserProfileBinding binding;
    AlertDialog loadingDialog;
    DatabaseReference userRef, deviceRef;
    FirebaseAuth auth;
    String uID;
    String selection;
    ProgressDialog dialog;
    private String userToken;
    DeviceInfo deviceInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        deviceInfo = Tools.getDeviceInfo(this);

//        FirebaseMessaging.getInstance()
//                .getToken()
//                .addOnSuccessListener(new OnSuccessListener<String>() {
//                    @Override
//                    public void onSuccess(String token) {
//                        userToken = token;
//                    }
//                });


        userRef = FirebaseDatabase.getInstance().getReference("AllUsers");

        deviceRef = FirebaseDatabase.getInstance().getReference("RegDevices");
        auth = FirebaseAuth.getInstance();
        String username = getIntent().getStringExtra("username");



        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        genderSelection();
        dateOfBirthSelection();
        loadingAlertDialog();



        uID = auth.getUid();


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.emailBox.getText().toString().trim().isEmpty()) {
                    binding.emailBox.setError("Please Type Your Valid EmailAddress");
                } else if (binding.nameBox.getText().toString().trim().isEmpty()) {
                    binding.nameBox.setError("Please Type Your Full Name");
                } else if (binding.genderBox.getText().toString().trim().isEmpty()) {
                    binding.genderBox.setError("Please Select Your Gender");
                } else if (binding.maskedDobTv.getText().toString().trim().isEmpty()) {
                    binding.maskedDobTv.setError("Please Enter Your Date of Birth");
                } else {

                    loadingDialog.show();
                    UserDataModel dataModel = new UserDataModel(
                            uID,
                            auth.getCurrentUser().getPhoneNumber(),
                            username,
                            binding.emailBox.getText().toString(),
                            binding.nameBox.getText().toString(),
                            binding.genderBox.getText().toString(),
                            binding.maskedDobTv.getText().toString()
                    );
                    userRef.child(uID).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                deviceRef.child(deviceInfo.device_id).child("token").setValue(userToken);
                                loadingDialog.dismiss();
                                startActivity(new Intent(User_ProfileActivity.this, ActivityMain.class));
                                finishAffinity();
//                                deviceRef.child(deviceInfo.device_id).child("token").setValue(userToken);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingDialog.dismiss();
                            Toast.makeText(User_ProfileActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


    }

    public void loadingAlertDialog() {

        HandloadingDialogLayoutBinding handloadingBinding = HandloadingDialogLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(User_ProfileActivity.this)
                .setView(handloadingBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


    public void genderSelection() {

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getResources()
                .getStringArray(R.array.Gender_Names));

        binding.genderBox.setAdapter(arrayAdapter);
        binding.genderBox.setCursorVisible(false);
        binding.genderBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.genderBox.showDropDown();
                selection = (String) parent.getItemAtPosition(position);
                binding.genderBox.setText(selection);
                Toast.makeText(getApplicationContext(), selection,
                        Toast.LENGTH_SHORT);

            }
        });


        binding.genderBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                binding.genderBox.showDropDown();

            }
        });
    }

    public void dateOfBirthSelection() {

        binding.maskedDobTv.setMask("##/##/####");

    }


}