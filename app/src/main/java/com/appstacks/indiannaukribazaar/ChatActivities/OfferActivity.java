package com.appstacks.indiannaukribazaar.ChatActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ChatActivities.Models.MessageModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityOfferBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OfferActivity extends AppCompatActivity {

    ActivityOfferBinding binding;
    private String recevierUID,jobID;
    private String jobTitle,jobCompany;
    private String currentUID;

    private String senderRoom;
    private String receiverRoom;

    private FirebaseDatabase database;

    private String[] deliveryTime = {
            "Select Delivery Time",
            "1 Day",
            "3 Days",
            "7 Days",
            "14 Days",
            "21 Days",
            "30 Days"
    };

    private DatabaseReference chatRef, chatContactsRef, profileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recevierUID = getIntent().getStringExtra("recevierIDForOffer");
        jobID = getIntent().getStringExtra("jobIDForOffer");
        jobTitle = getIntent().getStringExtra("jobTitleForOffer");
        jobCompany = getIntent().getStringExtra("jobCompanyForOffer");

        chatRef = FirebaseDatabase.getInstance().getReference("chats");


        currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();

        senderRoom = currentUID + recevierUID;
        receiverRoom = recevierUID + currentUID;

        binding.jobTitleOffer.setText(jobTitle);


        binding.backBtnOffer.setOnClickListener(v -> finish());

//        Spinner spinner = bottomsheetView.findViewById(R.id.spinnerQualifications);
//        Button btnSave = bottomsheetView.findViewById(R.id.btnSaveQualificationJob);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deliveryTime);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDeliveryTime.setAdapter(adapter);

        binding.offerSendBtn.setOnClickListener(v -> offerSendNote());


    }

    private void offerSendNote() {
        String deliveryTime = binding.spinnerDeliveryTime.getSelectedItem().toString();
        String offerBudget = binding.offerBudgetID.getText().toString();
        String offerDes = binding.offerDesCriptionID.getText().toString();

        String randomKey = database.getReference().push().getKey();


        //for chatTIme
        Calendar calendar = Calendar.getInstance();

//                 Format the time in 12-hour format with AM/PM
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        String formattedTime2 = timeFormat.format(calendar.getTime());
        //for chatTime


        MessageModel message = new MessageModel("offer_From_sent_indianJob_Tank",currentUID,formattedTime2,randomKey);
        message.setOfferDeliveryTime(deliveryTime);
        message.setOfferBudget(offerBudget);
        message.setOfferDescription(offerDes);
        message.setOfferJobID(jobID);
        message.setJobTitle(jobTitle);
        message.setOfferStatus("none");


        if (recevierUID != null){
            chatRef.child(senderRoom)
                    .child("messages")
                    .child(randomKey)
                    .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
//                                    database.getReference().child("chats")
                            chatRef.child(receiverRoom)
                                    .child("messages")
                                    .child(randomKey)
                                    .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(OfferActivity.this, "offer_From_sent_indianJob_Tank", Toast.LENGTH_SHORT).show();
                                            finish();
//                                                        chatRef.child(senderRoom).child("msgcount").push().setValue("count");
//                                            chatRef.child(receiverRoom).child("msgcount").push().setValue("count");
//                                            binding.msgChatEditBox.getText().clear();
//                                            hideKeyboarddd(viewSendbtn);
////                                                        Toast.makeText(MessagesActivity.this, "Juu", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
        }
    }
}