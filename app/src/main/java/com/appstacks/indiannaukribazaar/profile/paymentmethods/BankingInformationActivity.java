package com.appstacks.indiannaukribazaar.profile.paymentmethods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityBankingInformationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class BankingInformationActivity extends AppCompatActivity {
    private ActivityBankingInformationBinding binding;
    private DatabaseReference databaseReference;
    private String userId;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBankingInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

      data=  getIntent().getStringExtra("data");
        Gson gson = new Gson();
        Type type = new TypeToken<PaymentMethods>() {
        }.getType();

       PaymentMethods paymentMethods= gson.fromJson(data,type);
       if (paymentMethods != null) {
           binding.etPrimaryBankName.setText(paymentMethods.getPrimaryBankName());
           binding.etIfscCode.setText(paymentMethods.getIfscCode());
           binding.etAccountNo.setText(paymentMethods.getAccountNumber());
           binding.etBranchName.setText(paymentMethods.getBranchName());
           binding.etAccoutType.setText(paymentMethods.getAccountType());
           binding.etUpiId.setText(paymentMethods.getUpiID());
       }

        binding.btnBackBankingInformation.setOnClickListener(view -> {
            finish();
        });


        binding.btnAddPaymentDetails.setOnClickListener(view -> {
            checkValidation();
        });

    }

    private void checkValidation() {

        if (binding.etPrimaryBankName.getText().toString().isEmpty())
            Toast.makeText(this, "Bank name is not added", Toast.LENGTH_SHORT).show();

        else if (binding.etAccountNo.getText().toString().isEmpty())
            Toast.makeText(this, "Account name not added", Toast.LENGTH_SHORT).show();
        else if (binding.etIfscCode.getText().toString().isEmpty())
            Toast.makeText(this, "IFSC Code not added", Toast.LENGTH_SHORT).show();
        else if (binding.etBranchName.getText().toString().isEmpty())
            Toast.makeText(this, "Branch name not added", Toast.LENGTH_SHORT).show();
        else if (binding.etAccoutType.getText().toString().isEmpty())
            Toast.makeText(this, "Account type not added", Toast.LENGTH_SHORT).show();
        else if (binding.etUpiId.getText().toString().isEmpty())
            Toast.makeText(this, "UPI Id not added", Toast.LENGTH_SHORT).show();
        else {
            String primaryBankName = binding.etPrimaryBankName.getText().toString();
            String accountNo = binding.etAccountNo.getText().toString();
            String ifscCode = binding.etIfscCode.getText().toString();
            String branchName = binding.etBranchName.getText().toString();
            String accountType = binding.etAccoutType.getText().toString();
            String upiId = binding.etUpiId.getText().toString();
            String uniqueId= UUID.randomUUID().toString();


            PaymentMethods paymentMethods = new PaymentMethods(primaryBankName,accountNo,ifscCode,branchName,accountType,upiId,uniqueId);

            databaseReference.child(userId).child("PaymentMethods").child(uniqueId).setValue(paymentMethods)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete() && task.isSuccessful()){
                                Toast.makeText(BankingInformationActivity.this, "Payment method added", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(BankingInformationActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BankingInformationActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }
}