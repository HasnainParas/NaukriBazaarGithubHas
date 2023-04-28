package com.appstacks.indiannaukribazaar.ChatActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ChatActivities.Adapters.MessageAdapter;
import com.appstacks.indiannaukribazaar.ChatActivities.Fragments.MessagesFragment;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.ChatContactModel;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.MessageModel;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityMessagesBinding;
import com.bumptech.glide.Glide;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {


    ActivityMessagesBinding binding;
    private String username;
    //    private String receiverUid;
    private String proposalSenderUID;
    //    private String proposalUIDFromAllUser;
    private DatabaseReference chatRef, chatContactsRef, profileRef;
    private DatabaseReference chatPresence;
    private FirebaseDatabase database;
    MessageAdapter adapter;
    ArrayList<MessageModel> messagess;
    String sendUID;

    String senderRoom;
    String receiverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = getIntent().getStringExtra("UserName");
//        userpic = getIntent().getStringExtra("UserPic");
        proposalSenderUID = getIntent().getStringExtra("proposalSendedUID");
//        proposalUIDFromAllUser = getIntent().getStringExtra("proposalUIdFromAllUser");
        profileRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        chatPresence = FirebaseDatabase.getInstance().getReference("chatPresence");

        sendUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

//        if (proposalSenderUID == null) {
//            proposalSenderUID = proposalUIDFromAllUser;
//        }

        chatRef = FirebaseDatabase.getInstance().getReference("chats");
        chatContactsRef = FirebaseDatabase.getInstance().getReference("UserChatContacts");
//        chatContactsRef = FirebaseDatabase.getInstance().getReference();

        database = FirebaseDatabase.getInstance();

//        Glide.with(this)
//                .load(userpic)
//                .placeholder(R.drawable.profileplace)
//                .into(binding.msgChatDP);

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

            }
        });


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
        Toast.makeText(this, proposalSenderUID, Toast.LENGTH_SHORT).show();

        senderRoom = sendUID + proposalSenderUID;
        receiverRoom = proposalSenderUID + sendUID;

//        ChatContactModel sendContactModel = new ChatContactModel(proposalSenderUID);
//        ChatContactModel receiverContactModel = new ChatContactModel(sendUID);
//


        messagess = new ArrayList<>();
        adapter = new MessageAdapter(this, messagess);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewChat.setAdapter(adapter);
        binding.recyclerViewChat.setHasFixedSize(true);
        binding.recyclerViewChat.setLayoutManager(linearLayoutManager);

        chatContactsRef.child(sendUID).child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MessagesActivity.this, "Exists", Toast.LENGTH_SHORT).show();
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


        chatRef.child(senderRoom)
                        .

                child("messages")
                        .

                addValueEventListener(new ValueEventListener() {
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


        binding.sendBtnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageTxt = binding.msgChatEditBox.getText().toString();

                if (messageTxt.isEmpty()) {
                    binding.msgChatEditBox.setError("Field can't be empty");
                } else {

                    String randomKey = database.getReference().push().getKey();

                    MessageModel message = new MessageModel(messageTxt, sendUID);


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

                                                        binding.msgChatEditBox.getText().clear();
                                                        Toast.makeText(MessagesActivity.this, "Juu", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });
                    }
                }


            }
        });
        binding.sendBtnCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // old code
//                String stenderRoom = sendUID + "-" + proposalSenderUID;
//                String rteceiverRoom = proposalSenderUID + "-" + sendUID;
//
//
//                chatContactsRef.child(stenderRoom).setValue(sendUID);
//                chatContactsRef.child(rteceiverRoom).setValue(proposalSenderUID);
//                /*
                String messageTxt = binding.msgChatEditBox.getText().toString();
                ChatContactModel sendContactModel = new ChatContactModel(proposalSenderUID);
                ChatContactModel receiverContactModel = new ChatContactModel(sendUID);
                if (messageTxt.isEmpty()) {
                    binding.msgChatEditBox.setError("Field can't be empty");
                } else {
                    String randomKey = database.getReference().push().getKey();

                    MessageModel message = new MessageModel(messageTxt, sendUID);


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
                                                        chatContactsRef.child(sendUID).child(proposalSenderUID).setValue(sendContactModel).addOnCompleteListener(task -> {
                                                            if (task.isComplete()) {
                                                                chatContactsRef.child(proposalSenderUID).child(sendUID).setValue(receiverContactModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isComplete()) {
                                                                            binding.msgChatEditBox.getText().clear();
                                                                            Toast.makeText(MessagesActivity.this, "Juu2", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                });
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {

                                                            }
                                                        });


                                                    }
                                                });

                                    }
                                });
                    }

                }


            }
        });

        final Handler handler = new Handler();
        binding.msgChatEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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


    private void statusCheck(String statusTxt) {
        String currentId = FirebaseAuth.getInstance().getUid();
        assert currentId != null;
        chatPresence.child(currentId).setValue(statusTxt);

    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        String currentId = FirebaseAuth.getInstance().getUid();
//        assert currentId != null;
//        chatPresence.child(currentId).setValue("Offline");
        statusCheck("Offline");

    }


    @Override
    protected void onStart() {
        super.onStart();

        chatContactsRef.child(sendUID).child(proposalSenderUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(MessagesActivity.this, "Exists", Toast.LENGTH_SHORT).show();
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
}