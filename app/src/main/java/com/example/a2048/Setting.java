package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Setting extends AppCompatActivity {
    Button btProfile;
    private static final int REQUEST_SELECT_PHOTO = 1;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btProfile = findViewById(R.id.btProfile);
        btProfile.setOnClickListener(v -> seleccionarFoto());

    }

    public void seleccionarFoto() {
        // Crea un intent para seleccionar un archivo de imagen
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // Filtra para seleccionar solo imágenes

        // Inicia la actividad para seleccionar una foto
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica si la selección de la foto fue exitosa
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                // Obtiene la URI de la foto seleccionada
                String selectedPhotoUri = data.getData().toString();
                Toast.makeText(this, "Foto seleccionada: " + selectedPhotoUri, Toast.LENGTH_SHORT).show();
                // Puedes hacer más cosas con la URI, como cargar la imagen en una ImageView
            }
        }


}
    private void savePhoto(Uri selectedPhotoUri) {
        // Crea un archivo en el directorio de la aplicación
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File imageFile = createImageFile(storageDir);
            copyPhoto(selectedPhotoUri, imageFile);
            Toast.makeText(this, "Foto guardada en: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile(File storageDir) throws IOException {
        // Crea un nombre de archivo único basado en la fecha y hora actuales
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Guarda la ruta actual del archivo para su uso posterior
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void copyPhoto(Uri selectedPhotoUri, File destinationFile) throws IOException {
        // Copia el archivo de la URI seleccionada al archivo de destino
        try (InputStream inputStream = getContentResolver().openInputStream(selectedPhotoUri);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }}