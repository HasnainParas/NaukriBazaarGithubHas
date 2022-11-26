package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseAdapters.LanguageAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.LanguagesAdapter;
import com.appstacks.indiannaukribazaar.ProfileModels.Appreciation;
import com.appstacks.indiannaukribazaar.ProfileModels.SelectedLanguages;
import com.appstacks.indiannaukribazaar.ProfileModels.AboutMeDescription;
import com.appstacks.indiannaukribazaar.ProfileModels.AddWorkExperience;
import com.appstacks.indiannaukribazaar.ProfileModels.Education;
import com.appstacks.indiannaukribazaar.ProfileModels.Resume;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    View aboutlayout;
    String profileIntent;
    ArrayList<SelectedLanguages> selectedLanguagesArrayList;
    ArrayAdapter<String> adapter;
    String levelOfEducation, instituteName, filedOfStudy;
    public static ArrayList<String> listOptions;
    ArrayList<String> skills;
    String levelOfWritten, levelOfOral;
    String selectedSkill;
    RadioGroup radioGroup;
    Uri uri;
    ProgressDialog dialog;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String userId, pdfName = "";
    String urlOfPdf = "";
    String PdfsizeInString,pdfDate;
    boolean workAdded;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        aboutlayout = findViewById(R.id.aboutidlay);
        dialog = new ProgressDialog(DetailsActivity.this);
        profileIntent = getIntent().getStringExtra("profile");
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        switch (profileIntent) {
            case "About Me":
                binding.aboutidlay.getRoot().setVisibility(View.VISIBLE);
                String aboutMeFromDb = getIntent().getStringExtra("aboutMeDesc");
                if (aboutMeFromDb != null)
                    binding.aboutidlay.etTellmeAbout.setText(aboutMeFromDb);
                binding.aboutidlay.btnBackAboutMe.setOnClickListener(view -> onBackPressed());
                binding.aboutidlay.etTellmeAbout.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_arrow_back_black_24dp);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_cancel);
                        binding.aboutidlay.btnBackAboutMe.setOnClickListener(view -> Toast.makeText(DetailsActivity.this, "On Text Chaged Cacel", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        binding.aboutidlay.btnBackAboutMe.setImageResource(R.drawable.ic_cancel);
                        binding.aboutidlay.btnBackAboutMe.setOnClickListener(view -> bottomDialog());
                    }
                });
                binding.aboutidlay.btnSaveAboutMe.setOnClickListener(view -> {
                    if (binding.aboutidlay.etTellmeAbout.getText().toString().isEmpty()) {
                        binding.aboutidlay.etTellmeAbout.setError("Kindly describe yourself");
                    } else {
                        String aboutMeDescription = binding.aboutidlay.etTellmeAbout.getText().toString();
                        dialog.setTitle("Uploading ");
                        dialog.setMessage("Please wait while uploading");
                        dialog.setCancelable(false);
                        dialog.show();
                        UploadAboutMe(aboutMeDescription);
                    }
                });
                break;
            case "Add Work":

                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.btnRemoveWorkEx.setVisibility(View.GONE);

                binding.addworklay.btnSaveAddWorkEx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.addworklay.editTextJobTitle.getText().toString().isEmpty()) {
                            binding.addworklay.editTextJobTitle.setError("Enter job title");
                        } else if (binding.addworklay.edittextCompany.getText().toString().isEmpty()) {
                            binding.addworklay.edittextCompany.setError("Enter company title");
                        } else if (binding.addworklay.etStartDateAddWork.getText().toString().isEmpty()) {
                            binding.addworklay.etStartDateAddWork.setError("Start date required");
                        } else if (binding.addworklay.etTellmeAbout.getText().toString().isEmpty()) {
                            binding.addworklay.etTellmeAbout.setError("Kindly provide a description");
                        } else if (binding.addworklay.checkboxPosition.isChecked() && !binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
                        } else if (!binding.addworklay.checkboxPosition.isChecked() && binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
                        } else {


                            String jobTitle = binding.addworklay.editTextJobTitle.getText().toString();
                            String company = binding.addworklay.edittextCompany.getText().toString();
                            String startDate = binding.addworklay.etStartDateAddWork.getText().toString();
                            String endDate = binding.addworklay.etEndDateAddWork.getText().toString();
                            String description = binding.addworklay.etTellmeAbout.getText().toString();

                            boolean isPosition = binding.addworklay.checkboxPosition.isChecked();
                            dialog.setTitle("Uploading");
                            dialog.setMessage("Please wait while uploading");
                            dialog.setCancelable(false);
                            dialog.show();
                            uploadWorkExperience(jobTitle, company, startDate, endDate, description, userId, isPosition);
                        }
                    }
                });
                break;

            case "Edit Work":


                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.editTextJobTitle.setText(getIntent().getStringExtra("jobTitle"));
                binding.addworklay.edittextCompany.setText(getIntent().getStringExtra("company"));
                binding.addworklay.etStartDateAddWork.setText(getIntent().getStringExtra("startDate"));
                binding.addworklay.etEndDateAddWork.setText(getIntent().getStringExtra("endDate"));
                binding.addworklay.checkboxPosition.setChecked(getIntent().getBooleanExtra("positionNow", false));
                binding.addworklay.etTellmeAbout.setText(getIntent().getStringExtra("jobDesc"));
                binding.addworklay.txtaddworkexperience.setText("Change Work Experience");
                binding.addworklay.btnSaveAddWorkEx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.addworklay.editTextJobTitle.getText().toString().isEmpty()) {
                            binding.addworklay.editTextJobTitle.setError("Enter job title");
                        } else if (binding.addworklay.edittextCompany.getText().toString().isEmpty()) {
                            binding.addworklay.edittextCompany.setError("Enter company title");
                        } else if (binding.addworklay.etStartDateAddWork.getText().toString().isEmpty()) {
                            binding.addworklay.etStartDateAddWork.setError("Start date required");
                        } else if (binding.addworklay.etTellmeAbout.getText().toString().isEmpty()) {
                            binding.addworklay.etTellmeAbout.setError("Kindly provide a description");
                        } else if (binding.addworklay.checkboxPosition.isChecked() && !binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
                        } else if (!binding.addworklay.checkboxPosition.isChecked() && binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
                        } else {


                            String jobTitle = binding.addworklay.editTextJobTitle.getText().toString();
                            String company = binding.addworklay.edittextCompany.getText().toString();
                            String startDate = binding.addworklay.etStartDateAddWork.getText().toString();
                            String endDate = binding.addworklay.etEndDateAddWork.getText().toString();
                            String description = binding.addworklay.etTellmeAbout.getText().toString();

                            boolean isPosition = binding.addworklay.checkboxPosition.isChecked();
                            dialog.setTitle("Updating");
                            dialog.setMessage("Please wait..");
                            dialog.setCancelable(false);
                            dialog.show();
                            uploadWorkExperience(jobTitle, company, startDate, endDate, description, userId, isPosition);
                        }
                    }
                });
                binding.addworklay.btnRemoveWorkEx.setVisibility(View.VISIBLE);
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
                    cancel.setOnClickListener(view16 -> dialog.dismiss());
                    title.setText("Remove Work Experience?");
                    subtitle.setText("Are you sure you want to delete this work experience?");

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseReference.child(getString(R.string.user_profile)).child(userId).child("WorkExperience").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete() && task.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(DetailsActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                });
                break;

            case "Add Education":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                binding.addworklay.btnRemoveWorkEx.setVisibility(View.GONE);
                binding.addworklay.textFileOfStudy.setVisibility(View.VISIBLE);
                binding.addworklay.etFieldofStudy.setVisibility(View.VISIBLE);

                binding.addworklay.checkboxPosition.setVisibility(View.VISIBLE);
                binding.addworklay.txtaddworkexperience.setText("Add Education");
                binding.addworklay.textJobTitle.setText("Level of education");
                binding.addworklay.editTextJobTitle.setOnClickListener(view -> {
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
                    binding.searchviewLayo.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
                        levelOfEducation = listOptions.get(i).toString();
                        binding.addworklay.editTextJobTitle.setText(levelOfEducation);
                        binding.addworklay.edittextCompany.requestFocus();
                        Toast.makeText(DetailsActivity.this, "" + levelOfEducation, Toast.LENGTH_SHORT).show();
                        binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                        binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                    });
                    binding.searchviewLayo.btnBackSearvhData.setOnClickListener(view17 -> {
                        binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                        binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                    });
                });
                binding.addworklay.textCompanyAddWork.setText("Institute name");
                binding.addworklay.edittextCompany.setOnClickListener(view -> {
                    binding.searchviewLayo.getRoot().setVisibility(View.VISIBLE);
                    binding.addworklay.getRoot().setVisibility(View.GONE);
                    binding.searchviewLayo.txtTitleSearchData.setText("Institute name");
                    ArrayList<String> list = new ArrayList<>();
                    list.add("Karakoram Internation University");
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
                    binding.searchviewLayo.listViewSearchData.setOnItemClickListener((adapterView, view18, i, l) -> {
                        instituteName = listOptions.get(i).toString();
                        binding.addworklay.edittextCompany.setText(instituteName);
                        binding.addworklay.etFieldofStudy.requestFocus();
                        Toast.makeText(DetailsActivity.this, "" + instituteName, Toast.LENGTH_SHORT).show();
                        binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                        binding.addworklay.getRoot().setVisibility(View.VISIBLE);

                    });
                    binding.searchviewLayo.btnBackSearvhData.setOnClickListener(view19 -> {
                        binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                        binding.addworklay.getRoot().setVisibility(View.VISIBLE);


                    });
                });
                binding.addworklay.etFieldofStudy.setOnClickListener(view -> {
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

                });
                binding.searchviewLayo.btnBackSearvhData.setOnClickListener(view -> {
                    binding.searchviewLayo.getRoot().setVisibility(View.GONE);
                    binding.addworklay.getRoot().setVisibility(View.VISIBLE);
                });
                if (binding.addworklay.txtaddworkexperience.getText().toString().equals("Add Education")) {
                    binding.addworklay.btnSaveAddWorkEx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (binding.addworklay.edittextCompany.getText().toString().isEmpty()) {
                                binding.addworklay.edittextCompany.setError("Add your institute by double tapping");
                            } else if (binding.addworklay.editTextJobTitle.getText().toString().isEmpty()) {
                                binding.addworklay.editTextJobTitle.setError("Add your education by double tapping");
                            } else if (binding.addworklay.etFieldofStudy.getText().toString().isEmpty()) {
                                binding.addworklay.etFieldofStudy.setError("Add your field by double tapping");
                            } else if (binding.addworklay.etStartDateAddWork.getText().toString().isEmpty()) {
                                binding.addworklay.etStartDateAddWork.setError("Enter your starting date");
                            } else if (binding.addworklay.etTellmeAbout.getText().toString().isEmpty()) {
                                binding.addworklay.etTellmeAbout.setError("Kindly describe about your education");
                            } else if (binding.addworklay.checkboxPosition.isChecked() && !binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                                Toast.makeText(DetailsActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
                            } else if (!binding.addworklay.checkboxPosition.isChecked() && binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                                Toast.makeText(DetailsActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
                            } else {

                                dialog.setTitle("Uploading");
                                dialog.setMessage("Please wait while uploading");
                                dialog.setCancelable(false);
                                dialog.show();
                                String levelOfEducation = binding.addworklay.editTextJobTitle.getText().toString();
                                String instituteName = binding.addworklay.edittextCompany.getText().toString();
                                String educationFieldOfStudy = binding.addworklay.etFieldofStudy.getText().toString();
                                String educationStartDate = binding.addworklay.etStartDateAddWork.getText().toString();
                                String educationEndDate = binding.addworklay.etEndDateAddWork.getText().toString();
                                String educationDescription = binding.addworklay.etTellmeAbout.getText().toString();
                                boolean isPosition = binding.addworklay.checkboxPosition.isChecked();

                                uploadEducation(levelOfEducation, instituteName, educationFieldOfStudy, educationStartDate, educationEndDate, educationDescription, isPosition);

                            }
                        }
                    });
                }
                break;

            case "Edit Education":
                binding.addworklay.getRoot().setVisibility(View.VISIBLE);

                binding.addworklay.btnRemoveWorkEx.setVisibility(View.VISIBLE);
                binding.addworklay.txtaddworkexperience.setText("Change Education");
                binding.addworklay.textFileOfStudy.setVisibility(View.VISIBLE);
                binding.addworklay.etFieldofStudy.setVisibility(View.VISIBLE);
                binding.addworklay.textJobTitle.setText("Level of education");
                binding.addworklay.textCompanyAddWork.setText("Institute name");


                binding.addworklay.editTextJobTitle.setText(getIntent().getStringExtra("levelEducation"));
                binding.addworklay.edittextCompany.setText(getIntent().getStringExtra("institute"));
                binding.addworklay.etFieldofStudy.setText(getIntent().getStringExtra("fieldOfStudy"));
                binding.addworklay.etStartDateAddWork.setText(getIntent().getStringExtra("educationStartDate"));
                binding.addworklay.etEndDateAddWork.setText(getIntent().getStringExtra("educationEndDate"));
                binding.addworklay.checkboxPosition.setChecked(getIntent().getBooleanExtra("isPositionEducation", false));
                binding.addworklay.etTellmeAbout.setText(getIntent().getStringExtra("educationDesc"));
                binding.addworklay.btnSaveAddWorkEx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (binding.addworklay.edittextCompany.getText().toString().isEmpty()) {
                            binding.addworklay.edittextCompany.setError("Add your institute ");
                        } else if (binding.addworklay.editTextJobTitle.getText().toString().isEmpty()) {
                            binding.addworklay.editTextJobTitle.setError("Add your education ");
                        } else if (binding.addworklay.etFieldofStudy.getText().toString().isEmpty()) {
                            binding.addworklay.etFieldofStudy.setError("Add your field by double");
                        } else if (binding.addworklay.etStartDateAddWork.getText().toString().isEmpty()) {
                            binding.addworklay.etStartDateAddWork.setError("Enter your starting date");
                        } else if (binding.addworklay.etTellmeAbout.getText().toString().isEmpty()) {
                            binding.addworklay.etTellmeAbout.setError("Kindly describe about your education");
                        } else if (binding.addworklay.checkboxPosition.isChecked() && !binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Your current position cannot have an end date", Toast.LENGTH_SHORT).show();
                        } else if (!binding.addworklay.checkboxPosition.isChecked() && binding.addworklay.etEndDateAddWork.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Provide your end date or this is your current position", Toast.LENGTH_SHORT).show();
                        } else {

                            dialog.setTitle("Uploading");
                            dialog.setMessage("Please wait while uploading");
                            dialog.setCancelable(false);
                            dialog.show();
                            String levelOfEducation = binding.addworklay.editTextJobTitle.getText().toString();
                            String instituteName = binding.addworklay.edittextCompany.getText().toString();
                            String educationFieldOfStudy = binding.addworklay.etFieldofStudy.getText().toString();
                            String educationStartDate = binding.addworklay.etStartDateAddWork.getText().toString();
                            String educationEndDate = binding.addworklay.etEndDateAddWork.getText().toString();
                            String educationDescription = binding.addworklay.etTellmeAbout.getText().toString();
                            boolean isPosition = binding.addworklay.checkboxPosition.isChecked();

                            uploadEducation(levelOfEducation, instituteName, educationFieldOfStudy, educationStartDate, educationEndDate, educationDescription, isPosition);

                        }


                    }
                });
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
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            databaseReference.child(getString(R.string.user_profile)).child(userId).child("Education").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete() && task.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(DetailsActivity.this, "Successfully removed", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                });
                break;

            case "Edit Skills":
                binding.addworklay.getRoot().setVisibility(View.GONE);
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

                binding.searchSkillLayout.listViewSearchData.setOnItemClickListener((adapterView, view, i, l) -> {

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

                        }

                    }


                });
                binding.searchSkillLayout.btnShow.setOnClickListener(view -> {

                    if (skills == null) {
                        Toast.makeText(DetailsActivity.this, "Select any skill", Toast.LENGTH_SHORT).show();
                    } else if (skills.size() != 8) {
                        Toast.makeText(DetailsActivity.this, "Select minimum eight skills", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.searchSkillLayout.getRoot().setVisibility(View.GONE);
                        binding.saveSkillsLayout.getRoot().setVisibility(View.VISIBLE);
                        binding.saveSkillsLayout.txtTitleSearchSkillcomp.setText("Add skill");
                        binding.saveSkillsLayout.btnShow.setText("Show");
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailsActivity.this, R.layout.sample_skills_layout, R.id.textView, skills);
                        binding.saveSkillsLayout.listOfSkills.setAdapter(arrayAdapter);

                        if (binding.saveSkillsLayout.btnShow.getText().equals("Show")) {
                            binding.saveSkillsLayout.listOfSkills.setOnItemClickListener((adapterView, view112, i, l) -> {

                                skills.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                            });
                        }
                        binding.saveSkillsLayout.btnBackSkillcomp.setOnClickListener(view12 -> {
                            binding.searchSkillLayout.getRoot().setVisibility(View.VISIBLE);
                            binding.saveSkillsLayout.getRoot().setVisibility(View.GONE);
                        });


                    }

                    if (binding.saveSkillsLayout.btnShow.getText().equals("Show")) {

                        binding.saveSkillsLayout.btnShow.setOnClickListener(view13 -> {

                            binding.saveSkillsLayout.txtNoOfSkills.setText("(" + skills.size() + ")");
                            adapter.notifyDataSetInvalidated();
                            binding.saveSkillsLayout.searchViewSearchSkillcomp.setVisibility(View.GONE);

                            binding.saveSkillsLayout.btnShow.setText("Confirm");

                            binding.saveSkillsLayout.btnShow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.setTitle("Uploading");
                                    dialog.setMessage("Please wait while uploading");
                                    dialog.setCancelable(false);
                                    dialog.show();
//                                    for (int i = 0; i < skills.size(); i++) {
//                                        skillsToUpload += skills.get(i);
//                                        skillsToUpload += ",";
//
//
//                                    }
                                    HashMap<String, String> skillsMap = convertArraylistToHashmap(skills);
                                    uploadSkills(skillsMap);

                                }
                            });


                        });
                    }

                });
                break;
            case "Edit language":
                binding.addlanguageEdit.getRoot().setVisibility(View.VISIBLE);
                binding.searchSkillLayout.getRoot().setVisibility(View.GONE);
                binding.addlanguageEdit.btnsaveAddlanguage1.setVisibility(View.GONE);
                selectedLanguagesArrayList = new ArrayList<>();
                databaseReference.child(getString(R.string.user_profile)).child(userId).child("Languages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {


                            for (DataSnapshot snap : snapshot.getChildren()) {
                                SelectedLanguages languages = snap.getValue(SelectedLanguages.class);

                                selectedLanguagesArrayList.add(languages);


                            }

                        } else {
                            Toast.makeText(DetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailsActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                if (selectedLanguagesArrayList != null) {
                    LanguagesAdapter languagesAdapter = new LanguagesAdapter(selectedLanguagesArrayList, this);
                    binding.addlanguageEdit.addedLanguageRecyclerview.setVisibility(View.VISIBLE);
                    binding.addlanguageEdit.addedLanguageRecyclerview.setAdapter(languagesAdapter);
                    binding.addlanguageEdit.addedLanguageRecyclerview.setLayoutManager(new LinearLayoutManager(this));

                }


                //TODO save the below languages and show
                binding.addlanguageEdit.btnBackAddLanguage1.setOnClickListener(view -> onBackPressed());
                binding.addlanguageEdit.btnsaveAddlanguage1.setVisibility(View.VISIBLE);
                binding.addlanguageEdit.btnAddLanguage1.setOnClickListener(view -> {
                    binding.addlanguageEdit.btnsaveAddlanguage1.setVisibility(View.GONE);
                    binding.addlanguageEdit.btnAddLanguage1.setVisibility(View.GONE);
                    binding.addlanguageEdit.btnsaveAddlanguage1.setVisibility(View.GONE);
                    binding.addlanguageEdit.addedLanguageRecyclerview.setVisibility(View.GONE);


                    String[] languages = {"Hindi", "Urdu", "Punjabi", "English", "Marathi", "Pashto", "Shina", "Spanish", "Chinese"};
                    int[] flags = {R.drawable.india_flag, R.drawable.pak_flag, R.drawable.pak_flag, R.drawable.us_flag, R.drawable.india_flag, R.drawable.pak_flag, R.drawable.pak_flag, R.drawable.spanish_flag, R.drawable.chinese_flag};

                    LanguageAdapter languageAdapter = new LanguageAdapter(this, languages, flags);
                    binding.addlanguageEdit.listViewAddlanguage1.setAdapter(languageAdapter);
                    binding.addlanguageEdit.listViewAddlanguage1.setOnItemClickListener((adapterView, view111, i, l) -> {

                        binding.addlanguageEdit.getRoot().setVisibility(View.GONE);
                        binding.languageDetailsLayout.getRoot().setVisibility(View.VISIBLE);
                        Dialog dialog = new Dialog(DetailsActivity.this);
                        dialog.setContentView(R.layout.level_layout);
                        radioGroup = dialog.findViewById(R.id.radioGroup_level);


                        binding.languageDetailsLayout.txtOral.setOnClickListener(view113 -> {

                            dialog.show();


                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton radioButton = radioGroup.findViewById(i);
                                    Toast.makeText(DetailsActivity.this, "" + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                                    //TODO    level of oral
                                    levelOfOral = radioButton.getText().toString();

                                    binding.languageDetailsLayout.levelToSet.setText(levelOfOral);
                                    dialog.dismiss();
                                }
                            });


                        });

                        binding.languageDetailsLayout.txtWritten.setOnClickListener(view114 -> {
                            dialog.show();


                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                    RadioButton radioButton = radioGroup.findViewById(i);
                                    Toast.makeText(DetailsActivity.this, "" + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                                    //TODO    level of written
                                    levelOfWritten = radioButton.getText().toString();

                                    dialog.dismiss();
                                }
                            });

                        });

                        binding.languageDetailsLayout.btnSaveLanguageDetails.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("SuspiciousIndentation")
                            @Override
                            public void onClick(View view) {
                                Drawable drawable = binding.languageDetailsLayout.flagToSet.getDrawable();
                                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

                                String language = binding.languageDetailsLayout.txtLanguageToSet.getText().toString();
                                if (Objects.equals(levelOfOral, null)) {
                                    Toast.makeText(DetailsActivity.this, "Select language's oral level", Toast.LENGTH_SHORT).show();
                                } else if (Objects.equals(levelOfWritten, null)) {
                                    Toast.makeText(DetailsActivity.this, "Select language's written level", Toast.LENGTH_SHORT).show();
                                } else {

                                    uploadFlagOfLanguage(bitmap, language);


                                }
                            }
                        });


                        switch (i) {

                            case 0:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Hindi");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.india_flag);
                                break;
                            case 1:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Urdu");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.pak_flag);
                                break;
                            case 2:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Punjabi");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.pak_flag);
                                break;
                            case 3:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("English");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.us_flag);
                                break;
                            case 4:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Marathi");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.india_flag);
                                break;
                            case 5:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Pashto");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.pak_flag);
                                break;
                            case 6:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Shina");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.pak_flag);
                                break;
                            case 7:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Spanish");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.spanish_flag);
                                break;
                            case 8:
                                binding.languageDetailsLayout.txtLanguageToSet.setText("Chinese");
                                binding.languageDetailsLayout.flagToSet.setImageResource(R.drawable.chinese_flag);
                                break;


                        }
                    });
                    binding.addlanguageEdit.btnBackAddLanguage1.setOnClickListener(view14 -> {
                        onBackPressed();

                        //TODO listview with languages


                    });


                });

                break;
            case "Add Appre":
                binding.addAppreciation.getRoot().setVisibility(View.VISIBLE);
                binding.addAppreciation.btnBackAddappreciation.setOnClickListener(view -> onBackPressed());
                binding.addAppreciation.btnRemoveAppreciation.setVisibility(View.GONE);
                binding.addAppreciation.btnSaveAddAppreciation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (binding.addAppreciation.editTextAwardName.getText().toString().isEmpty()) {
                            binding.addAppreciation.editTextAwardName.setError("Enter award name");
                        } else if (binding.addAppreciation.edittextCategory.getText().toString().isEmpty()) {
                            binding.addAppreciation.edittextCategory.setError("Enter your category");
                        } else if (binding.addAppreciation.etEndDateappreciation.getText().toString().isEmpty()) {
                            binding.addAppreciation.etEndDateappreciation.setError("Enter date");
                        } else if (binding.addAppreciation.etTellmeAppreciation.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Kindly provide a description", Toast.LENGTH_SHORT).show();
                        } else {

                            String awardName = binding.addAppreciation.editTextAwardName.getText().toString();
                            String awardCategory = binding.addAppreciation.edittextCategory.getText().toString();
                            String awardEndDate = binding.addAppreciation.etEndDateappreciation.getText().toString();
                            String awardDescription = binding.addAppreciation.etTellmeAppreciation.getText().toString();


                            uploadAppreciation(awardName, awardCategory, awardEndDate, awardDescription);
                        }
                    }
                });


                break;
            case "Edit Appre":
                binding.addAppreciation.getRoot().setVisibility(View.VISIBLE);
                binding.addAppreciation.txtaddappreciation.setText("Edit Appreciation");
                binding.addAppreciation.btnBackAddappreciation.setImageResource(R.drawable.ic_cancel);
                binding.addAppreciation.btnRemoveAppreciation.setVisibility(View.VISIBLE);
                binding.addAppreciation.editTextAwardName.setText(getIntent().getStringExtra("awardName"));
                binding.addAppreciation.edittextCategory.setText(getIntent().getStringExtra("awardCategory"));
                binding.addAppreciation.etEndDateappreciation.setText(getIntent().getStringExtra("awardDate"));
                binding.addAppreciation.etTellmeAppreciation.setText(getIntent().getStringExtra("awardDesc"));
                binding.addAppreciation.btnSaveAddAppreciation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (binding.addAppreciation.editTextAwardName.getText().toString().isEmpty()) {
                            binding.addAppreciation.editTextAwardName.setError("Enter award name");
                        } else if (binding.addAppreciation.edittextCategory.getText().toString().isEmpty()) {
                            binding.addAppreciation.edittextCategory.setError("Enter your category");
                        } else if (binding.addAppreciation.etEndDateappreciation.getText().toString().isEmpty()) {
                            binding.addAppreciation.etEndDateappreciation.setError("Enter date");
                        } else if (binding.addAppreciation.etTellmeAppreciation.getText().toString().isEmpty()) {
                            Toast.makeText(DetailsActivity.this, "Kindly provide a description", Toast.LENGTH_SHORT).show();
                        } else {

                            String awardName = binding.addAppreciation.editTextAwardName.getText().toString();
                            String awardCategory = binding.addAppreciation.edittextCategory.getText().toString();
                            String awardEndDate = binding.addAppreciation.etEndDateappreciation.getText().toString();
                            String awardDescription = binding.addAppreciation.etTellmeAppreciation.getText().toString();


                            uploadAppreciation(awardName, awardCategory, awardEndDate, awardDescription);
                        }

                    }
                });
                binding.addAppreciation.btnRemoveAppreciation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Appreciation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete() && task.isSuccessful()) {

                                    Toast.makeText(DetailsActivity.this, "Appreciation removed successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                });


                break;

            case "Add cv":
                binding.addResumelayout.getRoot().setVisibility(View.VISIBLE);
                binding.addResumelayout.btnBackAddResume.setOnClickListener(view -> onBackPressed());
                binding.addResumelayout.cardResumeAdded.setVisibility(View.GONE);
                binding.addResumelayout.cardUploadResume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                        // We will be redirected to choose pdf
                        galleryIntent.setType("application/pdf");
                        startActivityForResult(galleryIntent, 1);

                    }


                });
                binding.addResumelayout.btnSaveAddResume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (uri != null) {
                            uploadPdf();
                        } else {
                            Toast.makeText(DetailsActivity.this, "Select your resume in PDF format", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
        }

    }

    private ArrayList<SelectedLanguages> fetchLanguage() {


        return selectedLanguagesArrayList;
    }

    private void uploadAppreciation(String awardName, String awardCategory, String awardEndDate, String awardDescription) {
        dialog.setTitle("Adding appreciation");
        dialog.setMessage("Please wait while uploading...");
        dialog.setCancelable(false);
        dialog.show();

        Appreciation appreciation = new Appreciation(awardName, awardCategory, awardEndDate, awardDescription);

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Appreciation")
                .setValue(appreciation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Appreciation Added", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadFlagOfLanguage(Bitmap bitmap, String language) {
        dialog.setTitle("Adding Language");
        dialog.setMessage("Please wait uploading...");
        dialog.setCancelable(false);
        dialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child(getString(R.string.user_profile)).child("Languages").child(userId).child("LanguageFlag" + UUID.randomUUID().toString());
        UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String flagDownloadUrl = String.valueOf(uri);
                                    uploadLangauge(language, flagDownloadUrl, levelOfOral, levelOfWritten);
                                }
                            });
                        }
                    });
                } else {
                    dialog.dismiss();
                    Toast.makeText(DetailsActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void uploadLangauge(String language, String flagDownloadUrl, String levelOfOral, String levelOfWritten) {
        String uID = UUID.randomUUID().toString();

        SelectedLanguages selectedLanguages = new SelectedLanguages(language, flagDownloadUrl, levelOfOral, levelOfWritten, uID, userId);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Languages").child(uID)
                .setValue(selectedLanguages)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "Langauge Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void uploadSkills(HashMap<String, String> skillsMap) {

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Skills").setValue(skillsMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Skills added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void uploadEducation(String levelOfEducation, String instituteName, String filedOfStudy, String educationStartDate, String educationEndDate, String educationDescription, boolean isPosition) {
        Education education = new Education(levelOfEducation, instituteName, filedOfStudy, educationStartDate, educationEndDate, educationDescription, isPosition);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("Education").setValue(education)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Education Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadWorkExperience(String jobTitle, String company, String startDate, String endDate, String description, String userId, boolean isPosition) {

        AddWorkExperience data = new AddWorkExperience(jobTitle, company, startDate, endDate, description, userId, isPosition);

        databaseReference.child(getString(R.string.user_profile)).child(userId).child("WorkExperience").setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {

                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Work experience added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private static HashMap<String, String> convertArraylistToHashmap(ArrayList<String> arrayList) {

        HashMap<String, String> hashMap = new HashMap<>();


        for (int i = 0; i < arrayList.size(); i++) {

            hashMap.put(String.valueOf(i), arrayList.get(i));
        }





        return hashMap;

    }

    private void UploadAboutMe(String aboutMeDescription) {

        AboutMeDescription data = new AboutMeDescription(aboutMeDescription);
        databaseReference.child(getString(R.string.user_profile)).child(userId).child("AboutMe").setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Description Added", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPdf() {
        dialog.setTitle("Uploading");
        dialog.setMessage("Please wait while uploading");
        dialog.setCancelable(false);
        dialog.show();

        StorageReference reference = storageReference.child("Resumes").child(userId).child("pdf/" + pdfName);
        reference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri uri = uriTask.getResult();
                        uploadPdfData(String.valueOf(uri), pdfName);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();

                    }
                });

    }

    private void uploadPdfData(String downloadUrl, String pdfName) {
        Resume resume = new Resume(downloadUrl, pdfName,PdfsizeInString,pdfDate);
        databaseReference.child("UsersProfile").child(userId).child("Resume").setValue(resume)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete() && task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, "Resume Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(DetailsActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            binding.addResumelayout.cardUploadResume.setVisibility(View.GONE);
            binding.addResumelayout.cardResumeAdded.setVisibility(View.VISIBLE);
            uri = data.getData();
            String stringUri = uri.toString();

            if (stringUri.startsWith("content://")) {
                Cursor cursor = null;
                try {


                    cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                        long size = cursor.getLong(sizeIndex);
                        PdfsizeInString= getReadableFileSize(size);
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                     pdfDate= df.format(Calendar.getInstance().getTime());
                        Toast.makeText(this, "selected " + pdfName + " adn size is " + PdfsizeInString, Toast.LENGTH_SHORT).show();
                        binding.addResumelayout.resumeInfo.setText(pdfDate);
                        binding.addResumelayout.resumeName.setText(pdfName);


                    }
                } finally {
                    cursor.close();
                }

            }
        }
    }

    public static String getReadableFileSize(long size) {
        final int BYTES_IN_KILOBYTES = 1024;
        final java.text.DecimalFormat dec = new java.text.DecimalFormat("###.#");
        final String KILOBYTES = " KB";
        final String MEGABYTES = " MB";
        final String GIGABYTES = " GB";
        float fileSize = 0;
        String suffix = KILOBYTES;

        if (size > BYTES_IN_KILOBYTES) {
            fileSize = size / BYTES_IN_KILOBYTES;
            if (fileSize > BYTES_IN_KILOBYTES) {
                fileSize = fileSize / BYTES_IN_KILOBYTES;
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES;
                    suffix = GIGABYTES;
                } else {
                    suffix = MEGABYTES;
                }
            }
        }
        return String.valueOf(dec.format(fileSize) + suffix);
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



    private static void addList(ArrayList<String> options){
        listOptions= new ArrayList<>(options);
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