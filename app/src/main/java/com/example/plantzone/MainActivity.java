package com.example.plantzone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> permissionLauncher;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Launcher para el resultado de la cámara
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Foto capturada correctamente — photoUri tiene la imagen
                        // Aquí puedes procesarla o pasarla a otra Activity
                        Intent intent = new Intent(this, DiagnosticarActivity.class);
                        intent.putExtra("photo_uri", photoUri.toString());
                        startActivity(intent);
                    }
                }
        );

        // Launcher para pedir permiso de cámara
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {
                    if (granted) {
                        abrirCamara();
                    }
                }
        );

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_inici);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inici) {
                return true;
            } else if (id == R.id.nav_diagnosticar) {
                startActivity(new Intent(this, DiagnosticarActivity.class));
                return true;
            } else if (id == R.id.nav_camera) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    abrirCamara();
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA);
                }
                return true;
            } else if (id == R.id.nav_plantes) {
                startActivity(new Intent(this, PlantesActivity.class));
                return true;
            } else if (id == R.id.nav_mes) {
                startActivity(new Intent(this, MesActivity.class));
                return true;
            }
            return false;
        });
    }

    private void abrirCamara() {
        try {
            File photoFile = crearArxiuFoto();
            photoUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".fileprovider",
                    photoFile
            );
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File crearArxiuFoto() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile("PLANT_" + timestamp, ".jpg", storageDir);
    }
}