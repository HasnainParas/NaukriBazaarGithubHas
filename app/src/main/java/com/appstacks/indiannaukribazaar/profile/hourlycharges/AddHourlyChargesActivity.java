package com.appstacks.indiannaukribazaar.profile.hourlycharges;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddHourlyChargesBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHourlyChargesActivity extends AppCompatActivity {
    private ActivityAddHourlyChargesBinding binding;
    private DatabaseReference databaseReference;
    private String userId;
    private String intentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHourlyChargesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        intentString = getIntent().getStringExtra("hourly");

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (intentString.equals("edit")) {
            binding.txtHourlyCharges.setText("Change your hourly charges");
            fetchData();
        }


        binding.btnBackHourlyCharges.setOnClickListener(view -> {
            finish();
        });
        binding.btnSaveAboutMe.setOnClickListener(view -> {
            if (binding.etHourlyCharges.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add your hourly charges", Toast.LENGTH_SHORT).show();
            } else {
                String charges = binding.etHourlyCharges.getText().toString();
                uploadHourlyCharges(charges);
            }
        });
    }

    private void fetchData() {
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Hourly Charges").exists()) {
                    String data = snapshot.child("Hourly Charges").getValue(String.class);
                    binding.etHourlyCharges.setText(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadHourlyCharges(String charges) {


        databaseReference.child(userId).child("HourlyCharges").setValue(charges)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        finish();
                    } else {
                        Toast.makeText(AddHourlyChargesActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(AddHourlyChargesActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());


    }
}