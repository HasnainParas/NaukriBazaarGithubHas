package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.ImageView;
import android.widget.RadioButton;

import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.CompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.InstantCompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.AddInstantJobActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.NewActivities.Models.UserJobModel;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class AddJobsActivity extends AppCompatActivity {
    private SharedPrefe sharedPrefe;
    private ActivityAddJobsBinding binding;
    private BottomSheetDialog bottomSheetDialog;
    //    int checkRadio;
    private DatabaseReference userJobRef, allUserJobs, databaseReference;
    private UserJobModel userJobModel;
    private String userUid;
    private AlertDialog loadingDialog;
    private ArrayList<CompanyModel> companyList;
    private CompanyAdapter companyJobAdapter;

    private ProfileUtils profileUtils;
    private String uniqueKey;
    private String title, internet, companyLogo;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefe = new SharedPrefe(this);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);
        title = getIntent().getStringExtra("title");
        internet = getIntent().getStringExtra("cominternet");
        companyLogo = getIntent().getStringExtra("image");


        Toast.makeText(this, "" + companyLogo, Toast.LENGTH_SHORT).show();

        userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        allUserJobs = FirebaseDatabase.getInstance().getReference("allUserJobs");
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));


        if (title != null) {
            binding.comTitle.setText(title);

        }
        if (internet != null) {
            binding.txtCompany.setText(internet);
        }

        Intent i = getIntent();
        //job position
        binding.txtPositon.setText(sharedPrefe.fetchJobPosition());
        //job location

        binding.txtLocation.setText(sharedPrefe.fetchJobLocation());

//        String title = i.getStringExtra("title");
//        binding.txtCompany.setText(title);


        binding.txtDescription.setText(sharedPrefe.fetchDescription());
        binding.comTitle.setText(sharedPrefe.fetchComTitle());
        binding.txtCompany.setText(sharedPrefe.fetchCompany());


        iconChange();
        loadingAlertDialog();


        binding.postBtn.setOnClickListener(view -> {
checkValidation();

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

        binding.cancelBtn.setOnClickListener(view -> finish());

    }

    private void checkValidation() {
        if (sharedPrefe.fetchComTitle() == null){
            Toast.makeText(this, "Title not set", Toast.LENGTH_SHORT).show();
        }else if (  binding.txtPositon.getText().toString().isEmpty()){
            Toast.makeText(this, "Position is not added", Toast.LENGTH_SHORT).show();
        }else if ( binding.txtLocation.getText().toString().isEmpty()){
            Toast.makeText(this, "Location not added", Toast.LENGTH_SHORT).show();
        }else if (binding.employmentTxt.getText().toString().equals("Click to select")){
            Toast.makeText(this, "Select employment type", Toast.LENGTH_SHORT).show();
        }else if (binding.txtWorkplace.getText().toString().equals("Click to select")){
            Toast.makeText(this, "Select workplace type", Toast.LENGTH_SHORT).show();
        }else{
            postJob();
        }
    }

    private void postJob() {
        uniqueKey = UUID.randomUUID().toString();

        userJobModel = new UserJobModel(sharedPrefe.fetchTitle(),
                binding.txtPositon.getText().toString(),

                binding.txtLocation.getText().toString(),
                binding.employmentTxt.getText().toString(),
                binding.txtWorkplace.getText().toString(),
                binding.txtDescription.getText().toString(),
                uniqueKey, userUid, binding.comTitle.getText().toString(),
                binding.txtCompany.getText().toString(), profileUtils.fetchCompanyImage(),String.valueOf(System.currentTimeMillis())


        );
        loadingDialog.show();

        allUserJobs.child(uniqueKey).setValue(userJobModel).addOnSuccessListener(unused -> Toast.makeText(AddJobsActivity.this, "JobPosted in AllUserNote...\n;)", Toast.LENGTH_SHORT).show());

        userJobRef.child(userUid).child(uniqueKey).setValue(userJobModel).addOnSuccessListener(unused -> {
            loadingDialog.dismiss();
            sharedPrefe.deleteAllsharedPre();
//                    startActivity(new Intent(AddJobsActivity.this, UserJobDetailsActivity.class));
            Toast.makeText(AddJobsActivity.this, "Job submitted Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddJobsActivity.this, UserJobDetailsActivity.class);
            intent.putExtra("unikey", uniqueKey);
            startActivity(intent);
            finish();

        }).addOnFailureListener(e -> {
            loadingDialog.dismiss();
            Toast.makeText(AddJobsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    private void getImage() {
        File imgFile = new File(companyLogo);
        if (imgFile.exists()) {
            ImageView myImage = new ImageView(this);
            myImage.setImageURI(Uri.fromFile(imgFile));

        }

    }

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

