package com.appstacks.indiannaukribazaar.NewActivities;

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
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityCompanyBinding;
import com.appstacks.indiannaukribazaar.databinding.AddCompanyLayoutBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CompanyActivity extends AppCompatActivity {


    private ActivityCompanyBinding binding;
    private SharedPrefe sharedPrefe;
    private CompanyAdapter adapter;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private AddCompanyLayoutBinding addCompanyLayoutBinding;
    ImageView companyLogo;
    ArrayList<CompanyModel> list;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String userId;
    private String downloadUrl;
    private CompanyModel data;
    private ProfileUtils profileUtils;
    private   CompanyModel companyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompanyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        data = new CompanyModel();
        list = new ArrayList<>();
        profileUtils = new ProfileUtils(this);
        sharedPrefe = new SharedPrefe(this);
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user_profile));
        storageReference = FirebaseStorage.getInstance().getReference(getString(R.string.user_profile));
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



            binding.btnAdd.setOnClickListener(view -> {
                bottomDialog();
            });

//        list.add(new CompanyModel(R.drawable.googleic, "Google", "Internet"));
//        list.add(new CompanyModel(R.drawable.ic_apple, "Apple", "Electronic goods"));
//        list.add(new CompanyModel(R.drawable.ic_amazon, "Amazon", "Internet"));
//        list.add(new CompanyModel(R.drawable.googleic, "Dribble", "Design"));
//        list.add(new CompanyModel(R.drawable.googleic, "Twitter", "Internet"));
//        list.add(new CompanyModel(R.drawable.googleic, "Facebook", "Internet"));
//        list.add(new CompanyModel(R.drawable.googleic, "Microsoft", "Internet"));
//        list.add(new CompanyModel(R.drawable.googleic, "Allianz", "Financial Service"));
//        list.add(new CompanyModel(R.drawable.googleic, "Adobe", "Computer software"));
//        list.add(new CompanyModel(R.drawable.googleic, "AXA", "Insurance"));


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
            Toast.makeText(CompanyActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        })).addOnFailureListener(e -> Toast.makeText(CompanyActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());


        dialog.cancel();
        dialog.dismiss();


    }

    private void uploadImageData(String downloadUrl, String companyType, String companyName) {
        String pushId = UUID.randomUUID().toString();
        data = new CompanyModel(companyName, companyType, downloadUrl, pushId);

        databaseReference.child(userId).child("Company").child(pushId).setValue(data).addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()) {
                profileUtils.saveCompanyImage(downloadUrl);
                Toast.makeText(CompanyActivity.this, "Company added", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();

        fetchCompanies();
    }

    private void fetchCompanies() {

        databaseReference.child(userId).child("Company").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                         companyData = snap.getValue(CompanyModel.class);
                        list.add(companyData);
                    }


                    adapter = new CompanyAdapter(list, CompanyActivity.this);

                    binding.recyclerViewCom.setLayoutManager(new LinearLayoutManager(CompanyActivity.this));
                    binding.recyclerViewCom.setAdapter(adapter);


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
}