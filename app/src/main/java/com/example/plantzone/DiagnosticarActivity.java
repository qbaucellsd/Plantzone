package com.example.plantzone;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
                return true;
            } else if (id == R.id.nav_diagnosticar) {
                // ja estem aquí
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
}