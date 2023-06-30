package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardUser extends AppCompatActivity {
    Button profileUser, cekjadwalUser, btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        profileUser = findViewById(R.id.btnProfile);
        cekjadwalUser = findViewById(R.id.btnCekJadwal);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSession();
                navigateToLogin();
            }
        });

        profileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        cekjadwalUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CekJadwalUser.class);
                startActivity(intent);
            }
        });
    }

    private void clearSession() {
    }

    private void navigateToLogin() {
        Intent intent = new Intent(DashboardUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}