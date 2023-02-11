package com.appstacks.indiannaukribazaar.profile.skills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.GridAdapter;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAllSkillsBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileEditActivity;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AllSkillsActivity extends AppCompatActivity {
    private ActivityAllSkillsBinding binding;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private DatabaseReference databaseReference;
    private String userId;
    private ProgressDialog dialog;
    private ProfileUtils profileUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllSkillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Adding skills");
        dialog.setCancelable(false);

        profileUtils = new ProfileUtils(this);

        binding.btnBackSkills.setOnClickListener(view -> {
            finish();
        });

        list = getIntent().getStringArrayListExtra("data");
        binding.txtTitleSkills.setText("Skills (" + list.size() + ")");
        setupGridView();

        binding.btnSaveSkills.setOnClickListener(view -> {
            uploadSkills(list);
        });

    }
//

    private void fetchSkills() {
        binding.waitingLayout.setVisibility(View.GONE);
        binding.mainLayoutSkill.setVisibility(View.VISIBLE);
        databaseReference.child(userId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    list = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list.add(obj);


                    }

                    adapter = new ArrayAdapter<String>(AllSkillsActivity.this, R.layout.item_skil, R.id.item_skill, list);

                    binding.gridViewSkills.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void uploadSkills(ArrayList<String> list) {


        dialog.show();

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Skills").setValue(list)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            profileUtils.saveSkills(list);
                            Toast.makeText(AllSkillsActivity.this, "Skills added", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AllSkillsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void setupGridView() {

        adapter = new ArrayAdapter<String>(AllSkillsActivity.this, R.layout.item_skil, R.id.item_skill, list);

        binding.gridViewSkills.setAdapter(adapter);

        binding.gridViewSkills.setOnItemClickListener((adapterView, view, i, l) -> {

            list.remove(i);

            adapter.notifyDataSetChanged();

            adapter.notifyDataSetInvalidated();

            binding.gridViewSkills.setOnItemLongClickListener((adapterView1, view1, i1, l1) -> {
                adapterView1.animate();
                return false;
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchSkills();
    }
}