package com.appstacks.indiannaukribazaar.profile.paymentmethods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.appstacks.indiannaukribazaar.databinding.ActivityBankingInformationBinding;

public class BankingInformationActivity extends AppCompatActivity {
private ActivityBankingInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBankingInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}