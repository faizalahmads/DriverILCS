package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDriver extends AppCompatActivity {

    Button BtnKendaraan, BtnCekJadwal, BtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_driver);

        BtnKendaraan = findViewById(R.id.btnKendaraan);
        BtnCekJadwal = findViewById(R.id.btnCekJadwalDriver);
        BtnLogout = findViewById(R.id.btnLogout);


        BtnKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KendaraanDriver.class);
                startActivity(i);
            }
        });

        BtnCekJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CekJadwalDriver.class);
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
        Intent intent = new Intent(DashboardDriver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
