package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardAdmin extends AppCompatActivity {

    Button BtnUser, BtnKendaraan, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        BtnUser = findViewById(R.id.btnUser);
        btnLogout = findViewById(R.id.btnLogout);

        BtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageAkun.class);
                startActivity(i);
            }
        });

        BtnKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Kendaraan.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lakukan tindakan logout di sini, seperti membersihkan sesi atau mengarahkan pengguna ke halaman login
                // Contoh: Membersihkan sesi dan mengarahkan pengguna ke halaman login
                clearSession();
                navigateToLogin();
            }
        });
    }

    private void clearSession() {
        // Lakukan penyingkiran sesi atau data pengguna yang disimpan saat logout di sini
    }

    private void navigateToLogin() {
        // Arahkan pengguna ke halaman login di sini
        Intent intent = new Intent(DashboardAdmin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}