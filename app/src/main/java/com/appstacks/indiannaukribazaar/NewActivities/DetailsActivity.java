package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityDetailsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    View aboutlayout;
    String profileIntent;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    String levelOfEducation, instituteName, filedOfStudy;
    public static ArrayList<String> listOptions;
    ArrayList<String> skills;

    String selectedSkill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        aboutlayout = findViewById(R.id.aboutidlay);

        profileIntent = getIntent().getStringExtra("profile");
        switch (profileIntent) {
            case "About Me":
                binding.aboutidlay.getRoot().setVisibility(View.VISIBLE);
                binding.aboutidlay.btnBackAboutMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                binding.aboutidlay.etTellmeAbout.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Toast.makeText(DetailsActivity.this, "Before", Toast.LENGTH_SHORT).show();
                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_arrow_back_black_24dp);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Toast.makeText(DetailsActivity.this, "Changing", Toast.LENGTH_SHORT).show();
                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_cancel);
                        binding.aboutidlay.btnBackAboutMe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(DetailsActivity.this, "On Text Chaged Cacel", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        Toast.makeText(DetailsActivity.this, "Changed", Toast.LENGTH_SHORT).show();
                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_cancel);
                        binding.aboutidlay.btnBackAboutMe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bottomDialog();
                            }
                        });
                    }
                });
                binding.aboutidlay.btnSaveAboutMe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String AboutMeDescription = binding.aboutidlay.etTellmeAbout.getText().toString();
                    }
                });
                break;
            case "Add Work":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.btnRemoveWorkEx.setVisibility(View.GONE);
                break;
            case "Edit Work":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.txtaddworkexperience.setText("Change Work Experience");
                binding.addworklay.btnRemoveWorkEx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetDialog dialog = new BottomSheetDialog(DetailsActivity.this, R.style.AppBottomSheetDialogTheme);
                        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                                inflate(R.layout.undo_changes_layout, (CardView) findViewById(R.id.UndoChanges));
                        dialog.setContentView(bottomsheetView);
                        dialog.show();
                        TextView title = bottomsheetView.findViewById(R.id.textTitle_dialog);
                        TextView subtitle = bottomsheetView.findViewById(R.id.textSubtitle_dialog);
                        Button confirm = bottomsheetView.findViewById(R.id.btnContinue_dialog);
                        Button cancel = bottomsheetView.findViewById(R.id.btnUndo_dialog);
                        confirm.setText("Yes");
                        cancel.setText("Cancel");
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        title.setText("Remove Work Experience?");
                        subtitle.setText("Are you sure you want to delete this work experience?");
                    }
                });
                break;
            case "Add Education":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.btnRemoveWorkEx.setVisibility(View.GONE);
                binding.addworklay.textFileOfStudy.setVisibility(View.VISIBLE);
                binding.addworklay.etFieldofStudy.setVisibility(View.VISIBLE);
                binding.addworklay.txtaddworkexperience.setText("Add Education");
                binding.addworklay.textJobTitle.setText("Level of education");
                binding.addworklay.editTextJobTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.searchviewLayo.getRoot().setVisibility(View.VISIBLE);
                        binding.addworklay.getRoot().setVisibility(View.GONE);

                        binding.searchviewLayo.txtTitleSearchData.setText("Level of education");
                        ArrayList<String> list = new ArrayList<>();
                        list.add("Assistant");
                        list.add("Associate");
                        list.add("Administrative Assistant");
                        list.add("Account Manager");
                        list.add("Assistant Manager");
                        list.add("Commission Sales Associate");
                        list.add("Sales Attendant");
                        list.add("Accountant");
                        list.add("Sales Advocate");
                        list.add("Analyst");
                        addList(list);
                        searchView();
                        binding.searchviewLayo.listViewSearchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                levelOfEducation = listOptions.get(i).toString();
                                binding.addworklay.editTextJobTitle.setText(levelOfEducation);
                                binding.addworklay.edittextCompany.requestFocus();
                                Toast.makeText(DetailsActivity.this, "" + levelOfEducation, Toast.LENGTH_SHORT).show();
                                binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                            }
                        });
                        binding.searchviewLayo.btnBackSearvhData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                binding.addworklay.textCompanyAddWork.setText("Institute name");
                binding.addworklay.edittextCompany.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.searchviewLayo.getRoot().setVisibility(View.VISIBLE);
                        binding.addworklay.getRoot().setVisibility(View.GONE);
                        binding.searchviewLayo.txtTitleSearchData.setText("Institute name");
                        ArrayList<String> list = new ArrayList<>();
                        list.add("Karakuram Internation University");
                        list.add("Oxford University");
                        list.add("University of Punjab");
                        list.add("National University of Science and Technology");
                        list.add("University of Engineering and Technology ");
                        list.add("National University of Modern Languages");
                        list.add("Bahria University");
                        list.add("Islamic University");
                        list.add("University of Peshawar");
                        list.add("Institute of Business and Accounting");
                        addList(list);
                        searchView();
                        binding.searchviewLayo.listViewSearchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                instituteName = listOptions.get(i).toString();
                                binding.addworklay.edittextCompany.setText(instituteName);
                                binding.addworklay.etFieldofStudy.requestFocus();
                                Toast.makeText(DetailsActivity.this, "" + instituteName, Toast.LENGTH_SHORT).show();
                                binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                                binding.addworklay.getRoot().setVisibility(View.VISIBLE);

                            }
                        });
                        binding.searchviewLayo.btnBackSearvhData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                                binding.addworklay.getRoot().setVisibility(View.VISIBLE);

                            }
                        });
                    }
                });
                binding.addworklay.etFieldofStudy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.searchviewLayo.getRoot().setVisibility(View.VISIBLE);
                        binding.addworklay.getRoot().setVisibility(View.GONE);

                        binding.searchviewLayo.txtTitleSearchData.setText("Field of study");
                        ArrayList<String> list = new ArrayList<>();
                        list.add("Bs Computer science");
                        list.add("Bs Information Technology");
                        list.add("Bs Software Engineering");
                        list.add("Bs GIS");
                        list.add("Bs in Medical Science");
                        list.add("Bs in Social Sciences");
                        list.add("Bs Zoology");
                        list.add("BS Mathematics");
                        list.add("BS Sociology");
                        list.add("BS Honors");
                        addList(list);
                        searchView();
                        binding.searchviewLayo.listViewSearchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                filedOfStudy = listOptions.get(i).toString();
                                binding.addworklay.etFieldofStudy.setText(filedOfStudy);
                                binding.addworklay.etStartDateAddWork.requestFocus();
                                Toast.makeText(DetailsActivity.this, "" + filedOfStudy, Toast.LENGTH_SHORT).show();
                                binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                                binding.addworklay.getRoot().setVisibility(View.VISIBLE);

                            }
                        });

                    }

                });
                binding.searchviewLayo.btnBackSearvhData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                        binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                    }
                });
                break;

            case "Edit Education":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.txtaddworkexperience.setText("Change Education");
                binding.addworklay.textFileOfStudy.setVisibility(View.VISIBLE);
                binding.addworklay.etFieldofStudy.setVisibility(View.VISIBLE);
                binding.addworklay.textJobTitle.setText("Level of education");
                binding.addworklay.textCompanyAddWork.setText("Institute name");
                binding.addworklay.btnRemoveWorkEx.setOnClickListener(view -> {

                    BottomSheetDialog dialog = new BottomSheetDialog(DetailsActivity.this, R.style.AppBottomSheetDialogTheme);

                    View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                            inflate(R.layout.undo_changes_layout, (CardView) findViewById(R.id.UndoChanges));

                    dialog.setContentView(bottomsheetView);
                    dialog.show();
                    TextView title = bottomsheetView.findViewById(R.id.textTitle_dialog);
                    TextView subtitle = bottomsheetView.findViewById(R.id.textSubtitle_dialog);
                    Button confirm = bottomsheetView.findViewById(R.id.btnContinue_dialog);
                    Button cancel = bottomsheetView.findViewById(R.id.btnUndo_dialog);
                    confirm.setText("Yes");
                    cancel.setText("Cancel");
                    cancel.setOnClickListener(view1 -> dialog.dismiss());
                    title.setText("Remove Education?");
                    subtitle.setText("Are you sure you want to delete this education?");

                });
                break;

            case "Edit Skills":

                binding.searchSkillLayout.getRoot().setVisibility(View.VISIBLE);
                binding.searchSkillLayout.txtTitleSearchSkill.setText("Add Skill");
                binding.searchSkillLayout.searchViewSearchSkill.setQueryHint("Search Skills");
                ArrayList<String> list = new ArrayList<>();
                list.add("Graphic Designing");
                list.add("Android Development");
                list.add("Digital Marketing");
                list.add("Coding");
                list.add("Video Editing");
                list.add("Graphics");
                list.add("LeaderShip");
                list.add("English");
                list.add("Good Communication Skills");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
                binding.searchSkillLayout.listViewSearchData.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                binding.searchSkillLayout.listViewSearchData.setAdapter(adapter);
                binding.searchSkillLayout.searchViewSearchSkill.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

                binding.searchSkillLayout.listViewSearchData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        SparseBooleanArray clickedItemPositions = binding.searchSkillLayout.listViewSearchData.getCheckedItemPositions();
                        skills = new ArrayList<>();
                        String items = (String) adapterView.getItemAtPosition(i);
                        Toast.makeText(DetailsActivity.this, "" + items, Toast.LENGTH_SHORT).show();

                        binding.searchSkillLayout.textToSetSkills.setText("items");
                        for (int index = 0; index < clickedItemPositions.size(); index++) {
                            // Get the checked status of the current item
                            boolean checked = clickedItemPositions.valueAt(index);

                            if (checked) {
                                // If the current item is checked

                                int key = clickedItemPositions.keyAt(index);
                                selectedSkill = (String) binding.searchSkillLayout.listViewSearchData.getItemAtPosition(key);

                                skills.add(selectedSkill);

                                // Display the checked items on TextView
                                binding.searchSkillLayout.textToSetSkills.setText("item ");
                            }

                        }


                    }
                });

                binding.searchSkillLayout.btnShow.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (skills.isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Select sny skill", Toast.LENGTH_SHORT).show();
                        }
                        if (skills.size() != 8) {
                            Toast.makeText(DetailsActivity.this, "Select minimum eight skills", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.searchSkillLayout.getRoot().setVisibility(View.GONE);
                            binding.saveSkillsLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.saveSkillsLayout.txtTitleSearchSkillcomp.setText("Add skill");
                            binding.saveSkillsLayout.btnBackSkillcomp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    binding.searchSkillLayout.getRoot().setVisibility(View.VISIBLE);
                                    binding.saveSkillsLayout.getRoot().setVisibility(View.GONE);
                                }
                            });
                            if (!skills.get(0).isEmpty()) {
                                binding.saveSkillsLayout.skillOne.setText(skills.get(0));
                                binding.saveSkillsLayout.skillOne.setOnClickListener(view12 -> {
                                    binding.saveSkillsLayout.skillOne.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillOne.setVisibility(View.GONE);
                            }
                            if (!skills.get(1).isEmpty()) {
                                binding.saveSkillsLayout.skillTwo.setOnClickListener(view13 -> {
                                    binding.saveSkillsLayout.skillTwo.setOnClickListener(view14 -> {
                                        binding.saveSkillsLayout.skillTwo.setText("");

                                    });
                                });
                                binding.saveSkillsLayout.skillTwo.setText(skills.get(1));
                            } else {
                                binding.saveSkillsLayout.skillTwo.setVisibility(View.GONE);
                            }
                            if (!skills.get(2).isEmpty()) {
                                binding.saveSkillsLayout.skillThree.setText(skills.get(2));
                                binding.saveSkillsLayout.skillThree.setOnClickListener(view15 -> {
                                    binding.saveSkillsLayout.skillThree.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillThree.setVisibility(View.GONE);
                            }
                            if (!skills.get(3).isEmpty()) {
                                binding.saveSkillsLayout.skillFour.setText(skills.get(3));
                                binding.saveSkillsLayout.skillFour.setOnClickListener(view16 -> {
                                    binding.saveSkillsLayout.skillFour.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillFour.setVisibility(View.GONE);
                            }
                            if (!skills.get(4).isEmpty()) {
                                binding.saveSkillsLayout.skillFive.setText(skills.get(4));
                                binding.saveSkillsLayout.skillFive.setOnClickListener(view17 -> {
                                    binding.saveSkillsLayout.skillFive.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillFive.setVisibility(View.GONE);
                            }
                            if (!skills.get(5).isEmpty()) {
                                binding.saveSkillsLayout.skillSix.setText(skills.get(5));
                                binding.saveSkillsLayout.skillSix.setOnClickListener(view18 -> {
                                    binding.saveSkillsLayout.skillSix.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillSix.setVisibility(View.GONE);
                            }
                            if (!skills.get(6).isEmpty()) {
                                binding.saveSkillsLayout.skillSeven.setText(skills.get(6));
                                binding.saveSkillsLayout.skillSeven.setOnClickListener(view19 -> {
                                    binding.saveSkillsLayout.skillSeven.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillSeven.setVisibility(View.GONE);
                            }
                            if (!skills.get(7).isEmpty()) {
                                binding.saveSkillsLayout.skillEight.setText(skills.get(7));
                                binding.saveSkillsLayout.skillEight.setOnClickListener(view110 -> {
                                    binding.saveSkillsLayout.skillEight.setText("");
                                });
                            } else {
                                binding.saveSkillsLayout.skillEight.setVisibility(View.GONE);
                            }
                        }
                        binding.saveSkillsLayout.btnShow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        if (binding.saveSkillsLayout.btnShow.getText().equals("Save")) {

                            binding.saveSkillsLayout.btnShow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    binding.saveSkillsLayout.btnShow.setText("Change");
                                    binding.saveSkillsLayout.searchViewSearchSkillcomp.setVisibility(View.GONE);
                                }
                            });
                        }
                        if (binding.saveSkillsLayout.btnShow.getText().equals("Change")){
                            Toast.makeText(DetailsActivity.this, "Change Button clicked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }

    }

    private void bottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(DetailsActivity.this, R.style.AppBottomSheetDialogTheme);

        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.undo_changes_layout, (CardView) findViewById(R.id.UndoChanges));
        dialog.setContentView(bottomsheetView);
        dialog.show();
        Button btnContinue = bottomsheetView.findViewById(R.id.btnContinue_dialog);
        Button btnUndo = bottomsheetView.findViewById(R.id.btnUndo_dialog);
        btnUndo.setOnClickListener(view1 -> {
            finish();

        });
        btnContinue.setOnClickListener(view -> dialog.dismiss());
    }


    private static void addList(ArrayList<String> options) {
        listOptions = new ArrayList<>(options);
    }

    private void searchView() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOptions);
        binding.searchviewLayo.listViewSearchData.setAdapter(adapter);
        binding.searchviewLayo.searchViewSearchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public void onBackPressed() {
        bottomDialog();
    }
}