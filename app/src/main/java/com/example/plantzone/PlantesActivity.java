package com.example.plantzone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlantesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantes);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_plantes);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inici) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_diagnosticar) {
                startActivity(new Intent(this, DiagnosticarActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_plantes) {
                // ja estem aquí
                return true;
            } else if (id == R.id.nav_mes) {
                startActivity(new Intent(this, MesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        // FAB càmera
        ImageButton fabCamera = findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class))
        );
    }
}