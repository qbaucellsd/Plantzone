package com.example.plantzone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.ImageButton;

public class DiagnosticarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosticar);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_diagnosticar);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_inici) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.nav_diagnosticar) {
                return true;
            } else if (id == R.id.nav_plantes) {
                startActivity(new Intent(this, PlantesActivity.class));
                overridePendingTransition(0, 0);
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