package com.appstacks.indiannaukribazaar.profile.skills;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;


import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivitySkillsBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileEditActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkillsActivity extends AppCompatActivity {
    private ActivitySkillsBinding binding;
    private ArrayList<String> skillsList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> gridAdapter;
    private ArrayList<String> gridSkillsList = new ArrayList<>();
    private DatabaseReference databaseReference;
    private String userId;
    private ProgressDialog dialog;
    ArrayList<Skills> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySkillsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Adding skills");
        dialog.setCancelable(false);
        gridSkillsList.clear();
        skillsList.add("Summarizing");
        skillsList.add("Patience");
        skillsList.add("Storytelling");
        skillsList.add("Editing");
        skillsList.add("Written communication ");
        skillsList.add("Open-mindedness");
        skillsList.add("Inquiring");
        skillsList.add("Coaching");
        skillsList.add("Presenting");
        skillsList.add("Summarizing");
        skillsList.add("Documenting");

        searchView(skillsList);


        binding.btnBackSkills.setOnClickListener(view -> {
            finish();
        });
        binding.btnSaveSkills.setOnClickListener(view -> {
            if (gridSkillsList.isEmpty()) {
                Toast.makeText(this, "Select any skill", Toast.LENGTH_SHORT).show();
            } else {
//                list = new ArrayList<>();
//
//                for (int i = 0; i < gridSkillsList.size(); i++) {
//                    list.add(new Skills(String.valueOf(i), gridSkillsList.get(i)));
//                }
                uploadSkills(gridSkillsList);

                //Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
            }
        });


        binding.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
            String instituteName = skillsList.get(i).toString();
            if (gridSkillsList.isEmpty()) {
                gridSkillsList.add(instituteName);
                gridAdapter.notifyDataSetChanged();

                gridAdapter.notifyDataSetInvalidated();
            } else if (notExists(i)) {
                gridSkillsList.add(instituteName);
                gridAdapter.notifyDataSetChanged();

                gridAdapter.notifyDataSetInvalidated();
            } else {
                Toast.makeText(this, "Already added try selecting different options", Toast.LENGTH_SHORT).show();
            }


        });


    }
    private void fetchSkills() {

        databaseReference.child(userId).child("Skills").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                   ArrayList<String> list1 = new ArrayList<>();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String obj = snap.getValue(String.class);
                        list1.add(obj);


                    }

                    adapter = new ArrayAdapter<String>(SkillsActivity.this,R.layout.grid_item,R.id.tvidd,list1);

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
                                Toast.makeText(SkillsActivity.this, "Skills added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SkillsActivity.this, AllSkillsActivity.class);

                                intent.putExtra("data", gridSkillsList);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(SkillsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




    }

    private boolean notExists(int position) {
        boolean result = false;
        for (int j = 0; j < gridSkillsList.size(); j++) {
            if (!skillsList.get(position).equals(gridSkillsList.get(j))) {
                result = true;
            } else {
                Toast.makeText(this, skillsList.get(position) + "Exists in " + j, Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

    private void setupGridView() {

        gridAdapter = new ArrayAdapter<String>(this, R.layout.item_skil, R.id.item_skill, gridSkillsList);
        binding.gridViewSkills.setAdapter(gridAdapter);
        binding.gridViewSkills.setOnItemClickListener((adapterView, view, i, l) -> {

            gridSkillsList.remove(i);

            gridAdapter.notifyDataSetChanged();

            gridAdapter.notifyDataSetInvalidated();

            binding.gridViewSkills.setOnItemLongClickListener((adapterView1, view1, i1, l1) -> {
                adapterView1.animate();
                return false;
            });
        });
    }

    private void searchView(ArrayList<String> list) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        binding.listViewSearchData.setAdapter(adapter);
        binding.searchViewSearchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchSkills();
        setupGridView();


    }
}