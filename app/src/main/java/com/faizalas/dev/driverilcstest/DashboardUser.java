package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardUser extends AppCompatActivity {
    Button BtnPesan, BtnCekJadwalUser, BtnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        BtnCekJadwalUser = findViewById(R.id.btnCekJadwal);
        BtnPesan = findViewById(R.id.btnPesan);
        BtnLogout = findViewById(R.id.btnLogout);

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSession();
                navigateToLogin();
            }
        });

        BtnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pesan.class);
                startActivity(intent);
            }
        });

        BtnCekJadwalUser.setOnClickListener(new View.OnClickListener() {
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