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
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class AddJobsActivity extends AppCompatActivity {
    SharedPrefe sharedPrefe;
    ActivityAddJobsBinding binding;
    BottomSheetDialog bottomSheetDialog;
    //    int checkRadio;
    DatabaseReference userJobRef, allUserJobs;
    UserJobModel userJobModel;
    String userUid;
    AlertDialog loadingDialog;
    ArrayList<CompanyModel> companyList;
    CompanyAdapter companyJobAdapter;


    String uniqueKey;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefe = new SharedPrefe(this);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        String internet = getIntent().getStringExtra("cominternet");

        userUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        userJobRef = FirebaseDatabase.getInstance().getReference("userJobs");
        allUserJobs = FirebaseDatabase.getInstance().getReference("allUserJobs");


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
//            Toast.makeText(AddJobsActivity.this, sharedPrefe.fetchTitle(), Toast.LENGTH_SHORT).show();

            uniqueKey = UUID.randomUUID().toString();

            userJobModel = new UserJobModel(sharedPrefe.fetchTitle(),
                    binding.txtPositon.getText().toString(),
                    binding.txtCompany.getText().toString(),
                    binding.txtLocation.getText().toString(),
                    binding.employmentTxt.getText().toString(),
                    binding.txtWorkplace.getText().toString(),
                    binding.txtDescription.getText().toString(),
                    uniqueKey,userUid
            );
            loadingDialog.show();

            allUserJobs.child(uniqueKey).setValue(userJobModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddJobsActivity.this, "JobPosted in AllUserNote...\n;)", Toast.LENGTH_SHORT).show();
                }
            });

            userJobRef.child(userUid).child(uniqueKey).setValue(userJobModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    loadingDialog.dismiss();
                    sharedPrefe.deleteAllsharedPre();
//                    startActivity(new Intent(AddJobsActivity.this, UserJobDetailsActivity.class));
                    Intent intent = new Intent(AddJobsActivity.this, UserJobDetailsActivity.class);
                    intent.putExtra("unikey", uniqueKey);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AddJobsActivity.this, "Job submitted Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    Toast.makeText(AddJobsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

        binding.jobPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JobPosition.class));
            }
        });

        binding.btnWorkplaceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);

                View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.bottom_sheet_workplace, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

                dialog.setContentView(bottomsheetView);
                dialog.show();

                RadioButton onsite = bottomsheetView.findViewById(R.id.onSitebtn);
                RadioButton hybrid = bottomsheetView.findViewById(R.id.hybridBtn);
                RadioButton remote = bottomsheetView.findViewById(R.id.remoteBtn);


                onsite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String onSitTxt = onsite.getText().toString();

                        binding.txtWorkplace.setText(onSitTxt);
                        binding.btnAdd5.setVisibility(View.INVISIBLE);
                        binding.btnEdit5.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });

                hybrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String hybridTxt = hybrid.getText().toString();
                        binding.txtWorkplace.setText(hybridTxt);
                        binding.btnAdd5.setVisibility(View.INVISIBLE);
                        binding.btnEdit5.setVisibility(View.VISIBLE);
                        dialog.dismiss();

                    }
                });

                remote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String remoteTxt = remote.getText().toString();
                        binding.txtWorkplace.setText(remoteTxt);
                        binding.btnAdd5.setVisibility(View.INVISIBLE);
                        binding.btnEdit5.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                });


            }
        });

        binding.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JobLocationActivity.class));

            }
        });

        binding.btnCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyBottomSheet();


            }
        });

        binding.employmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);


                bottomSheetDialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);
                View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.bottom_sheet_employment, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

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
                volunteer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String volunteerTxt = volunteer.getText().toString();

                        binding.employmentTxt.setText(volunteerTxt);
                        dialogandimagechange();


                    }
                });

                apprenticeship.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String apprenticeShipTxt = apprenticeship.getText().toString();

                        binding.employmentTxt.setText(apprenticeShipTxt);
                        dialogandimagechange();


                    }
                });


            }
        });

        binding.cancelBtn.setOnClickListener(view -> finish());

    }

    private void iconChange() {


        if (binding.txtPositon.length() != 0) {

            binding.btnEdit1.setVisibility(View.VISIBLE);
            binding.btnAdd1.setVisibility(View.INVISIBLE);


        } else {
            binding.btnAdd1.setVisibility(View.VISIBLE);
            binding.btnEdit1.setVisibility(View.INVISIBLE);
        }

        if (binding.txtDescription.length() != 0) {

            binding.btnEdit6.setVisibility(View.VISIBLE);
            binding.btnAdd6.setVisibility(View.INVISIBLE);

        } else {

            binding.btnEdit6.setVisibility(View.INVISIBLE);
            binding.btnAdd6.setVisibility(View.VISIBLE);
        }

        if (binding.txtLocation.length() != 0) {

            binding.btnEdit3.setVisibility(View.VISIBLE);
            binding.btnAdd3.setVisibility(View.INVISIBLE);
        } else {
            binding.btnEdit3.setVisibility(View.INVISIBLE);
            binding.btnAdd3.setVisibility(View.VISIBLE);

        }


    }

    public void dialogandimagechange() {
        binding.btnAdd4.setVisibility(View.INVISIBLE);
        binding.btnEdit4.setVisibility(View.VISIBLE);
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


    public void companyBottomSheet(){

        {
            BottomSheetDialog dialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);

            View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                    inflate(R.layout.activity_company, (CardView) findViewById(R.id.UndoChanges));
            dialog.setContentView(bottomsheetView);
            dialog.show();
            dialog.setCancelable(true);
            RecyclerView recyclerViewBottomSheet = bottomsheetView.findViewById(R.id.recyclerViewCom);
            SearchView searchView = bottomsheetView.findViewById(R.id.searchViewCom);

            companyList = new ArrayList<>();
            companyList.add(new CompanyModel(R.drawable.googleic, "Google", "Internet"));
            companyList.add(new CompanyModel(R.drawable.ic_apple, "Apple", "Electronic goods"));
            companyList.add(new CompanyModel(R.drawable.ic_amazon, "Amazon", "Internet"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Dribble", "Design"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Twitter", "Internet"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Facebook", "Internet"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Microsoft", "Internet"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Allianz", "Financial Service"));
            companyList.add(new CompanyModel(R.drawable.googleic, "Adobe", "Computer software"));
            companyList.add(new CompanyModel(R.drawable.googleic, "AXA", "Insurance"));
            companyJobAdapter = new CompanyAdapter(companyList, this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(AddJobsActivity.this);
            recyclerViewBottomSheet.setLayoutManager(layoutManager);
            recyclerViewBottomSheet.setAdapter(companyJobAdapter);

            searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    companyJobAdapter.getFilter().filter(s);
                    return false;
                }
            });


        }


    }



}

