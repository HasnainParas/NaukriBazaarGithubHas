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

    ActivityAddJobsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent i = getIntent();
        String mcg = i.getStringExtra("mcg");
        binding.txtPositon.setText(mcg);

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
                        binding.btnAdd2.setVisibility(View.INVISIBLE);
                        binding.btnEdit2.setVisibility(View.VISIBLE);


                    }
                });

                hybrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String hybridTxt = hybrid.getText().toString();
                        binding.txtWorkplace.setText(hybridTxt);

                    }
                });

                remote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String remoteTxt = remote.getText().toString();
                        binding.txtWorkplace.setText(remoteTxt);
                        dialog.dismiss();
                    }
                });


            }
        });


    }


    private void iconChange() {


        if (binding.txtPositon.length() != 0) {

            binding.btnEdit1.setVisibility(View.VISIBLE);
            binding.btnAdd1.setVisibility(View.INVISIBLE);


        } else {
            binding.btnAdd1.setVisibility(View.VISIBLE);
            binding.btnEdit1.setVisibility(View.INVISIBLE);
        }
    }
}