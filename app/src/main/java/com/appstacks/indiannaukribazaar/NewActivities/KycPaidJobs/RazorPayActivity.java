package com.appstacks.indiannaukribazaar.NewActivities.KycPaidJobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FcmNotificationsSender;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityRazorPayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Objects;

public class RazorPayActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityRazorPayBinding binding;
    DatabaseReference kycAmountRef, userRef, adminTokenRef;
    FirebaseAuth auth;
    private static final String TAG = RazorPayActivity.class.getSimpleName();
    String kycAmount;
    String currentUserID;
    UserDataModel model;
    String adminToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRazorPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Checkout.preload(getApplicationContext());

        binding.progressBar3.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();
        currentUserID = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        kycAmountRef = FirebaseDatabase.getInstance().getReference().child("kycAmount");
        userRef = FirebaseDatabase.getInstance().getReference("AllUsers").child(currentUserID);
        adminTokenRef = FirebaseDatabase.getInstance().getReference("AdminPanel").child("adminToken");


        kycAmountRef.child("inr").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    kycAmount = snapshot.getValue(String.class);
                    binding.progressBar3.setVisibility(View.INVISIBLE);
                    binding.amounttext.setVisibility(View.VISIBLE);
                    binding.amounttext.setText(kycAmount + "/- Inr");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Toast.makeText(RazorPayActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                    model = snapshot.getValue(UserDataModel.class);
                    assert model != null;
//                    binding.status.setText(model.getEmailAddress());


//                    for (DataSnapshot s : snapshot.getChildren()) {
//                        UserDataModel model = s.getValue(UserDataModel.class);
//                        assert model != null;
////                        usernameCheck = data.getUserName();
//
//                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.paybtn.setOnClickListener(view -> {

            startPayment(Integer.parseInt(kycAmount),
                    model.getFullName(),
                    model.getMobileNumber(),
                    model.getEmailAddress());

        });

        adminTokenRef.child("token").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminToken = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void startPayment(int amount, String name, String phoneNumber, String email) {
        Checkout checkout = new Checkout(); /**   * Instantiate Checkout   */
        checkout.setKeyID("rzp_test_zMcQBYij8ClyM5");    /**   * Reference to current activity   */
        checkout.setImage(R.drawable.logow);  /**   * Set your logo here   */
        final Activity activity = this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */


        try {
            JSONObject options = new JSONObject();

            options.put("name", name);
//            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#6EDB47");
            options.put("currency", "INR");
            options.put("amount", amount * 100);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", phoneNumber);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(RazorPayActivity.this, "Successful \n ID " + s, Toast.LENGTH_SHORT).show();
        userRef.child("verification").setValue(false);
        binding.status.setText("Successful \n ID: " + s);
        userRef.child("razorPaymentID").setValue(s);
        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                adminToken,
                model.getFullName() + " Just Create Job",
                "RazorPayment ID: " + s, getApplicationContext(), RazorPayActivity.this
        );
        notificationsSender.SendNotifications();
        startActivity(new Intent(RazorPayActivity.this, WelldoneActivity.class));
        finishAffinity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(RazorPayActivity.this, "Failed " + s, Toast.LENGTH_SHORT).show();
        binding.status.setText("Failed " + s);

    }
}
