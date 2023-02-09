package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivitySettingBinding;
import com.appstacks.indiannaukribazaar.profile.paymentmethods.AddPaymentMethodActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;
    DatabaseReference databaseReference;
    private  String mStoreLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStoreLink = "market://details?id=" + getApplicationContext().getPackageName();
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        databaseReference= FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile)).child("Notifications");
        setContentView(binding.getRoot());


        binding.btnPaymentMethods.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, AddPaymentMethodActivity.class));
        });

        binding.btnLocationAccountSetting.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, LocationActivity.class));

        });

        binding.switchPushNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            databaseReference.child("PushNotification").setValue(b);



            }
        });

        binding.switchSmsNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                databaseReference.child("SmsNotification").setValue(b);



            }
        });
        binding.btnAddSocialAccountAccountSetting.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this,AddSocialAccountActivity.class));
        });
        binding.switchPromoNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                databaseReference.child("PromotionalNotification").setValue(b);



            }

        });

        binding.btnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateUsOnGooglePlay();
            }
        });
    }


    public void rateUsOnGooglePlay(){

final Uri marketUri = Uri.parse(mStoreLink);
try {
    startActivity(new Intent(Intent.ACTION_VIEW, marketUri));

}catch (android.content.ActivityNotFoundException ex){
    Toast.makeText(this, "Couldn't find PlayStore on this device", Toast.LENGTH_SHORT).show();
}

    }
}