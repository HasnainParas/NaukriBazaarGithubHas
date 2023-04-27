package com.appstacks.indiannaukribazaar.ChatActivities.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.ChatActivities.Adapters.AllUserAdapter;
import com.appstacks.indiannaukribazaar.ChatActivities.Adapters.MessageAdapter;
import com.appstacks.indiannaukribazaar.ChatActivities.Models.ChatContactModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserDataModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.FragmentMessagesBinding;
import com.appstacks.indiannaukribazaar.databinding.FragmentWellDoneBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesFragment extends Fragment {

    FragmentMessagesBinding binding;
    private DatabaseReference allUsersRef, chatContactRef;
    private String chatConatact;
    private String currentUser;

    AllUserAdapter adapter;
    ArrayList<ChatContactModel> modelArrayList;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMessagesBinding.inflate(inflater, container, false);
        currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        allUsersRef = FirebaseDatabase.getInstance().getReference("AllUsers");
        chatContactRef = FirebaseDatabase.getInstance().getReference("UserChatContacts");

        modelArrayList = new ArrayList<>();

//        adapter = new AllUserAdapter(modelArrayList,getContext());
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        binding.allUserRec.setAdapter(adapter);
//        binding.allUserRec.setHasFixedSize(true);
//        binding.allUserRec.setLayoutManager(linearLayoutManager);

        chatContactRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    chatConatact = snapshot.child("uuID").getValue(String.class);
//                    Toast.makeText(getContext(), chatConatact + " ", Toast.LENGTH_SHORT).show();
//                }

                if (snapshot.exists()) {
                    Toast.makeText(getContext(), "YesExist", Toast.LENGTH_SHORT).show();
                for (DataSnapshot s : snapshot.getChildren()){
                    ChatContactModel chatContactModel = s.getValue(ChatContactModel.class);
                    modelArrayList.add(chatContactModel);
                    adapter = new AllUserAdapter(modelArrayList,getContext());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.allUserRec.setAdapter(adapter);
                    binding.allUserRec.setHasFixedSize(true);
                    binding.allUserRec.setLayoutManager(linearLayoutManager);

                }


//                    holder.binding.proposaName.setText(userDataModel.getFullName());
//                    holder.binding.statusUserEmailTxt.setText(userDataModel.getEmailAddress());
//                    proposalSenderUID = userDataModel.getUuID();

                } else {
//                    holder.binding.proposaName.setText("Not exist");
                    Toast.makeText(getContext(), "oPPs", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//        chatContactRef.child(chatConatact).addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    ChatContactModel chatContactModel = snapshot.getValue(ChatContactModel.class);
//                    modelArrayList.add(chatContactModel);
//
//
////                    holder.binding.proposaName.setText(userDataModel.getFullName());
////                    holder.binding.statusUserEmailTxt.setText(userDataModel.getEmailAddress());
////                    proposalSenderUID = userDataModel.getUuID();
//
//                } else {
////                    holder.binding.proposaName.setText("Not exist");
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();
    }
}