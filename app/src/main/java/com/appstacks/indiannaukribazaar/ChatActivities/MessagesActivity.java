package com.appstacks.indiannaukribazaar.ChatActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnSuccessListener;
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


   private ActivityMessagesBinding binding;
    private String username, userpic;
    //    private String receiverUid;
    private String proposalSenderUID;
    private String proposalUIDFromAllUser;
    private DatabaseReference chatRef, chatContactsRef,userProfile;
    private FirebaseDatabase database;
   private MessageAdapter adapter;
private     ArrayList<MessageModel> messagess;
  private   String sendUID;

  private   String senderRoom;
 private    String receiverRoom;
private String userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = getIntent().getStringExtra("UserName");
        userpic = getIntent().getStringExtra("UserPic");
        proposalSenderUID = getIntent().getStringExtra("proposalSendedUID");
        proposalUIDFromAllUser = getIntent().getStringExtra("proposalUIdFromAllUser");

        sendUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

//        if (proposalSenderUID == null) {
//            proposalSenderUID = proposalUIDFromAllUser;
//        }

        chatRef = FirebaseDatabase.getInstance().getReference("chats");
        chatContactsRef = FirebaseDatabase.getInstance().getReference("UserChatContacts");
        userProfile=FirebaseDatabase.getInstance().getReference("UsersProfile");
//        chatContactsRef = FirebaseDatabase.getInstance().getReference();

        database = FirebaseDatabase.getInstance();

getProfilePic(proposalSenderUID);

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
//
//                        }
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MessagesActivity.this, "Ksss", Toast.LENGTH_SHORT).show();
//            }
//        });


//        chatRef.child(senderRoom)
//                .child("messages")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        messagess.clear();
//                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                            MessageModel message = snapshot1.getValue(MessageModel.class);
//                            messagess.add(message);
//                            binding.recyclerViewChat.smoothScrollToPosition(messagess.size() - 1);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


//        binding.sendBtnCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String messageTxt = binding.msgChatEditBox.getText().toString();
//
//                String randomKey = database.getReference().push().getKey();
//
//                MessageModel message = new MessageModel(messageTxt, sendUID);
//
//
//                if (randomKey != null) {
//                    chatRef.child(senderRoom)
//                            .child("messages")
//                            .child(randomKey)
//                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
////                                    database.getReference().child("chats")
//                                    chatRef.child(receiverRoom)
//                                            .child("messages")
//                                            .child(randomKey)
//                                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//
//                                                    binding.msgChatEditBox.getText().clear();
//                                                    Toast.makeText(MessagesActivity.this, "Juu", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                }
//                            });
//                }
//
//            }
//        });
        binding.sendBtnCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // old code
                String stenderRoom = sendUID + "-" + proposalSenderUID;
                String rteceiverRoom = proposalSenderUID + "-" + sendUID;


                chatContactsRef.child(stenderRoom).setValue(sendUID);
                chatContactsRef.child(rteceiverRoom).setValue(proposalSenderUID);
                /*
                String messageTxt = binding.msgChatEditBox.getText().toString();

                ChatContactModel sendContactModel = new ChatContactModel(proposalSenderUID);
                ChatContactModel receiverContactModel = new ChatContactModel(sendUID);

                chatContactsRef.child(sendUID).push().setValue(sendContactModel);
                chatContactsRef.child(proposalSenderUID).push().setValue(receiverContactModel);


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
                                                    Toast.makeText(MessagesActivity.this, "Juu2", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            });
                }


                 */

            }
        });


    }
    private void loadFragment(Fragment fragment) {
        // load fragment
//        binding.frameContainer.setVisibility(View.VISIBLE);
//        binding.frameContainer2.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
    private void getProfilePic(String userId) {

        userProfile.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userImage = snapshot.child("UserImage").getValue(String.class);
//                    String budget = snapshot.child("Hourly Charges").getValue(String.class);
                    Glide.with(MessagesActivity.this)
                            .load(userImage)
                            .placeholder(R.drawable.profileplace)
                            .into(binding.msgChatDP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



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