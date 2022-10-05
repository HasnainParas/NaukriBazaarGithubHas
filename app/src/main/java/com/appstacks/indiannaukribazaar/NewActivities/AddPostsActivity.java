package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.FirebaseModels.PersonalInformationModel;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddPostsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class AddPostsActivity extends AppCompatActivity {

    ActivityAddPostsBinding binding;
    SharedPrefe sharedPrefe;
    private static final int CAMERA_PIC_REQUEST = 1;
    private static final int GALLERY_PIC_REQUEST = 2;

    String username, useraddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefe = new SharedPrefe(AddPostsActivity.this);

        username = getIntent().getStringExtra("username");
        useraddress = getIntent().getStringExtra("useraddress");

        binding.username.setText(username);
        binding.useraddress.setText(useraddress);

        binding.icBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
        binding.imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_PIC_REQUEST);
            }
        });


        binding.postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etPostTitle.getText().toString().isEmpty()) {
                    binding.etPostTitle.setError("Field Cann't be empty");
                    return;
                } else if (binding.etDescription.getText().toString().isEmpty()) {
                    binding.etDescription.setError("Field Cann't be empty");
                    return;
                } else {
                    Intent intent = new Intent(getApplicationContext(), AddJobsActivity.class);
                    String txt = binding.etDescription.getText().toString();
                    sharedPrefe.saveDescription(txt);
                    startActivity(intent);
                }
                
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
