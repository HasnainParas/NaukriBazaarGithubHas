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

    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rg =findViewById(R.id.radio_group);

        Intent i = getIntent();
        String mcg = i.getStringExtra("mcg");
        binding.txtPositon.setText(mcg);


        if (binding.txtPositon.length() != 0) {

            binding.btnEdit1.setVisibility(View.VISIBLE);
            binding.btnAdd1.setVisibility(View.INVISIBLE);


        } else {
            binding.btnAdd1.setVisibility(View.VISIBLE);
            binding.btnEdit1.setVisibility(View.INVISIBLE);


        }


        binding.jobPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), JobPosition.class));
            }
        });
        binding.btnWorkplaceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog dialog = new BottomSheetDialog(AddJobsActivity.this,R.style.AppBottomSheetDialogTheme);

                View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.bottom_sheet_workplace,(ConstraintLayout)findViewById(R.id.bottom_sheet_container));

                dialog.setContentView(bottomsheetView);
                dialog.show();



//                bottomsheetView.findViewById(R.id.onSitebtn).setOnClickListener(new View.OnClickListener() {dw
//                    @Override
//                    public void onClick(View view) {
//
//                        bottomsheetView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//
//
//                            }
//                        });
//                    }
//                });
                
            }
        });









    }
}