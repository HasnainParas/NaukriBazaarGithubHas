package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddNormalJobPostBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Objects;

public class AddNormalJobPostActivity extends AppCompatActivity {

    private ActivityAddNormalJobPostBinding binding;
    private SharedPrefe sharedPrefe;
    private static final int CAMERA_PIC_REQUEST = 1;
    private static final int GALLERY_PIC_REQUEST = 2;
    private String userId;
    private String username, useraddress;


    private ProfileUtils profileUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddNormalJobPostBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        sharedPrefe = new SharedPrefe(AddNormalJobPostActivity.this);

        profileUtils= new ProfileUtils(this);

        userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        username = getIntent().getStringExtra("username");
        useraddress = getIntent().getStringExtra("useraddress");

        binding.username.setText(username);

        Glide.with(this).load(profileUtils.fetchUserImage()).placeholder(R.drawable.profileplace).into(binding.userpicture);



        binding.useraddress.setText(useraddress);

        binding.icBck.setOnClickListener(view -> finish());




        binding.imgCamera.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        });
        binding.imgGallery.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_PIC_REQUEST);
        });

        binding.hashtagid.setOnClickListener(view -> {

        });
        binding.postbtn.setOnClickListener(view -> {
            if (binding.etPostTitleTx.getText().toString().isEmpty()) {
                binding.etPostTitleTx.setError("Field Can't be empty");
                return;
            } else if (binding.etDescription.getText().toString().isEmpty()) {
                binding.etDescription.setError("Field Can't be empty");
                return;
            } else {
                Intent intent = new Intent(getApplicationContext(), AddJobsActivity.class);
                String descriptionTxt = binding.etDescription.getText().toString();
                String titleTxt = binding.etPostTitleTx.getText().toString();
                sharedPrefe.saveDescription(descriptionTxt);
                sharedPrefe.saveTitle(titleTxt);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            try {
                assert data != null;
                Bitmap image = (Bitmap) data.getExtras().get("data");
//                ImageView imageview = (ImageView) findViewById(R.id.imageView18); //sets imageview as the bitmap
                binding.userpicture.setImageBitmap(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == GALLERY_PIC_REQUEST && resultCode == RESULT_OK) {
            try {
                assert data != null;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
//                ImageView imageview = (ImageView) findViewById(R.id.imageView18); //sets imageview as the bitmap
                binding.userpicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
