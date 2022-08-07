package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Models.DeviceDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityOtpactivityBinding;
import com.appstacks.indiannaukribazaar.databinding.HandloadingDialogLayoutBinding;
import com.appstacks.indiannaukribazaar.model.DeviceInfo;
import com.appstacks.indiannaukribazaar.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;

public class OTPActivity extends AppCompatActivity {

    ActivityOtpactivityBinding binding;

    AlertDialog loadingDialog;
    String mobileNumber;
    String verificationID;
    String android_id;
    DatabaseReference deviceRef;
    int remaintime = 60;
    CountDownTimer countDownTimer;
    DeviceInfo deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deviceInfo = Tools.getDeviceInfo(this);

        loadingAlertDialog();

        deviceRef = FirebaseDatabase.getInstance().getReference();

        mobileNumber = getIntent().getStringExtra("mobileNumber");
        verificationID = getIntent().getStringExtra("verificationId");
        android_id = getIntent().getStringExtra("deviceID");
        binding.otpnumber.setText("mobile number " + mobileNumber);

        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millifinished) {
                remaintime = (int) millifinished / 1000;
                binding.resendBtn.setVisibility(View.INVISIBLE);
                binding.countdownlayout.setVisibility(View.VISIBLE);
                binding.countDown.setText(String.valueOf(remaintime));

            }

            @Override
            public void onFinish() {
                binding.countdownlayout.setVisibility(View.INVISIBLE);
                binding.resendBtn.setVisibility(View.VISIBLE);
            }
        };
        countDownTimer.start();


        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(String otp) {
//                Toast.makeText(OtpActivity.this, otp, Toast.LENGTH_SHORT).show();

                if (verificationID != null) {

                    loadingDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(
                            verificationID, otp);

                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            DeviceDataModel model = new
                                    DeviceDataModel(
                                    android_id, mobileNumber, deviceInfo.os_version,
                                    deviceInfo.device_name, deviceInfo.app_version, true);
                            loadingDialog.dismiss();
                            if (task.isSuccessful()) {
                                deviceRef.child("RegDevices").child(android_id).setValue(model);
                                startActivity(new Intent(OTPActivity.this, UserNameActivity.class));
//                                Toast.makeText(OTPActivity.this, "Sign in Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(OTPActivity.this, "Verification Code Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(OTPActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

//        binding.imageViewClick.setOnClickListener(view -> countDownTimer.start());

        binding.resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber,
                        60, TimeUnit.SECONDS, OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                loadingDialog.dismiss();
                                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(s, forceResendingToken);
                                loadingDialog.dismiss();
                                verificationID = newVerificationID;
                                Toast.makeText(OTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                                countDownTimer.start();
                            }
                        }
                );
            }
        });
    }

    public void loadingAlertDialog() {

        HandloadingDialogLayoutBinding handloadingBinding = HandloadingDialogLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(OTPActivity.this)
                .setView(handloadingBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


}