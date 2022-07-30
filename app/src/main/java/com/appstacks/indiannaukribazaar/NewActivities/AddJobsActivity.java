package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddJobsBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class AddJobsActivity extends AppCompatActivity {
    SharedPrefe sharedPrefe;
    ActivityAddJobsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefe = new SharedPrefe(this);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent i = getIntent();
        //job position
        binding.txtPositon.setText(sharedPrefe.fetchJobPosition());
        //job location

        binding.txtLocation.setText(sharedPrefe.fetchJobLocation());

        String title = i.getStringExtra("title");
        binding.txtCompany.setText(title);


        binding.txtDescription.setText(sharedPrefe.fetchDescription());


        iconChange();


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


                    }
                });

                hybrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String hybridTxt = hybrid.getText().toString();
                        binding.txtWorkplace.setText(hybridTxt);
                        binding.btnAdd5.setVisibility(View.INVISIBLE);
                        binding.btnEdit5.setVisibility(View.VISIBLE);


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


                startActivity(new Intent(getApplicationContext(), CompanyActivity.class));


            }
        });


        binding.employmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BottomSheetDialog dialog = new BottomSheetDialog(AddJobsActivity.this, R.style.AppBottomSheetDialogTheme);

                View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.bottom_sheet_employment, (ConstraintLayout) findViewById(R.id.bottom_sheet_container));

                dialog.setContentView(bottomsheetView);
                dialog.show();

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
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });
                partTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String partTimeTxt = partTime.getText().toString();

                        binding.employmentTxt.setText(partTimeTxt);
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });

                contract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String contractTxt = contract.getText().toString();

                        binding.employmentTxt.setText(contractTxt);
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });

                temporary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String temporaryTxt = contract.getText().toString();

                        binding.employmentTxt.setText(temporaryTxt);
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });
                volunteer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String volunteerTxt = volunteer.getText().toString();

                        binding.employmentTxt.setText(volunteerTxt);
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });

                apprenticeship.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String apprenticeShipTxt = apprenticeship.getText().toString();

                        binding.employmentTxt.setText(apprenticeShipTxt);
                        binding.btnAdd4.setVisibility(View.INVISIBLE);
                        binding.btnEdit4.setVisibility(View.VISIBLE);

                    }
                });


            }
        });


    }


    private void iconChange() {


        if (binding.txtPositon.length() != 0)
        {

            binding.btnEdit1.setVisibility(View.VISIBLE);
            binding.btnAdd1.setVisibility(View.INVISIBLE);


        } else {
            binding.btnAdd1.setVisibility(View.VISIBLE);
            binding.btnEdit1.setVisibility(View.INVISIBLE);
        }

        if (binding.txtDescription.length()!=0){

            binding.btnEdit6.setVisibility(View.VISIBLE);
            binding.btnAdd6.setVisibility(View.INVISIBLE);

        }else {

            binding.btnEdit6.setVisibility(View.INVISIBLE);
            binding.btnAdd6.setVisibility(View.VISIBLE);
        }

        if (binding.txtLocation.length() !=0){

            binding.btnEdit3.setVisibility(View.VISIBLE);
            binding.btnAdd3.setVisibility(View.INVISIBLE);
        }else {
            binding.btnEdit3.setVisibility(View.INVISIBLE);
            binding.btnAdd3.setVisibility(View.VISIBLE);

        }


    }
}