package com.appstacks.indiannaukribazaar.ChatActivities.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.viewHolder> {

    ArrayList<ChatContactModel> modelArrayList;
    Context context;
    private DatabaseReference allUserRef;
    private DatabaseReference  appliedUserProfileRef;

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

//        holder.binding.chatUserName.setText(model.getUserUUID());
        receiverUUid = model.getUserUUID();




        holder.binding.userConstraintLayoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, MessagesActivity.class);
                in.putExtra("UserPic",userPic);
                in.putExtra("UserName",holder.binding.chatUserName.getText().toString());
                in.putExtra("proposalUIdFromAllUser",model.getUserUUID());
                context.startActivity(in);

            }
        });



        allUserRef.child(receiverUUid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserDataModel userDataModel = snapshot.getValue(UserDataModel.class);

                    holder.binding.chatUserName.setText(userDataModel.getFullName());
//                    holder.binding.statusUserEmailTxt.setText(userDataModel.getEmailAddress());
//                    proposalSenderUID = userDataModel.getUuID();

                }else {
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
                if (snapshot.exists()){
                    userPic  = snapshot.child("UserImage").getValue(String.class);
                    String budget  = snapshot.child("Hourly Charges").getValue(String.class);

                    Glide.with(context)
                            .load(userPic)
                            .placeholder(R.drawable.placeholder)
                            .into(holder.binding.chatUserProfilePic);

//                    holder.binding.statususerbudgetTview.setText(budget);

                }else {
                    Toast.makeText(context, "Not", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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


}
