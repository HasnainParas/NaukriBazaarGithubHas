package com.appstacks.indiannaukribazaar.NewActivities.JobStatusActivies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appstacks.indiannaukribazaar.ChatActivities.MessagesActivity;
import com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs.JobAppliedModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.ProfileModels.WholeProfileModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.PropasalLayoutBinding;
import com.appstacks.indiannaukribazaar.profile.ViewProfileActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProposalsAdapter extends RecyclerView.Adapter<ProposalsAdapter.viewHolderss> {

    ArrayList<JobAppliedModel> modelArrayList;
    Context context;
    private String appliedUserID;
    private String userPic;
    UserDataModel userDataModel;
    private String proposalSenderUID;
  private   AboutMeDescription aboutme=null;
  private String userImage;
  private AddWorkExperience workExperience=null;
    private DatabaseReference allUserRef, appliedUserProfileRef;

    public ProposalsAdapter(ArrayList<JobAppliedModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolderss onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.propasal_layout, parent, false);
        return new ProposalsAdapter.viewHolderss(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolderss holder, int position) {
        JobAppliedModel model = modelArrayList.get(position);

        holder.binding.proposaName.setText(model.getAppliedUserAuthID());
        allUserRef = FirebaseDatabase.getInstance().getReference("AllUsers");
        appliedUserProfileRef = FirebaseDatabase.getInstance().getReference("UsersProfile");

        appliedUserID = model.getAppliedUserAuthID();


        allUserRef.child(model.getAppliedUserAuthID()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDataModel userDataModel = snapshot.getValue(UserDataModel.class);

                    holder.binding.proposaName.setText(userDataModel.getFullName());
                    holder.binding.statusUserEmailTxt.setText(userDataModel.getEmailAddress());
//                    proposalSenderUID = userDataModel.getUuID();

                } else {
                    holder.binding.proposaName.setText("Not exist");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        appliedUserProfileRef.child(model.getAppliedUserAuthID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userPic = snapshot.child("UserImage").getValue(String.class);
//                    String budget = snapshot.child("Hourly Charges").getValue(String.class);
                    try {
                        Glide.with(context)
                                .load(userPic)
                                .placeholder(R.drawable.placeholder)
                                .into(holder.binding.proposalPic);
                    }catch (IllegalArgumentException e){

                    }
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






        holder.binding.statusUserChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model.getAppliedUserAuthID()+" ", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(context, MessagesActivity.class);
               // in.putExtra("UserPic", getProfilePic(model.getAppliedUserAuthID()));

                in.putExtra("UserName", holder.binding.proposaName.getText().toString());
                in.putExtra("proposalSendedUID", model.getAppliedUserAuthID());
                context.startActivity(in);
                ((Activity)context).finish();

            }
        });

        holder.binding.statusUserDetailsBtn.setOnClickListener(view -> {

            Intent intent= new Intent(context, ViewProfileActivity.class);
            intent.putExtra("userId",model.getAppliedUserAuthID());
            context.startActivity(intent);

        });

    }



    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class viewHolderss extends RecyclerView.ViewHolder {
        PropasalLayoutBinding binding;

        public viewHolderss(@NonNull View itemView) {

            super(itemView);
            binding = PropasalLayoutBinding.bind(itemView);

        }
    }




}
