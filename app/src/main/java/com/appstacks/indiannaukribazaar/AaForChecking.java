package com.appstacks.indiannaukribazaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.GridAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AaForChecking extends AppCompatActivity {

    GridView listView;

    DatabaseReference userReference;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    String userauth;
    GridAdapter gridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aa_for_checking);

//        userauth = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        userReference = FirebaseDatabase.getInstance().getReference("UsersProfile");
//
//
//        listView = (GridView) findViewById(R.id.listvieww);
//
//        userReference.child(userauth).child("Skills").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String value=dataSnapshot.getValue(String.class);
//                arrayList.add(value);
////                arrayAdapter = new ArrayAdapter<String>(AaForChecking.this, android.R.layout.simple_list_item_1, arrayList);
//                gridAdapter = new GridAdapter(AaForChecking.this,arrayList);
//
//                listView.setAdapter(gridAdapter);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//


    }
}