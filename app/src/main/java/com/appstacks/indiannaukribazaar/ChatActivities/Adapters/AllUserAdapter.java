package com.appstacks.indiannaukribazaar.ChatActivities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.ChatActivities.MessagesActivity;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.ChatContactModel;
import com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies.ProposalsAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.UserChatSampleBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.viewHolder> {

    ArrayList<ChatContactModel> modelArrayList;
    Context context;
    private DatabaseReference allUserRef;
    private DatabaseReference appliedUserProfileRef;
    private DatabaseReference chatPresence;
    private String receiverUUid;
    private String userPic;

    public AllUserAdapter(ArrayList<ChatContactModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_chat_sample, parent, false);
        return new viewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ChatContactModel model = modelArrayList.get(position);

        allUserRef = FirebaseDatabase.getInstance().getReference("AllUsers");
        appliedUserProfileRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        chatPresence = FirebaseDatabase.getInstance().getReference("chatPresence");

//        holder.binding.chatUserName.setText(model.getUserUUID());
        receiverUUid = model.getUserUUID();

        String senderID = FirebaseAuth.getInstance().getUid();

        String senderROOM = senderID + model.getUserUUID();
        String receiveROOM = model.getUserUUID() + senderID;

        FirebaseDatabase.getInstance().getReference().
                child("chats").
                child(senderROOM).
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lastMsg = snapshot.child("lastMsg").getValue(String.class);
                            String lastMsgTime = snapshot.child("lastMsgTime").getValue(String.class);
//                            holder.binding.userConstraintLayoutClick.setOnClickListener(v -> {
//                                Toast.makeText(context, lastMsgTime, Toast.LENGTH_SHORT).show();
//                            });
                            holder.binding.chatUserLastMessage.setText(lastMsg);
                            String totaltime = calculateTimeAgoo(lastMsgTime);
                            holder.binding.chatUserTimetxt.setText(totaltime);
                        } else {
                            holder.binding.chatUserLastMessage.setText("Tap to chat");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        FirebaseDatabase.getInstance().getReference().
                child("chats").
                child(senderROOM).child("msgcount").
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            holder.binding.chatUserLastMessage.setTypeface(Typeface.DEFAULT_BOLD);
                            holder.binding.textView98.setVisibility(View.VISIBLE);
                            int countsize = (int) snapshot.getChildrenCount();
                            holder.binding.textView98.setText(countsize + "");

                        } else {
                            holder.binding.textView98.setVisibility(View.GONE);
                            holder.binding.chatUserLastMessage.setTypeface(Typeface.DEFAULT);
                            Toast.makeText(context, "Juuu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        holder.binding.userConstraintLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, MessagesActivity.class);
//                in.putExtra("UserPic",model.getUserUUID());
                in.putExtra("UserName", holder.binding.chatUserName.getText().toString());
                in.putExtra("proposalSendedUID", model.getUserUUID());
                in.putExtra("jobIDProposal", model.getJobID());
//                in.putExtra("proposalUIdFromAllUser",model.getUserUUID());
                context.startActivity(in);

                Toast.makeText(context, model.getUserUUID(), Toast.LENGTH_SHORT).show();

            }
        });


        allUserRef.child(receiverUUid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDataModel userDataModel = snapshot.getValue(UserDataModel.class);

                    holder.binding.chatUserName.setText(userDataModel.getFullName());
//                    holder.binding.statusUserEmailTxt.setText(userDataModel.getEmailAddress());
//                    proposalSenderUID = userDataModel.getUuID();

                } else {
                    holder.binding.chatUserName.setText("Not exist");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        appliedUserProfileRef.child(receiverUUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userPic = snapshot.child("UserImage").getValue(String.class);
                    String budget = snapshot.child("Hourly Charges").getValue(String.class);

                    Glide.with(context)
                            .load(userPic)
                            .placeholder(R.drawable.placeholder)
                            .into(holder.binding.chatUserProfilePic);

//                    holder.binding.statususerbudgetTview.setText(budget);

                } else {
                    Toast.makeText(context, "Not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        chatPresence.child(model.getUserUUID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String status = snapshot.getValue(String.class);
                    assert status != null;
                    if (status.equals("Offline")) {
                        holder.binding.onlineDot.setVisibility(View.GONE);
                    } else {
//                            binding.statusTxtView.setText(status);
                        holder.binding.onlineDot.setVisibility(View.VISIBLE);
                    }
//                    if (!status.isEmpty()) {
//                        if (status.equals("Offline")) {
//                            holder.binding.onlineDot.setVisibility(View.GONE);
//                        } else {
////                            binding.statusTxtView.setText(status);
//                            holder.binding.onlineDot.setVisibility(View.VISIBLE);
//                        }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private String calculateTimeAgoo(String lastMsgTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.getDefault());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

        try {
            long time = sdf.parse(lastMsgTime).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        UserChatSampleBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserChatSampleBinding.bind(itemView);
        }
    }


//    private void statusCheck(String statusTxt) {
//        String currentId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//        chatPresence.child(currentId).setValue(statusTxt);
//
//    }
//    public void onResume() {
//        // Perform actions when the adapter's parent activity resumes
//        statusCheck("Online");
//    }
//
//    // onPause method
//    public void onPause() {
//        statusCheck("Offline");
//        // Perform actions when the adapter's parent activity pauses
//    }

}
