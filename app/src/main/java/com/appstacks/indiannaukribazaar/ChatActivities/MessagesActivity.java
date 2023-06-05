package com.appstacks.indiannaukribazaar.ChatActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ChatActivities.Adapters.MessageAdapter;
import com.appstacks.indiannaukribazaar.ChatActivities.Fragments.MessagesFragment;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.ChatContactModel;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.MessageModel;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityMessagesBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.ScopeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {


    ActivityMessagesBinding binding;
    private String username;
    //    private String receiverUid;
    private String proposalSenderUID;
    //    private String proposalUIDFromAllUser;
    private DatabaseReference chatRef, chatContactsRef, profileRef;
    private DatabaseReference chatPresence;
    private DatabaseReference jobRef, jobRefInReceiverRoom;
    private FirebaseDatabase database;
    private MessageAdapter adapter;
    ArrayList<MessageModel> messagess;
    private String sendUID;
    private String jobTitle,jobCompany;

    private String senderRoom;
    private String receiverRoom;
    private String jobIDProposal;
    private String jobIDReceiverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //StringIntent
        username = getIntent().getStringExtra("UserName");
        jobIDProposal = getIntent().getStringExtra("jobIDProposal");
        proposalSenderUID = getIntent().getStringExtra("proposalSendedUID");

        //FirebaseReference
        profileRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        chatPresence = FirebaseDatabase.getInstance().getReference("chatPresence");
        jobRef = FirebaseDatabase.getInstance().getReference("InstantJobs");

        sendUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        chatRef = FirebaseDatabase.getInstance().getReference("chats");
        jobRefInReceiverRoom = FirebaseDatabase.getInstance().getReference("chats");
        chatContactsRef = FirebaseDatabase.getInstance().getReference("UserChatContacts");


        database = FirebaseDatabase.getInstance();

        senderRoom = sendUID + proposalSenderUID;
        receiverRoom = proposalSenderUID + sendUID;

        if (jobIDProposal != null) {
            proposalJobNote(jobIDProposal);
        }


        binding.msgBackBTn.setOnClickListener(v -> onBackPressed());

        chatPresence.child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    if (!status.isEmpty()) {
                        if (status.equals("Offline")) {
                            binding.statusTxtView.setVisibility(View.GONE);
                        } else {
                            binding.statusTxtView.setText(status);
                            binding.statusTxtView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessagesActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });


//        Toast.makeText(this, proposalSenderUID, Toast.LENGTH_SHORT).show();

        binding.offerBtn.setOnClickListener(v -> {
            Intent offetIntent = new Intent(MessagesActivity.this,OfferActivity.class);
            offetIntent.putExtra("recevierIDForOffer",proposalSenderUID);
            offetIntent.putExtra("jobIDForOffer",jobIDProposal);
            offetIntent.putExtra("jobTitleForOffer",jobTitle);
            offetIntent.putExtra("jobCompanyForOffer",jobCompany);

            startActivity(offetIntent);
        });

        userPicAndNameRetrieve();
        messagesRetrieve();
        sendMsgAndSendBtnFunction();
        editBoxNote();

    }

    private void userPicAndNameRetrieve() {
        profileRef.child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userddPic = snapshot.child("UserImage").getValue(String.class);
//                    String budget = snapshot.child("Hourly Charges").getValue(String.class);
                    Glide.with(MessagesActivity.this)
                            .load(userddPic)
                            .placeholder(R.drawable.profileplace)
                            .into(binding.msgChatDP);
//                    holder.binding.statususerbudgetTview.setText(budget);

                } else {
                    Toast.makeText(MessagesActivity.this, "Not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessagesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        binding.msgChatUsername.setText(username);
    }

    private void messagesRetrieve() {
        messagess = new ArrayList<>();
        adapter = new MessageAdapter(this, messagess,senderRoom,receiverRoom);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewChat.setAdapter(adapter);
        binding.recyclerViewChat.setHasFixedSize(true);
        binding.recyclerViewChat.setLayoutManager(linearLayoutManager);


        chatRef.child(senderRoom)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagess.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel message = snapshot1.getValue(MessageModel.class);
                            messagess.add(message);
                            binding.recyclerViewChat.smoothScrollToPosition(messagess.size() - 1);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendMsgAndSendBtnFunction() {

        //sendBtn1Function
        binding.sendBtnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewSendbtn) {

                String messageTxt = binding.msgChatEditBox.getText().toString();
                //for LastMsgTime

                Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.getDefault());
                String stDate = dateFormat.format(date);
                //for LastMsgTime


                //for chatTIme
                Calendar calendar = Calendar.getInstance();

//                 Format the time in 12-hour format with AM/PM
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                String formattedTime2 = timeFormat.format(calendar.getTime());
                //for chatTime


                if (messageTxt.isEmpty()) {
                    binding.msgChatEditBox.setError("Field can't be empty");
                } else {

                    String randomKey = database.getReference().push().getKey();


                    MessageModel message = new MessageModel(messageTxt, sendUID, formattedTime2,randomKey);

                    HashMap<String, Object> lastMsgObj = new HashMap<>();
                    lastMsgObj.put("lastMsg", message.getMessage());
                    lastMsgObj.put("lastMsgTime", stDate);
                    chatRef.child(senderRoom).updateChildren(lastMsgObj);
                    chatRef.child(receiverRoom).updateChildren(lastMsgObj);


                    if (randomKey != null) {
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
//                                                        chatRef.child(senderRoom).child("msgcount").push().setValue("count");
                                                        chatRef.child(receiverRoom).child("msgcount").push().setValue("count");
                                                        binding.msgChatEditBox.getText().clear();
                                                        hideKeyboarddd(viewSendbtn);
//                                                        Toast.makeText(MessagesActivity.this, "Juu", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });
                    }
                }


            }
        });


        //sendBtn2Function
        binding.sendBtnCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageTxt = binding.msgChatEditBox.getText().toString();
                ChatContactModel sendContactModel = new ChatContactModel(proposalSenderUID, jobIDProposal);
                ChatContactModel receiverContactModel = new ChatContactModel(sendUID, jobIDProposal);
                //for LastMsgTime
                Date date = new Date();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.getDefault());
                String stDate = dateFormat.format(date);
                //for LastMsgTime

                //chatime

                Calendar calendar = Calendar.getInstance();

//                 Format the time in 12-hour format with AM/PM
                SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                String formattedTime1 = timeFormat.format(calendar.getTime());
                //chattime


                if (messageTxt.isEmpty()) {
                    binding.msgChatEditBox.setError("Field can't be empty");
                } else {
                    String randomKey = database.getReference().push().getKey();

                    MessageModel message = new MessageModel(messageTxt, sendUID, formattedTime1,randomKey);

                    HashMap<String, Object> lastMsgObj = new HashMap<>();
                    lastMsgObj.put("lastMsg", message.getMessage());
                    lastMsgObj.put("lastMsgTime", stDate);
                    chatRef.child(senderRoom).updateChildren(lastMsgObj);
                    chatRef.child(receiverRoom).updateChildren(lastMsgObj);

//                    chatRef.child(senderRoom).child("msgcount").push();
//                    chatRef.child(receiverRoom).child("msgcount").push();

                    if (randomKey != null) {
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
                                                        chatContactsRef
                                                                .child(sendUID)
                                                                .child(proposalSenderUID)
                                                                .setValue(sendContactModel)
                                                                .addOnCompleteListener(task -> {
                                                                    if (task.isComplete()) {
                                                                        chatContactsRef
                                                                                .child(proposalSenderUID)
                                                                                .child(sendUID)
                                                                                .setValue(receiverContactModel)
                                                                                .addOnCompleteListener(task1 -> {
                                                                                    if (task1.isComplete()) {
//                                                                                        if (jobIDProposal != null) {
//                                                                                            jobRefInReceiverRoom.child(receiverRoom).child("jobIDRef").setValue(jobIDProposal);
//                                                                                        }
                                                                                        binding.msgChatEditBox.getText().clear();
                                                                                        Toast.makeText(MessagesActivity.this, "Juu2", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                })
                                                                                .addOnFailureListener(e ->
                                                                                        Toast.makeText(MessagesActivity.this, e.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show());
                                                                    }
                                                                })
                                                                .addOnFailureListener(e ->
                                                                        Toast.makeText(MessagesActivity.this, e.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show());
                                                    }
                                                });

                                    }
                                });
                    }

                }


            }
        });

    }

    private void editBoxNote() {
        final Handler handler = new Handler();
        binding.msgChatEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.offerBtn.setVisibility(View.GONE);
                if (binding.msgChatEditBox.getText().toString().isEmpty()) {
                    binding.offerBtn.setVisibility(View.VISIBLE);
                    binding.attachBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.offerBtn.setVisibility(View.GONE);
                    binding.attachBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                chatPresence.child(sendUID).setValue("typing...");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(userStoppedTyping, 1000);
            }

            Runnable userStoppedTyping = new Runnable() {
                @Override
                public void run() {
                    chatPresence.child(sendUID).setValue("Online");
                }
            };
        });
    }


    private void proposalJobNote(String jobIDD) {

        jobRef.child(jobIDD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.jobproposalLayout.setVisibility(View.VISIBLE);
                    jobTitle = snapshot.child("inJobTitle").getValue(String.class);
                    jobCompany = snapshot.child("inJobCompany").getValue(String.class);
                    String jobBudget = snapshot.child("inJobBudget").getValue(String.class);
                    String jobImg = snapshot.child("inJobCompanyImgURL").getValue(String.class);

                    binding.proposalJobTitle.setText(jobTitle);
                    binding.proposalJobCompany.setText(jobCompany);
                    binding.proposalJobBudget.setText(jobBudget);

                    Glide.with(MessagesActivity.this)
                            .load(jobImg).placeholder(R.drawable.placeholder).into(binding.proposaljobImg);
                } else {
                    binding.jobproposalLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessagesActivity.this, error.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void hideKeyboarddd(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void statusCheck(String statusTxt) {
        String currentId = FirebaseAuth.getInstance().getUid();
        assert currentId != null;
        chatPresence.child(currentId).setValue(statusTxt);

    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck("Online");
        chatContact();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        String currentId = FirebaseAuth.getInstance().getUid();
//        assert currentId != null;
//        chatPresence.child(currentId).setValue("Offline");
        statusCheck("Offline");

    }

    private void chatContact() {
        chatContactsRef.child(sendUID).child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Toast.makeText(MessagesActivity.this, "Exists", Toast.LENGTH_SHORT).show();
                    binding.sendBtnCard.setVisibility(View.VISIBLE);
                    binding.sendBtnCard2.setVisibility(View.GONE);
                } else {
                    binding.sendBtnCard2.setVisibility(View.VISIBLE);
                    binding.sendBtnCard.setVisibility(View.GONE);

                }
//                    Toast.makeText(MessagesActivity.this, sendUID, Toast.LENGTH_SHORT).show();
//                    for (DataSnapshot s : snapshot.getChildren()) {
//                        ChatContactModel model = s.getValue(ChatContactModel.class);
//                        assert model != null;
//                        if (model.getUserUUID().equals(proposalSenderUID))
//
//                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessagesActivity.this, "Ksss", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        chatContact();

//        chatContactsRef.child(sendUID).child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    Toast.makeText(MessagesActivity.this, "Exists", Toast.LENGTH_SHORT).show();
//                    binding.sendBtnCard.setVisibility(View.VISIBLE);
//                    binding.sendBtnCard2.setVisibility(View.GONE);
//                } else {
//                    binding.sendBtnCard2.setVisibility(View.VISIBLE);
//                    binding.sendBtnCard.setVisibility(View.GONE);
//
//                }
////                    Toast.makeText(MessagesActivity.this, sendUID, Toast.LENGTH_SHORT).show();
////                    for (DataSnapshot s : snapshot.getChildren()) {
////                        ChatContactModel model = s.getValue(ChatContactModel.class);
////                        assert model != null;
////                        if (model.getUserUUID().equals(proposalSenderUID))
////
////                    }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MessagesActivity.this, "Ksss", Toast.LENGTH_SHORT).show();
//            }
//        });


//        chatContactsRef.child(sendUID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot s : snapshot.getChildren()) {
//                        ChatContactModel model = s.getValue(ChatContactModel.class);
//                        assert model != null;
//                        if (model.getUserUUID().equals(proposalSenderUID)) {
//                            binding.sendBtnCard.setVisibility(View.VISIBLE);
//                            binding.sendBtnCard2.setVisibility(View.GONE);
//                        } else {
//                            binding.sendBtnCard2.setVisibility(View.VISIBLE);
//                            binding.sendBtnCard.setVisibility(View.GONE);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MessagesActivity.this, "Ksss", Toast.LENGTH_SHORT).show();
//            }
//        });
        chatRef.child(senderRoom).child("msgcount").removeValue();

    }

    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        loadFragment(new MessagesFragment());
//        proposalSenderUID = null;
//        proposalUIDFromAllUser = null;
//        startActivity(new Intent(MessagesActivity.this, FindJobsActivity.class));
//        finishAffinity();
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}