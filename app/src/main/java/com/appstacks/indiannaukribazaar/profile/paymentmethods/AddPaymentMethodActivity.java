package com.appstacks.indiannaukribazaar.profile.paymentmethods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddPaymentBinding;

public class AddPaymentMethodActivity extends AppCompatActivity {
    private ActivityAddPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackAddPayment.setOnClickListener(view -> {
            finish();
        });


        binding.btnAddPaymentMethod.setOnClickListener(view -> {
            startActivity(new Intent(AddPaymentMethodActivity.this, BankingInformationActivity.class));
        });
    }
}