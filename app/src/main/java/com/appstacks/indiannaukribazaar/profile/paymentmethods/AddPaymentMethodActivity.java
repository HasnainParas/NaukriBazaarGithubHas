package com.appstacks.indiannaukribazaar.profile.paymentmethods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddPaymentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddPaymentMethodActivity extends AppCompatActivity {
    private ActivityAddPaymentBinding binding;
    private DatabaseReference databaseReference;
    private String userId;
    private ArrayList<PaymentMethods> list;
    private PaymentAdapter adapter;
    private PaymentMethods paymentMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackAddPayment.setOnClickListener(view -> {
            finish();
        });

        list = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        binding.btnAddPaymentMethod.setOnClickListener(view -> {
            startActivity(new Intent(AddPaymentMethodActivity.this, BankingInformationActivity.class));
        });


        fetchPaymentMethods();


    }

    private void fetchPaymentMethods() {

        databaseReference.child(userId).child("PaymentMethods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.layoutNoCard.setVisibility(View.GONE);
                    binding.cardsRecyclerView.setVisibility(View.VISIBLE);

                    list.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {

                        paymentMethods = snap.getValue(PaymentMethods.class);
                        list.add(paymentMethods);


                    }
                    assert paymentMethods != null;

                    adapter = new PaymentAdapter(list, AddPaymentMethodActivity.this, userId, paymentMethods.getUniqueId());
                    binding.cardsRecyclerView.setLayoutManager(new LinearLayoutManager(AddPaymentMethodActivity.this));
                    adapter.notifyDataSetChanged();

                    binding.cardsRecyclerView.setAdapter(adapter);

                } else {

                    binding.layoutNoCard.setVisibility(View.VISIBLE);
                    binding.cardsRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddPaymentMethodActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                binding.layoutNoCard.setVisibility(View.VISIBLE);
                binding.cardsRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}