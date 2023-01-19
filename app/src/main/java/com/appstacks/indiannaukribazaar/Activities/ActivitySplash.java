package com.appstacks.indiannaukribazaar.Activities;

import static com.appstacks.indiannaukribazaar.data.ThisApp.CHANNEL_1_ID;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.appstacks.indiannaukribazaar.NewActivities.BoardingActivity;
import com.appstacks.indiannaukribazaar.NewActivities.UserNameActivity;
import com.appstacks.indiannaukribazaar.NewActivities.User_ProfileActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.Slider.ImageData;
import com.appstacks.indiannaukribazaar.data.SharedPref;
import com.appstacks.indiannaukribazaar.data.ThisApp;
import com.appstacks.indiannaukribazaar.databinding.ActivitySplashBinding;
import com.appstacks.indiannaukribazaar.databinding.DeviceLayoutBinding;
import com.appstacks.indiannaukribazaar.databinding.HandloadingDialogLayoutBinding;
import com.appstacks.indiannaukribazaar.model.DeviceInfo;
import com.appstacks.indiannaukribazaar.utils.PermissionUtil;
import com.appstacks.indiannaukribazaar.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {

    private SharedPref sharedPref;
    private boolean on_permission_result = false;
    ActivitySplashBinding binding;
    FirebaseAuth auth;
    DatabaseReference deviceRef, userRef;
    String android_id;
    AlertDialog loadingDialog;
    DeviceInfo deviceInfo;
    private NotificationManagerCompat notificationManager;
    DataSnapshot user;
    String userUID;
    String namevalid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        deviceRef = FirebaseDatabase.getInstance().getReference().child("RegDevices");
        userRef = FirebaseDatabase.getInstance().getReference("AllUsers");

        if (auth.getCurrentUser() != null) {
            binding.getStartBtn.setVisibility(View.INVISIBLE);
            userRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    namevalid = snapshot.child("userToken").getValue(String.class);
                    namevalid = snapshot.child("userName").getValue(String.class);
                    if (!snapshot.exists() || namevalid.isEmpty()) {
                        startActivity(new Intent(ActivitySplash.this, UserNameActivity.class));
                        Toast.makeText(ActivitySplash.this, "NotValid", Toast.LENGTH_SHORT).show();
                        finishAffinity();
                    } else {
//                                Toast.makeText(ActivitySplash.this, "Valid", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivitySplash.this,ActivityMain.class));
                    }
                    Toast.makeText(ActivitySplash.this, namevalid + "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ActivitySplash.this, "SomeThing Wrong\n " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        deviceInfo = Tools.getDeviceInfo(this);
//        Toast.makeText(ActivitySplash.this,deviceInfo.device_name+"\n"+deviceInfo.os_version+"\n"+deviceInfo.device_id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(ActivitySplash.this, deviceInfo.device_id, Toast.LENGTH_SHORT).show();

        notificationManager = NotificationManagerCompat.from(this);

        loadingAlertDialog();


        new LongOperation().execute();

        sharedPref = new SharedPref(this);
        Tools.setSmartSystemBar(this);
        Tools.RTLMode(getWindow());
        ThisApp.get().saveClassLogEvent(getClass());


    }

    private class LongOperation extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseFirestore.collection("SingleImageLink").document("data");
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ImageData imageData = ImageData.getInstance();
                            imageData.setImglink(document.getString("imageLink"));
                            imageData.setWebsiteLink(document.getString("websiteLink"));
                            imageData.setText(document.getString("text"));
                        }


                    }
                }
            });
            return null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ThisApp.get().resetInfo();
        // permission checker for android M or higher
        if (Tools.needRequestPermission() && !on_permission_result) {
            String[] permission = PermissionUtil.getDeniedPermission(this);
            if (permission.length != 0) {
                requestPermissions(permission, 200);
            } else {
                startActivityMainDelay();
            }
        } else {
            startActivityMainDelay();
        }
    }

    private void startActivityMainDelay() {
        // Show splash screen for 2 seconds
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                Intent i = new Intent(ActivitySplash.this, ActivityMain.class);
//                startActivity(i);
//                finish(); // kill current activity
//            }
//        };
//        new Timer().schedule(task, 1500);
        binding.getStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySplash.this, BoardingActivity.class));
                finish();


//                DynamicLink dynamicLink = FirebaseDyna micLinks.getInstance().createDynamicLink()
//                        .setLink(Uri.parse("https://play.google.com/store/apps/details?id=com.app.naukribazaarinc"))
//                        .setDomainUriPrefix("https://appstacks.page.link")
//                        // Open links with this app on Android
//                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
//                        // Open links with com.example.ios on iOS
//                        .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
//                        .buildDynamicLink();
//
//                String links = "https://appstacks.page.link/?"+
//                        "link=http://www.jobtanks.com/"+
//                        "&apn="+getPackageName()+
//                        "&st="+"My Refer Link"+
//                        "sd="+"Rewards 20"+
//                        "&si="+"https://storage.googleapis.com/gweb-uniblog-publish-prod/images/HeroHomepage_2880x1200.max-1000x1000.jpg"
//                        ;
//
//
//                Uri dynamicLinkUri = dynamicLink.getUri();
//
//                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
////                        .setLongLink(dynamicLink.getUri())
//                        .setLongLink(Uri.parse(links))
//                        .buildShortDynamicLink()
//                        .addOnCompleteListener(ActivitySplash.this, task -> {
//                            if (task.isSuccessful()) {
//                                // Short link created
//                                Uri shortLink = task.getResult().getShortLink();
//                                binding.naukriTv.setText(shortLink.toString());
//                                binding.naukriTv.setOnClickListener(view -> {
//
//                                    Intent in = new Intent();
//                                    in.setAction(Intent.ACTION_SEND);
//                                    in.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
//                                    in.setType("text/Plain");
//                                    startActivity(in);
//
//                                });
//
//                                Uri flowchartLink = task.getResult().getPreviewLink();
//                            } else {
//                                // Error
//                                Toast.makeText(ActivitySplash.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                // ...
//                            }
//                        });
            }
        });

        deviceRef.child(deviceInfo.device_id).child("valid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Toast.makeText(ActivitySplash.this, "kkkk", Toast.LENGTH_SHORT).show();
                    Boolean istrue = snapshot.getValue(boolean.class);
                    if (istrue) {
//                        utils.toast(ActivitySplash.this, "Its True");
                        loadingDialog.dismiss();
                        if (auth.getCurrentUser() != null) {
                            binding.getStartBtn.setVisibility(View.INVISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    /* Create an Intent that will start the Menu-Activity. */
//                                    startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
//                                    finish();

                                }
                            }, 1000);
//                            sendOnChannel1();
//                            if (namevalid.isEmpty()) {
//                                startActivity(new Intent(ActivitySplash.this, UserNameActivity.class));
//                                Toast.makeText(ActivitySplash.this, "NotValid", Toast.LENGTH_SHORT).show();
//                                finishAffinity();
//                            } else {
//                                startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
////                                Toast.makeText(ActivitySplash.this, "Valid", Toast.LENGTH_SHORT).show();
//                            }
                        }

                    } else {
                        loadingDialog.show();
                        Toast.makeText(ActivitySplash.this, " Is signOut", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ActivitySplash.this, DeviceBlockingActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(intent);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ActivitySplash.this, "SomeThing Wrong\n " + error, Toast.LENGTH_SHORT).show();

            }
        });


//        if (auth.getCurrentUser() != null) {
//            binding.getStartBtn.setVisibility(View.INVISIBLE);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    /* Create an Intent that will start the Menu-Activity. */
//                    startActivity(new Intent(ActivitySplash.this, ActivityMain.class));
//                    finish();
//
//                }
//            }, 2000);
//
//
//        }else {
////            binding.getStartBtn.setVisibility(View.);
//            Toast.makeText(this, "Not reg", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            startActivity(new Intent(ActivitySplash.this, DeviceBlockingActivity.class));
//            finish();
//        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            for (String perm : permissions) {
                boolean rationale = shouldShowRequestPermissionRationale(perm);
                sharedPref.setNeverAskAgain(perm, !rationale);
            }
            on_permission_result = true;
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("HardwareIds")
    @Override
    protected void onStart() {
        super.onStart();
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

//        deviceRef.child(android_id).child("valid").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    boolean istrue = snapshot.getValue(Boolean.class);
//                    if (istrue){
//                        Toast.makeText(ActivitySplash.this, "Yes", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(ActivitySplash.this, "No", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public void loadingAlertDialog() {
        DeviceLayoutBinding dialogBinding = DeviceLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(ActivitySplash.this)
                .setView(dialogBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


    }

    public void sendOnChannel1() {
        String title = "Now Your Account is Unblock";
        String message = "Open Your App by sign in your old number";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID

        )
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

}
