package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.CompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityCompanyBinding;
import com.appstacks.indiannaukribazaar.databinding.AddCompanyLayoutBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;

public class CompanyActivity extends AppCompatActivity {


    private ActivityCompanyBinding binding;
    private SharedPrefe sharedPrefe;
    private CompanyAdapter adapter;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private AddCompanyLayoutBinding addCompanyLayoutBinding;
    ImageView companyLogo;
    ArrayList<CompanyModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        sharedPrefe = new SharedPrefe(this);


        list.add(new CompanyModel(R.drawable.googleic, "Google", "Internet"));
        list.add(new CompanyModel(R.drawable.ic_apple, "Apple", "Electronic goods"));
        list.add(new CompanyModel(R.drawable.ic_amazon, "Amazon", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Dribble", "Design"));
        list.add(new CompanyModel(R.drawable.googleic, "Twitter", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Facebook", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Microsoft", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Allianz", "Financial Service"));
        list.add(new CompanyModel(R.drawable.googleic, "Adobe", "Computer software"));
        list.add(new CompanyModel(R.drawable.googleic, "AXA", "Insurance"));

        adapter = new CompanyAdapter(list, this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewCom.setLayoutManager(layoutManager);
        binding.recyclerViewCom.setAdapter(adapter);


//        binding.searchViewCom.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//                return false;
//            }
//
//        });
        if (adapter.getItemCount() == 0) {
            binding.layoutNothing.setVisibility(View.VISIBLE);
        } else {
            binding.layoutNothing.setVisibility(View.GONE);
        }

        binding.searchViewCom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                if (adapter.getItemCount() == 0) {
                    binding.layoutNothing.setVisibility(View.VISIBLE);

                    binding.btnAdd.setOnClickListener(view -> {
                        bottomDialog();
                    });
                } else {
                    binding.layoutNothing.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    @SuppressLint("MissingInflatedId")
    private void bottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(CompanyActivity.this, R.style.AppBottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.add_company_layout, (CardView) findViewById(R.id.UndoChanges));
        dialog.setContentView(bottomsheetView);


        dialog.show();
        dialog.setCancelable(false);
        Button addCompany = bottomsheetView.findViewById(R.id.btnAddCompany);
        EditText etCompanyType = bottomsheetView.findViewById(R.id.etCompanyType);

        ImageView cancel = bottomsheetView.findViewById(R.id.addCompanyCancelBtn);
        EditText etCompanyName = bottomsheetView.findViewById(R.id.etCompanyName);
        companyLogo = bottomsheetView.findViewById(R.id.uploadCompanyLogo);


        companyLogo.setOnClickListener(view -> {
            chooseImage();

        });
        cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        addCompany.setOnClickListener(view -> {
            if (etCompanyType.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add Company service type", Toast.LENGTH_SHORT).show();

            } else if (etCompanyName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add company name", Toast.LENGTH_SHORT).show();

            } else if (filePath == null) {
                Toast.makeText(this, "Add company logo", Toast.LENGTH_SHORT).show();

            } else {
                addCompany(etCompanyName.getText().toString(), etCompanyType.getText().toString(), filePath, dialog);
                finish();
            }
        });


    }

    private void addCompany(String companyName, String companyType, Uri filePath, BottomSheetDialog dialog) {


        list.add(new CompanyModel(companyName, companyType, filePath));
        //  list.notify();
        adapter.notifyDataSetChanged();
        dialog.cancel();
        dialog.dismiss();


    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                companyLogo.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}