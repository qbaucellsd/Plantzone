package com.example.plantzone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

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

        // --- Launchers de cámara ---
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadFragment(new DiagnosticarFragment());
                        findViewById(R.id.bottomNav)
                                .post(() -> ((BottomNavigationView) findViewById(R.id.bottomNav))
                                        .setSelectedItemId(R.id.nav_diagnosticar));
                    }
                }
        );

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> { if (granted) abrirCamara(); }
        );

        // --- FAB càmera ---
        ImageButton fabCamera = findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        // --- NavBar ---
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        if (savedInstanceState == null) {
            loadFragment(new IniciFragment());
            bottomNav.setSelectedItemId(R.id.nav_inici);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inici)        { loadFragment(new IniciFragment());       return true; }
            if (id == R.id.nav_diagnosticar) { loadFragment(new DiagnosticarFragment()); return true; }
            if (id == R.id.nav_plantes)      { loadFragment(new PlantesFragment());     return true; }
            if (id == R.id.nav_mes)          { loadFragment(new MesFragment());         return true; }
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

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}