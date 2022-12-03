package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddJobsBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AddJobsActivity extends AppCompatActivity {
    SharedPrefe sharedPrefe;
    ActivityAddJobsBinding binding;
    BottomSheetDialog bottomSheetDialog;
    int checkRadio;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefe = new SharedPrefe(this);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        String internet = getIntent().getStringExtra("cominternet");

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


        binding.jobPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JobPosition.class));
                finish();
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
                finish();
            }
        });

        binding.btnCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CompanyActivity.class));
                finish();
            }
        });


        binding.employmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        binding.cancelBtn.setOnClickListener(view -> finishAffinity());

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


}