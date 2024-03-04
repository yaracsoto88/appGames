package com.example.a2048;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class Setting extends AppCompatActivity {
    Button btProfile;

    private String currentPhotoPath;
    DBHelper dbHelper;

    private ActivityResultLauncher<Intent> galleryLauncher;
    String userName;
    Button btBack;
    Button btChangePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dbHelper = new DBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");


        btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener((v -> {
            Intent intent = new Intent(Setting.this, Menu.class);
            startActivity(intent);
        }));

        btProfile = findViewById(R.id.btProfile);
        btProfile.setOnClickListener((v -> captureImage()));

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            if (checkImageSize(selectedImageUri)) {
                                Bitmap imageBitmap = getBitmapFromUri(selectedImageUri);
                                dbHelper.setPhoto(userName, imageBitmap);
                                mensaje("Profile picture updated successfully");
                            } else {
                                mensaje("Error, Image too big. Max size 1MB");
                            }


                        }
                    }
                }
        );
        btChangePassword = findViewById(R.id.btCambioContraseña);
        btChangePassword.setOnClickListener((v -> {
            changePass();
        }));


    }

    private boolean checkImageSize(Uri selectedImageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
            int fileSize = inputStream.available();
            inputStream.close();
            return fileSize <= (1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void changePass() {
        EditText etPassword = findViewById(R.id.editContraseñaActual);
        EditText etNewPassword = findViewById(R.id.editNuevaContraseña);
        String password = etPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        if (password.equals("") || newPassword.equals("")) {
            etPassword.setError("Fill in the blanks");
            etNewPassword.setError("Fill in the blanks");
            mensaje("Fill in the blanks");
        } else {
            if (dbHelper.checkUserData(userName, password)) {
                dbHelper.updatePassword(userName, newPassword);
                etPassword.setText("");
                etNewPassword.setText("");
                mensaje("Password changed successfully");

            } else {
                mensaje("Incorrect password");
            }
        }
    }

    private void mensaje(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
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



