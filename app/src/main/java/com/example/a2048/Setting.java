package com.example.a2048;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class Setting extends AppCompatActivity {
    Button btProfile;
    String userName;
    private String currentPhotoPath;
    DBHelper dbHelper;
    ImageView imgSetting;
    Button btBack;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener((v -> {
            Intent intent1 = new Intent(Setting.this, Menu.class);
            intent1.putExtra("UserName", userName);
            startActivity(intent1);
        }));
        imgSetting = findViewById(R.id.imgSetting);

        btProfile = findViewById(R.id.btProfile);
        btProfile.setOnClickListener((v -> captureImage()));

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            Bitmap imageBitmap = getBitmapFromUri(selectedImageUri);

                            dbHelper.setPhoto(userName, imageBitmap);
                            imgSetting.setImageBitmap(imageBitmap);


                        }
                    }
                }
        );
    }
    private void captureImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



