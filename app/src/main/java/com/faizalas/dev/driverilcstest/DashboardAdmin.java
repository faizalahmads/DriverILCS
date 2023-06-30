package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardAdmin extends AppCompatActivity {

    Button BtnUser, BtnKendaraan,BtnCekJadwal, BtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        BtnUser = findViewById(R.id.btnUser);
        BtnKendaraan = findViewById(R.id.btnKendaraan);
        BtnCekJadwal = findViewById(R.id.btnCekJadwal);
        BtnLogout = findViewById(R.id.btnLogout);

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

        BtnCekJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CekJadwal.class);
                startActivity(i);
            }
        });

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSession();
                navigateToLogin();
            }
        });
    }

    private void clearSession() {
    }

    private void navigateToLogin() {
        Intent intent = new Intent(DashboardAdmin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}