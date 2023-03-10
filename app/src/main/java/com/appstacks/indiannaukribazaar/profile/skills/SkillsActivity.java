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
        skillsList.add("Editing");
        skillsList.add("Written communication ");
        skillsList.add("Open-mindedness");
        skillsList.add("Inquiring");
        skillsList.add("Coaching");
        skillsList.add("Presenting");
        skillsList.add("Summarizing");
        skillsList.add("Documenting");
        skillsList.add("Active listening");
        skillsList.add("Clear and concise writing");
        skillsList.add("Verbal communication");
        skillsList.add("Nonverbal communication");
        skillsList.add("Interpersonal communication");
        skillsList.add("Presentation skills");
        skillsList.add("Conflict resolution");
        skillsList.add("Negotiation");
        skillsList.add("Logical reasoning");
        skillsList.add("Analytical skills");
        skillsList.add("Decision-making");
        skillsList.add("Problem-solving");
        skillsList.add("Creative thinking");
        skillsList.add("Innovation");
        skillsList.add("Conceptual thinking");
        skillsList.add("Evaluating evidence");
        skillsList.add("Synthesizing information");
        skillsList.add("Idea generation");
        skillsList.add("Brainstorming Imagination");
        skillsList.add("Artistic ability");
        skillsList.add("Design skills");
        skillsList.add("Inventing Curiosity");
        skillsList.add("Risk-taking");
        skillsList.add("Collaboration");
        skillsList.add("Team building");
        skillsList.add("Team communication");
        skillsList.add("Conflict resolution");
        skillsList.add("Leadership");
        skillsList.add("Diversity and inclusion Delegation");
        skillsList.add("Trust-building");
        skillsList.add("Flexibility");
        skillsList.add("Vision-setting");
        skillsList.add("Goal-setting");
        skillsList.add("Strategy development");
        skillsList.add("Decision-making");
        skillsList.add("Effective communication");
        skillsList.add("Conflict resolution");
        skillsList.add("Empathy");
        skillsList.add("Time management");
        skillsList.add("Mentoring and coaching");
        skillsList.add("Prioritization Planning");
        skillsList.add("Scheduling Organization");
        skillsList.add("Delegation Focus");
        skillsList.add("Self-motivation");
        skillsList.add("Meeting deadlines");
        skillsList.add("Multitasking");
        skillsList.add("Flexibility Resilience");
        skillsList.add("Openness to change");
        skillsList.add("Learning agility");
        skillsList.add("Risk-taking");
        skillsList.add("Creativity");
        skillsList.add("Problem-solving");
        skillsList.add("Cultural awareness");
        skillsList.add("Cross-functional collaboration");
        skillsList.add("Analyzing data");
        skillsList.add("Identifying problems");
        skillsList.add("Root cause analysis");
        skillsList.add("Developing solutions");
        skillsList.add("Decision-making");
        skillsList.add("Critical thinking");
        skillsList.add("Strategic thinking");
        skillsList.add("Evaluating alternatives");
        skillsList.add("Innovation");
        skillsList.add("Analyzing data");
        skillsList.add("Weighing pros and cons");
        skillsList.add("Identifying alternatives");
        skillsList.add("Considering risks");
        skillsList.add("Making informed decisions");
        skillsList.add("Rational decision-making");
        skillsList.add("Intuitive decision-making");
        skillsList.add("Ethical decision-making");
        skillsList.add("Judgment");
        skillsList.add("Computer skills");
        skillsList.add("Coding");
        skillsList.add("Data analysis");
        skillsList.add("Programming");
        skillsList.add("Design skills");
        skillsList.add("Writing skills");
        skillsList.add("Marketing skills");
        skillsList.add("Financial analysis");
        skillsList.add("Industry-specific knowledge");
        skillsList.add("Time management");
        skillsList.add("Planning and scheduling Prioritization");
        skillsList.add("Multitasking");
        skillsList.add("Attention to detail");
        skillsList.add("Record-keeping");
        skillsList.add("Delegation");
        skillsList.add("Project management");
        skillsList.add("Meeting deadlines");
        skillsList.add("Thoroughness Accuracy");
        skillsList.add("Precision Focus");
        skillsList.add("Perfectionism Consistency");
        skillsList.add("Critical thinking");
        skillsList.add("Data analysis");
        skillsList.add("Following procedures");
        skillsList.add("Emotional intelligence");
        skillsList.add("Active listening");
        skillsList.add("Empathy");
        skillsList.add("Positive attitude");
        skillsList.add("Conflict resolution");
        skillsList.add("Relationship building");
        skillsList.add("Teamwork Collaboration");
        skillsList.add("Communication");
        skillsList.add("Communication skills");
        skillsList.add("Problem-solving");
        skillsList.add("Patience Empathy");
        skillsList.add("Active listening");
        skillsList.add("Conflict resolution");
        skillsList.add("Attention to detail");
        skillsList.add("Product knowledge");
        skillsList.add("Adaptability");
        skillsList.add("Persuasion Negotiation");
        skillsList.add("Relationship building");
        skillsList.add("Product knowledge");
        skillsList.add("Closing deals");
        skillsList.add("Follow-up");
        skillsList.add("Time management");
        skillsList.add("Goal setting");
        skillsList.add("Market research");
        skillsList.add("Customer analysis");
        skillsList.add("Campaign planning");
        skillsList.add("Brand management");
        skillsList.add("Advertising");
        skillsList.add("Public relations");
        skillsList.add("Social media management Analytics");
        skillsList.add("Creative thinking");
        skillsList.add("Accounting");
        skillsList.add("Financial analysis");
        skillsList.add("Budgeting Forecasting");
        skillsList.add("Reporting");
        skillsList.add("Tax management");
        skillsList.add("Investment management");
        skillsList.add("Risk management");
        skillsList.add("Cash flow management");
        skillsList.add("Planning Budgeting");
        skillsList.add("Risk management");
        skillsList.add("Resource management");

        searchView(skillsList);


        binding.btnBackSkills.setOnClickListener(view -> {
            finish();
        });
        binding.btnSaveSkills.setOnClickListener(view -> {
            if (gridSkillsList.isEmpty()) {
                Toast.makeText(this, "Select any skill", Toast.LENGTH_SHORT).show();
            } else {

                uploadSkills(gridSkillsList);

            }
        });


        binding.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
            String instituteName = skillsList.get(i).toString();

            if (gridSkillsList.contains(instituteName)){
                Toast.makeText(this, "Already added this skill try selecting another one", Toast.LENGTH_SHORT).show();
            }else {
                gridSkillsList.add(instituteName);
                gridAdapter.notifyDataSetChanged();
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