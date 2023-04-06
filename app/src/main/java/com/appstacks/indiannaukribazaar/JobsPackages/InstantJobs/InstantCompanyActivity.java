package com.appstacks.indiannaukribazaar.JobsPackages.InstantJobs;

import androidx.annotation.NonNull;
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
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.InstantCompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.JobsActivities.CompanyActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantCompanyBinding;
import com.appstacks.indiannaukribazaar.databinding.AddCompanyLayoutBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class InstantCompanyActivity extends AppCompatActivity {

    ActivityInstantCompanyBinding binding;
    ArrayList<CompanyModel> list;
    InstantCompanyAdapter adapter;
    ImageView companyLogo;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    private DatabaseReference databaseReference;

    private CompanyModel data;

    private AddCompanyLayoutBinding addCompanyLayoutBinding;
    private StorageReference storageReference;
    private String userId;
    private String downloadUrl;
    private ProfileUtils profileUtils;
    private CompanyModel companyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstantCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = new CompanyModel();

        profileUtils = new ProfileUtils(this);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));
        storageReference = FirebaseStorage.getInstance().getReference(getString(R.string.user_profile));

//        companyData();
        fetchCompanies();

        binding.addbtnCom.setOnClickListener(v -> bottomDialog());


    }


    //    private void companyData() {
//
//
//        list = new ArrayList<>();
//
//        //new
////        list.add(new CompanyModel(R.drawable.ic_google_logo, "Google", "Internet"));
////        list.add(new CompanyModel(R.drawable.ic_apple_logo, "Apple", "Electronic goods"));
////        list.add(new CompanyModel(R.drawable.ic_amazon_logo, "Amazon", "Internet"));
////        list.add(new CompanyModel(R.drawable.ic_dribbble_logo, "Dribble", "Design"));
////        list.add(new CompanyModel(R.drawable.ic_twitter_logo, "Twitter", "Internet"));
////        list.add(new CompanyModel(R.drawable.ic_facebook_logo, "Facebook", "Internet"));
////        list.add(new CompanyModel(R.drawable.ic_microsoft_logo, "Microsoft", "Internet"));
////        list.add(new CompanyModel(R.drawable.ic_allianz_logo, "Allianz", "Financial Service"));
////        list.add(new CompanyModel(R.drawable.ic_adobe_logo, "Adobe", "Computer software"));
////        list.add(new CompanyModel(R.drawable.ic_axa_logo, "AXA", "Insurance"));
//
//        adapter = new InstantCompanyAdapter(list, this);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(InstantCompanyActivity.this);
////        recyclerViewBottomSheet.setLayoutManager(layoutManager);
//        binding.recyclerviewInCom.setLayoutManager(layoutManager);
//        binding.recyclerviewInCom.setAdapter(adapter);
//
//        binding.searcheditbox.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                adapter.getFilter().filter(charSequence);
//                if (adapter.getItemCount() == 0) {
//                    binding.nothingincom.setVisibility(View.VISIBLE);
//
//                    binding.addbtnCom.setOnClickListener(view -> {
//                        bottomDialog();
//                    });
//                } else {
//                    binding.nothingincom.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//
////        binding.searcheditbox.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String s) {
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String s) {
////                adapter.getFilter().filter(s);
////                return false;
////            }
////        });
//
//
//    }
    private void fetchCompanies() {

        list = new ArrayList<>();
        databaseReference.child(userId).child("Company").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        companyData = snap.getValue(CompanyModel.class);
                        list.add(companyData);
                    }


                    adapter = new InstantCompanyAdapter(list, InstantCompanyActivity.this);

                    binding.recyclerviewInCom.setLayoutManager(new LinearLayoutManager(InstantCompanyActivity.this));
                    binding.recyclerviewInCom.setAdapter(adapter);


//                    if (adapter.getItemCount() == 0) {
//                        binding.layoutNothing.setVisibility(View.VISIBLE);
//                    } else {
//                        binding.layoutNothing.setVisibility(View.GONE);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @SuppressLint("MissingInflatedId")
    private void bottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(InstantCompanyActivity.this, R.style.AppBottomSheetDialogTheme);
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

            if (profileUtils.fetchCompanyImage() != null)
                profileUtils.saveUserImage(null);
            if (etCompanyType.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add Company service type", Toast.LENGTH_SHORT).show();

            } else if (etCompanyName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add company name", Toast.LENGTH_SHORT).show();

            } else if (filePath == null) {
                Toast.makeText(this, "Add company logo", Toast.LENGTH_SHORT).show();

            } else {
                addCompany(etCompanyName.getText().toString(), etCompanyType.getText().toString(), filePath, dialog);
            }
        });
    }

    private void addCompany(String companyName, String companyType, Uri filePath, BottomSheetDialog dialog) {

        StorageReference ref = storageReference.child(userId).child("company " + UUID.randomUUID().toString());

        ref.putFile(filePath).addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnCompleteListener(task -> {

            if (task.isComplete() && task.isSuccessful()) {
                downloadUrl = task.getResult().toString();

                uploadImageData(downloadUrl, companyType, companyName);
            }

        }).addOnFailureListener(e -> {
            Toast.makeText(InstantCompanyActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        })).addOnFailureListener(e -> Toast.makeText(InstantCompanyActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());

        dialog.cancel();
        dialog.dismiss();

    }

    private void uploadImageData(String downloadUrl, String companyType, String companyName) {
        String pushId = UUID.randomUUID().toString();
        data = new CompanyModel(companyName, companyType, downloadUrl, pushId);

        databaseReference.child(userId).child("Company").child(pushId).setValue(data).addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()) {
                profileUtils.saveCompanyImage(downloadUrl);
                Toast.makeText(InstantCompanyActivity.this, "Company added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCompanies();
    }
}