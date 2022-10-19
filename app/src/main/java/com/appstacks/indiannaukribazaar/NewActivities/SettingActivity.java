package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {
    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPaymentMethods.setOnClickListener(view -> Toast.makeText(SettingActivity.this, "This feature is currently in under development mode", Toast.LENGTH_SHORT).show());

        binding.btnLocationAccountSetting.setOnClickListener(view -> {
            startActivity(new Intent(SettingActivity.this, LocationActivity.class));
        });
    }
}