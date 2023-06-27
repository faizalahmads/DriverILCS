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
                // Lakukan tindakan logout di sini, seperti membersihkan sesi atau mengarahkan pengguna ke halaman login
                // Contoh: Membersihkan sesi dan mengarahkan pengguna ke halaman login
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
                Intent intent = new Intent(getApplicationContext(), CekJadwal.class);
                startActivity(intent);
            }
        });
    }

    private void clearSession() {
        // Lakukan penyingkiran sesi atau data pengguna yang disimpan saat logout di sini
    }

    private void navigateToLogin() {
        // Arahkan pengguna ke halaman login di sini
        Intent intent = new Intent(DashboardUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}