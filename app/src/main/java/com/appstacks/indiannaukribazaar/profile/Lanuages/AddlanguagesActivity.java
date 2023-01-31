package com.appstacks.indiannaukribazaar.profile.Lanuages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.EditProfileActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddlanguagesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddlanguagesActivity extends AppCompatActivity {
    private ActivityAddlanguagesBinding binding;
    private DatabaseReference userRef;
    private String userId;
    private ArrayList<SelectedLanguages> list;
    private LanguagesAdapterFirebase adapterFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddlanguagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userRef = FirebaseDatabase.getInstance().getReference("UsersProfile");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchLanguage();
        binding.btnAddLanguage1.setOnClickListener(view -> {
            startActivity(new Intent(AddlanguagesActivity.this, SearchLanguagesActivity.class));
        });

        binding.btnBackAddLanguage1.setOnClickListener(view -> {
            finish();
        });
    }

    private void fetchLanguage() {
        userRef.child(userId).child("Languages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list = new ArrayList<>();
                    list.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        SelectedLanguages languages = snap.getValue(SelectedLanguages.class);
                        assert languages != null;
                        list.add(languages);


                    }
                    adapterFirebase = new LanguagesAdapterFirebase(list, AddlanguagesActivity.this);
                    binding.addedLanguageRecyclerview.setLayoutManager(new LinearLayoutManager(AddlanguagesActivity.this));
                    binding.addedLanguageRecyclerview.setAdapter(adapterFirebase);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddlanguagesActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}