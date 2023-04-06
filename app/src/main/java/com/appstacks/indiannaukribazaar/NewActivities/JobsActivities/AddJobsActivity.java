package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.CompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.InstantCompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.JobLocationActivity;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.JobPosition;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.NewActivities.UserJobDetailsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddJobsBinding;

import com.appstacks.indiannaukribazaar.databinding.HandloadingDialogLayoutBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class AddJobsActivity extends AppCompatActivity {
    private SharedPrefe sharedPrefe;
    private ProfileUtils profileUtils;
    private ActivityAddJobsBinding binding;
    private BottomSheetDialog bottomSheetDialog;
    //    int checkRadio;
    private DatabaseReference allUserNormalJobs;
    private UserJobModel userJobModel;
    private String userUid;
    private AlertDialog loadingDialog;
    private ArrayList<CompanyModel> companyList;
    private CompanyAdapter companyJobAdapter;
    private String uniqueKey;
    private String jobSalary, jobQualification, jobExperience, jobSpecialization;
    private String[] jobSpecializations = {"Select Specialization", "Software Engineer", "Data Scientist", "UX Designer", "Product Manager", "Marketing Analyst", "Finance Manager", "Human Resources Specialist", "Sales Representative", "Customer Success Manager", "Business Development Manager", "IT Manager", "Operations Manager", "Supply Chain Analyst", "Graphic Designer", "Web Developer", "Mobile Application Developer", "Database Administrator", "Network Administrator", "Systems Analyst", "Project Manager", "Technical Writer", "Content Strategist", "Public Relations Specialist", "Event Planner", "Account Manager", "Brand Manager", "Social Media Manager", "Market Research Analyst", "Investment Banker", "Management Consultant", "Human Factors Engineer", "Industrial Designer", "Mechanical Engineer", "Electrical Engineer", "Chemical Engineer", "Civil Engineer", "Environmental Engineer", "Architect", "Medical Doctor", "Nurse Practitioner", "Physical Therapist", "Occupational Therapist", "Pharmacist", "Dentist", "Veterinarian", "Medical Laboratory Technician", "Medical Technologist", "Radiologic Technologist", "Clinical Research Coordinator", "Clinical Nurse Specialist", "Health Informatics Specialist", "Healthcare Administrator", "Psychologist", "Social Worker", "Counselor", "Interpreter/Translator", "Technical Support Specialist"};

    private String[] qualifications = {"Select Qualification", "Bachelor's Degree in Computer Science",
            "Master's Degree in Business Administration",
            "Certification in Project Management",
            "Certification in Agile Development",
            "Bachelor's Degree in Mathematics",
            "Bachelor's Degree in Physics",
            "Bachelor's Degree in Chemistry",
            "Bachelor's Degree in Biology",
            "Master's Degree in Data Science",
            "Master's Degree in Information Technology",
            "Certification in Cloud Computing",
            "Certification in Cybersecurity",
            "Bachelor's Degree in Electrical Engineering",
            "Bachelor's Degree in Mechanical Engineering",
            "Bachelor's Degree in Civil Engineering",
            "Master's Degree in Economics",
            "Certification in Six Sigma",
            "Certification in Scrum",
            "Certification in ITIL",
            "Certification in Lean Manufacturing",
            "Certification in Quality Management",
            "Certification in Risk Management",
            "Certification in Human Resource Management",
            "Bachelor's Degree in Accounting",
            "Bachelor's Degree in Finance",
            "Master's Degree in Marketing",
            "Certification in Digital Marketing",
            "Certification in Social Media Marketing",
            "Certification in Search Engine Optimization",
            "Certification in Email Marketing",
            "Certification in Content Marketing",
            "Certification in Google Analytics",
            "Certification in Google AdWords",
            "Certification in Microsoft Office",
            "Certification in Adobe Creative Suite",
            "Certification in Salesforce",
            "Bachelor's Degree in Psychology",
            "Master's Degree in Counseling",
            "Certification in Life Coaching",
            "Certification in NLP",
            "Certification in Yoga Teaching",
            "Certification in Pilates Teaching",
            "Certification in Personal Training",
            "Certification in Nutrition",
            "Certification in Massage Therapy",
            "Certification in Acupuncture",
            "Certification in Reiki",
            "Certification in Reflexology",
            "Certification in Aromatherapy",
            "Certification in Herbalism",
            "Certification in Homeopathy",
            "Certification in Naturopathy"};

    ArrayList<String> jobEligibilitiesList = new ArrayList<>();
    ArrayList<String> jobFacilitiesList = new ArrayList<>();

    ArrayList<String> selectedEligibilities;
    ArrayList<String> selectedFacilities;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefe = new SharedPrefe(this);
        profileUtils = new ProfileUtils(AddJobsActivity.this);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        title = getIntent().getStringExtra("title");
//        internet = getIntent().getStringExtra("cominternet");
//        companyLogo = getIntent().getStringExtra("image");


//        Toast.makeText(this, "" + companyLogo, Toast.LENGTH_SHORT).show();

        userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


//        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        allUserNormalJobs = FirebaseDatabase.getInstance().getReference("allUserNormalJobs");

        binding.textView28.setOnClickListener(v -> {
            profileUtils.deleteSelectedJobLists();
        });


//        if (title != null) {
//            binding.comTitle.setText(title);
//
//        }
//        if (internet != null) {
//            binding.txtCompany.setText(internet);
//        }

//        Intent i = getIntent();
        //job position
        binding.txtPositon.setText(sharedPrefe.fetchJobPosition());
        //job location

        binding.txtLocation.setText(sharedPrefe.fetchJobLocation());

//        String title = i.getStringExtra("title");
//        binding.txtCompany.setText(title);


        binding.txtDescription.setText(sharedPrefe.fetchDescription());
        binding.comTitle.setText(sharedPrefe.fetchComTitle());
        binding.txtCompany.setText(sharedPrefe.fetchCompany());

        selectedEligibilities = profileUtils.fetchSelectedjobEligibilities();
        selectedFacilities = profileUtils.fetchSelectedjobFacilities();


        iconChange();
        fetchListofSelected();

        loadingAlertDialog();

        binding.BtnSalary.setOnClickListener(view -> {

            if (binding.BtnSalaryADD.getVisibility() == View.GONE) {
                binding.BtnSalaryADD.setVisibility(View.VISIBLE);
                binding.btnAddJobSalary.setImageResource(R.drawable.delete);
            } else {
                binding.BtnSalaryADD.setVisibility(View.GONE);
                binding.btnAddJobSalary.setImageResource(R.drawable.addddd);
            }
        });

        binding.btnSalaryEnterAmount.setOnClickListener(view -> {

            if (binding.radioGroup.getCheckedRadioButtonId() == -1)
                Toast.makeText(this, "Select salary type", Toast.LENGTH_SHORT).show();
            else {
                // get selected radio button from radioGroup
                int selectedId = binding.radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
                View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.layout_salary, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

                bottomSheetDialog.setContentView(bottomsheetView);
                bottomSheetDialog.show();

                EditText editTextSalary = bottomsheetView.findViewById(R.id.etSalaryJob);

                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                Button btnSaveSalary = bottomsheetView.findViewById(R.id.btnSaveSalaryJob);
                btnSaveSalary.setOnClickListener(view1 -> {
                    if (editTextSalary.getText().toString().isEmpty())
                        editTextSalary.setError("Enter amount");
                    else {
                        jobSalary = editTextSalary.getText().toString() + " " + selectedRadioButton.getText().toString();
                        bottomSheetDialog.dismiss();
                        binding.BtnSalaryADD.setVisibility(View.GONE);
                        binding.btnAddJobSalary.setImageResource(R.drawable.addddd);
                        binding.txtSalary.setText(jobSalary);

                    }

                });

            }

        });

        binding.BtnEligibility.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.layout_eligibility, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();

            EditText et1 = bottomsheetView.findViewById(R.id.etElgibility1);
            EditText et2 = bottomsheetView.findViewById(R.id.etElgibility2);
            EditText et3 = bottomsheetView.findViewById(R.id.etElgibility3);
            EditText et4 = bottomsheetView.findViewById(R.id.etElgibility4);
            Button btnSaveEligibilities = bottomsheetView.findViewById(R.id.btnSaveEligibilities);


            btnSaveEligibilities.setOnClickListener(view1 -> {

                if (et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() || et4.getText().toString().isEmpty())
                    Toast.makeText(this, "Add any eligibility", Toast.LENGTH_SHORT).show();
                else {
//                    jobEligibilities = et1.getText().toString() + " , " + et2.getText().toString() + " , " + et3.getText().toString() + " , " + et4.getText().toString();
                    jobEligibilitiesList.add(et1.getText().toString());
                    jobEligibilitiesList.add(et2.getText().toString());
                    jobEligibilitiesList.add(et3.getText().toString());
                    jobEligibilitiesList.add(et4.getText().toString());
                    profileUtils.saveSelectedjobEligibilities(jobEligibilitiesList);
//                    binding.txtEligibility.setText(jobEligibilities);
                    bottomSheetDialog.dismiss();
                    startActivity(new Intent(AddJobsActivity.this,AddJobsActivity.class));
                    finish();
                }
            });
        });

        binding.BtnExperience.setOnClickListener(view -> {

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.layout_experience, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();


            @SuppressLint({"MissingInflatedId", "LocalSuppress"})

            RadioGroup radioGroup = bottomsheetView.findViewById(R.id.radioGroupExperience);
            RadioButton radioFresher = bottomsheetView.findViewById(R.id.radioBtnFresher);
            RadioButton radioBtn6Months = bottomsheetView.findViewById(R.id.radioBtn6Months);
            RadioButton radioBtn1to5Years = bottomsheetView.findViewById(R.id.radioBtn1to5Years);
            RadioButton radioBtn5to10Years = bottomsheetView.findViewById(R.id.radioBtn5to10Years);
            RadioButton radioBtn10PlusYears = bottomsheetView.findViewById(R.id.radioBtn10PlusYears);

            radioFresher.setOnClickListener(view1 -> {
                setExperience(radioFresher.getText().toString());
                bottomSheetDialog.dismiss();
            });

            radioBtn6Months.setOnClickListener(view1 -> {
                setExperience(radioBtn6Months.getText().toString());
                bottomSheetDialog.dismiss();
            });

            radioBtn1to5Years.setOnClickListener(view1 -> {
                setExperience(radioBtn1to5Years.getText().toString());
                bottomSheetDialog.dismiss();
            });

            radioBtn5to10Years.setOnClickListener(view1 -> {
                setExperience(radioBtn5to10Years.getText().toString());
                bottomSheetDialog.dismiss();
            });

            radioBtn10PlusYears.setOnClickListener(view1 -> {
                setExperience(radioBtn10PlusYears.getText().toString());
                bottomSheetDialog.dismiss();
            });


        });
        binding.BtnQualifications.setOnClickListener(view -> {
            if (binding.BtnQualificationsADD.getVisibility() == View.GONE) {

                binding.BtnQualificationsADD.setVisibility(View.VISIBLE);
                binding.btnAddJobQualification.setImageResource(R.drawable.delete);
            } else {
                binding.BtnQualificationsADD.setVisibility(View.GONE);
                binding.btnAddJobQualification.setImageResource(R.drawable.addddd);
            }
        });
        binding.btnAddQualification.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.layout_qualification, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();
            Spinner spinner = bottomsheetView.findViewById(R.id.spinnerQualifications);
            Button btnSave = bottomsheetView.findViewById(R.id.btnSaveQualificationJob);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualifications);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            btnSave.setOnClickListener(view1 -> {
                if (spinner.getSelectedItem().equals("Select Qualification"))
                    Toast.makeText(this, "Select a valid qualification", Toast.LENGTH_SHORT).show();
                else {
                    jobQualification = spinner.getSelectedItem().toString();
                    bottomSheetDialog.dismiss();
                    binding.BtnQualificationsADD.setVisibility(View.GONE);
                    binding.btnAddJobQualification.setImageResource(R.drawable.addddd);
                    binding.txtQualification.setText(jobQualification);
                }
            });


//            setSpinner();

        });
        binding.BtnFacilities.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.layout_facilities, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();

//            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            EditText et11 = bottomsheetView.findViewById(R.id.etFacility1);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            EditText et22 = bottomsheetView.findViewById(R.id.etFacility2);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            EditText et33 = bottomsheetView.findViewById(R.id.etFacility3);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            EditText et44 = bottomsheetView.findViewById(R.id.etFacility4);
//            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
            Button btnSaveFacility = bottomsheetView.findViewById(R.id.btnSaveFacilities);


            btnSaveFacility.setOnClickListener(view1 -> {

                if (et11.getText().toString().isEmpty() || et22.getText().toString().isEmpty() || et33.getText().toString().isEmpty() || et44.getText().toString().isEmpty())
                    Toast.makeText(this, "Add any Facility", Toast.LENGTH_SHORT).show();
                else {
//                    jobFacilities =
//                            et1.getText().toString()  + " , " +
//                            et2.getText().toString() + " , " +
//                                    et3.getText().toString() + " , " +
//                                    et4.getText().toString();
                    jobFacilitiesList.add(et11.getText().toString());
                    jobFacilitiesList.add(et22.getText().toString());
                    jobFacilitiesList.add(et33.getText().toString());
                    jobFacilitiesList.add(et44.getText().toString());
                    profileUtils.saveSelectedjobFacilities(jobFacilitiesList);

//                    binding.txtFacilities.setText(jobFacilities);
                    bottomSheetDialog.dismiss();
                    startActivity(new Intent(AddJobsActivity.this,AddJobsActivity.class));
                    finish();

                }
            });


        });
        binding.BtnSpecialization.setOnClickListener(view -> {
            if (binding.BtnSpecializationADD.getVisibility() == View.GONE) {

                binding.BtnSpecializationADD.setVisibility(View.VISIBLE);
                binding.btnAddJobSpecialization.setImageResource(R.drawable.delete);
            } else {
                binding.BtnSpecializationADD.setVisibility(View.GONE);
                binding.btnAddJobSpecialization.setImageResource(R.drawable.addddd);
            }


        });
        binding.btnAddSpecialization.setOnClickListener(view1 -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.layout_specialization, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();
            Spinner spinner = bottomsheetView.findViewById(R.id.spinnerSpecialization);
            Button btnSave = bottomsheetView.findViewById(R.id.btnSaveSpecializationJob);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jobSpecializations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            btnSave.setOnClickListener(view -> {
                if (spinner.getSelectedItem().equals("Select Specialization"))
                    Toast.makeText(this, "Select valid specialization", Toast.LENGTH_SHORT).show();
                else {
                    jobSpecialization = spinner.getSelectedItem().toString();
                    bottomSheetDialog.dismiss();
                    binding.BtnSpecializationADD.setVisibility(View.GONE);
                    binding.btnAddJobSpecialization.setImageResource(R.drawable.addddd);
                    binding.txtSpecialization.setText(jobSpecialization);
                }
            });

        });

        binding.jobPosition.setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), JobPosition.class));
            finish();
        });

        binding.btnWorkplaceType.setOnClickListener(view -> {

            BottomSheetDialog dialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);

            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.bottom_sheet_workplace, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

            dialog.setContentView(bottomsheetView);
            dialog.show();

            RadioButton onsite = bottomsheetView.findViewById(R.id.onSitebtn);
            RadioButton hybrid = bottomsheetView.findViewById(R.id.hybridBtn);
            RadioButton remote = bottomsheetView.findViewById(R.id.remoteBtn);


            onsite.setOnClickListener(view1 -> {
                String onSitTxt = onsite.getText().toString();

                binding.txtWorkplace.setText(onSitTxt);
                binding.btnAddTypeOfWorkPlace.setVisibility(View.INVISIBLE);
                binding.btnEditTypeOfWorkPlace.setVisibility(View.VISIBLE);
                dialog.dismiss();
            });

            hybrid.setOnClickListener(view12 -> {


                String hybridTxt = hybrid.getText().toString();
                binding.txtWorkplace.setText(hybridTxt);
                binding.btnAddTypeOfWorkPlace.setVisibility(View.INVISIBLE);
                binding.btnEditTypeOfWorkPlace.setVisibility(View.VISIBLE);
                dialog.dismiss();

            });

            remote.setOnClickListener(view13 -> {
                String remoteTxt = remote.getText().toString();
                binding.txtWorkplace.setText(remoteTxt);
                binding.btnAddTypeOfWorkPlace.setVisibility(View.INVISIBLE);
                binding.btnEditTypeOfWorkPlace.setVisibility(View.VISIBLE);
                dialog.dismiss();
            });


        });

        binding.locationBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), JobLocationActivity.class)));

        binding.btnCompany.setOnClickListener(view -> {
//                companyBottomSheet()
            startActivity(new Intent(AddJobsActivity.this, CompanyActivity.class));
            finish();
        });

        binding.employmentBtn.setOnClickListener(view -> {

            bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);


            bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.bottom_sheet_employment, findViewById(R.id.bottom_sheet_container));

            bottomSheetDialog.setContentView(bottomsheetView);
            bottomSheetDialog.show();

            RadioButton fullTime = bottomsheetView.findViewById(R.id.fulltime);
            RadioButton partTime = bottomsheetView.findViewById(R.id.partTime);
            RadioButton contract = bottomsheetView.findViewById(R.id.contract);
            RadioButton temporary = bottomsheetView.findViewById(R.id.temporary);
            RadioButton volunteer = bottomsheetView.findViewById(R.id.volunteer);
            RadioButton apprenticeship = bottomsheetView.findViewById(R.id.apprenticeship);

            fullTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String fullTimeTxt = fullTime.getText().toString();

                    binding.employmentTxt.setText(fullTimeTxt);
                    dialogandimagechange();

                }
            });
            partTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String partTimeTxt = partTime.getText().toString();

                    binding.employmentTxt.setText(partTimeTxt);
                    dialogandimagechange();


                }
            });

            contract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String contractTxt = contract.getText().toString();

                    binding.employmentTxt.setText(contractTxt);
                    dialogandimagechange();

                }
            });

            temporary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String temporaryTxt = contract.getText().toString();

                    binding.employmentTxt.setText(temporaryTxt);
                    dialogandimagechange();


                }
            });
            volunteer.setOnClickListener(view14 -> {

                String volunteerTxt = volunteer.getText().toString();

                binding.employmentTxt.setText(volunteerTxt);
                dialogandimagechange();


            });

            apprenticeship.setOnClickListener(view15 -> {

                String apprenticeShipTxt = apprenticeship.getText().toString();

                binding.employmentTxt.setText(apprenticeShipTxt);
                dialogandimagechange();


            });


        });

        binding.postBtn.setOnClickListener(view -> {
            checkValidation();

        });

        binding.cancelBtn.setOnClickListener(view -> finish());

    }

    private void fetchListofSelected() {
        //EligibilitiesListTextSet
        if (selectedEligibilities != null) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < selectedEligibilities.size(); i++) {
                TextView tv = new TextView(AddJobsActivity.this);
                tv.setLayoutParams(lparams);
                tv.setText(selectedEligibilities.get(i));
                binding.textlayoutEligibility.addView(tv);
            }
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        //FacilitiesListTextSet
        if (selectedFacilities != null) {
            LinearLayout.LayoutParams lparamse = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i1 = 0; i1 < selectedFacilities.size(); i1++) {
                TextView tv2 = new TextView(AddJobsActivity.this);
                tv2.setLayoutParams(lparamse);
                tv2.setText(selectedFacilities.get(i1));
                binding.facilitiesTxtLayout.addView(tv2);
            }
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

    }

    private void setExperience(String experience) {

        jobExperience = experience;
        binding.txtExperience.setText(experience);

    }

    private void setSpinner() {

        ArrayList<String> list = new ArrayList<>();


    }

    private void checkValidation() {
        if (sharedPrefe.fetchComTitle() == null) {
            Toast.makeText(this, "Title not set", Toast.LENGTH_SHORT).show();
        } else if (binding.txtPositon.getText().toString().isEmpty()) {
            Toast.makeText(this, "Position is not added", Toast.LENGTH_SHORT).show();
        } else if (binding.txtLocation.getText().toString().isEmpty()) {
            Toast.makeText(this, "Location not added", Toast.LENGTH_SHORT).show();
        } else if (binding.employmentTxt.getText().toString().equals("Click to select")) {
            Toast.makeText(this, "Select employment type", Toast.LENGTH_SHORT).show();
        } else if (binding.txtWorkplace.getText().toString().equals("Click to select")) {
            Toast.makeText(this, "Select workplace type", Toast.LENGTH_SHORT).show();
        } else if (jobSalary == null) {
            Toast.makeText(this, "Salary not added", Toast.LENGTH_SHORT).show();
        } else if (jobQualification == null) {
            Toast.makeText(this, "Qualification not added", Toast.LENGTH_SHORT).show();
        } else if (jobEligibilitiesList == null) {
            Toast.makeText(this, "Eligbilites not added", Toast.LENGTH_SHORT).show();
        } else if (jobExperience == null) {
            Toast.makeText(this, "Experience not added", Toast.LENGTH_SHORT).show();
        } else if (jobSpecialization == null) {
            Toast.makeText(this, "Specialization not selected", Toast.LENGTH_SHORT).show();
        } else if (jobFacilitiesList == null) {
            Toast.makeText(this, "Facilities not set for the job", Toast.LENGTH_SHORT).show();
        } else {
            postJob();
        }
    }

    private void postJob() {
        uniqueKey = UUID.randomUUID().toString();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String stDate = dateFormat.format(date);
        userJobModel = new UserJobModel(
                sharedPrefe.fetchTitle(),
                binding.txtPositon.getText().toString(),
                binding.txtLocation.getText().toString(),
                binding.employmentTxt.getText().toString(),
                binding.txtWorkplace.getText().toString(),
                binding.txtDescription.getText().toString(),
                uniqueKey,
                userUid,
                binding.comTitle.getText().toString(),
                binding.txtCompany.getText().toString(),
                sharedPrefe.fetchComImageURl(),
                stDate,
                jobSalary,
                jobQualification,
                jobExperience,
                jobSpecialization
        );
        loadingDialog.show();

//        allUserNormalJobs.child(uniqueKey).setValue(userJobModel).addOnSuccessListener(unused ->
//                Toast.makeText(AddJobsActivity.this, "JobPosted in AllUserNote...\n;)", Toast.LENGTH_SHORT).show());

        allUserNormalJobs
                .child(uniqueKey)
                .setValue(userJobModel)
                .addOnSuccessListener(unused -> {

                    allUserNormalJobs
                            .child(uniqueKey).child("jobEligibilities")
                            .setValue(profileUtils.fetchSelectedjobEligibilities());
                    allUserNormalJobs
                            .child(uniqueKey).child("jobFacilities")
                            .setValue(profileUtils.fetchSelectedjobFacilities()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    loadingDialog.dismiss();
                                    sharedPrefe.deleteAllsharedPre();
                                    profileUtils.deleteSelectedJobLists();
//                    startActivity(new Intent(AddJobsActivity.this, UserJobDetailsActivity.class));
                                    Toast.makeText(AddJobsActivity.this, "Job submitted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddJobsActivity.this, UserJobDetailsActivity.class);
                                    intent.putExtra("unikey", uniqueKey);
                                    startActivity(intent);
                                    finish();
                                }
                            });


                }).addOnFailureListener(e -> {
                    loadingDialog.dismiss();
                    Toast.makeText(AddJobsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });

    }

//    private void getImage() {
//        File imgFile = new File(companyLogo);
//        if (imgFile.exists()) {
//            ImageView myImage = new ImageView(this);
//            myImage.setImageURI(Uri.fromFile(imgFile));
//
//        }
//
//    }

    private void iconChange() {


        if (binding.txtPositon.length() != 0) {
            binding.btnEditJobPosition.setVisibility(View.VISIBLE);
            binding.btnAddJobPosition.setVisibility(View.INVISIBLE);
        } else {
            binding.btnAddJobPosition.setVisibility(View.VISIBLE);
            binding.btnEditJobPosition.setVisibility(View.INVISIBLE);
        }

        if (binding.txtDescription.length() != 0) {

            binding.btnEditJobDescription.setVisibility(View.VISIBLE);
            binding.btnAddJobDescription.setVisibility(View.INVISIBLE);

        } else {

            binding.btnEditJobDescription.setVisibility(View.INVISIBLE);
            binding.btnAddJobDescription.setVisibility(View.VISIBLE);
        }

        if (binding.txtLocation.length() != 0) {
            binding.btnEditJobLocation.setVisibility(View.VISIBLE);
            binding.btnAddJobLocation.setVisibility(View.INVISIBLE);
        } else {
            binding.btnEditJobLocation.setVisibility(View.INVISIBLE);
            binding.btnAddJobLocation.setVisibility(View.VISIBLE);

        }
        //eligibilities
        if (selectedEligibilities != null) {
            binding.textlayoutEligibility.setVisibility(View.VISIBLE);
            binding.txtEligibility.setVisibility(View.GONE);
        } else {
            binding.textlayoutEligibility.setVisibility(View.GONE);
            binding.txtEligibility.setVisibility(View.VISIBLE);

        }
        //facilities
        if (selectedFacilities != null) {
            binding.facilitiesTxtLayout.setVisibility(View.VISIBLE);
            binding.txtFacilities.setVisibility(View.GONE);
        } else {
            binding.facilitiesTxtLayout.setVisibility(View.GONE);
            binding.txtFacilities.setVisibility(View.VISIBLE);

        }


    }

    public void dialogandimagechange() {
        binding.btnAddEmpolyment.setVisibility(View.INVISIBLE);
        binding.btnEditEmployment.setVisibility(View.VISIBLE);
        bottomSheetDialog.dismiss();
    }

    public void savecompanytitle() {
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("companyTitle", binding.comTitle.getText().toString());
        editor.apply();
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    public void loadPreference() {
        String savedtitle;
        SharedPreferences preferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        savedtitle = preferences.getString("companyTitle", "");
        binding.comTitle.setText(savedtitle);
    }

    public void loadingAlertDialog() {

        HandloadingDialogLayoutBinding handloadingBinding = HandloadingDialogLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(AddJobsActivity.this)
                .setView(handloadingBinding.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


}

