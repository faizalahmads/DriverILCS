package com.faizalas.dev.driverilcstest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TambahKendaraan extends AppCompatActivity {

    private EditText etNamaDriver, etNomorPlat, etMerk, etTanggalService, etNipDriver, etPasswordDriver, etConfirmPassword;
    private Spinner spinnerRole;
    private Button btnTambahKendaraan;

    private List<String> roleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kendaraan);

        // Inisialisasi elemen UI
        etNamaDriver = findViewById(R.id.etNamaDriver);
        etNomorPlat = findViewById(R.id.etNomorPlat);
        etMerk = findViewById(R.id.etMerk);
        etTanggalService = findViewById(R.id.etTanggalService);
        etNipDriver = findViewById(R.id.etNipDriver);
        etPasswordDriver = findViewById(R.id.etPasswordDriver);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnTambahKendaraan = findViewById(R.id.btnTambahKendaraan);

        final Context context = this;

        String[] roleArray = getResources().getStringArray(R.array.role_knd_array);

        // Setel adapter untuk Spinner Role
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleArray);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRole.setAdapter(roleAdapter);

        // Tambahkan onClickListener untuk tombol "Tambah"
        btnTambahKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ambil nilai dari EditText dan Spinner
                String namaDriver = etNamaDriver.getText().toString().trim();
                String nomorPlat = etNomorPlat.getText().toString().trim();
                String merk = etMerk.getText().toString().trim();
                String tanggalService = etTanggalService.getText().toString().trim();
                String nipDriver = etNipDriver.getText().toString().trim();
                String passwordDriver = etPasswordDriver.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String role = getSelectedRole().trim();

                // Memeriksa apakah semua kolom telah diisi
                if (namaDriver.isEmpty() || nomorPlat.isEmpty() || merk.isEmpty() || tanggalService.isEmpty() || nipDriver.isEmpty() || passwordDriver.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(TambahKendaraan.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else if (!passwordDriver.equals(confirmPassword)) {
                    Toast.makeText(TambahKendaraan.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    TambahKendaraanTask task = new TambahKendaraanTask();
                    task.execute(namaDriver, nomorPlat, merk, tanggalService, nipDriver, passwordDriver, role);
                }
            }
        });
    }

    private class TambahKendaraanTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String namaDriver = params[0];
            String nomorPlat = params[1];
            String merk = params[2];
            String tanggalService = params[3];
            String nipDriver = params[4];
            String passwordDriver = params[5];
            String role = params[6];

            try {
                // Membuat koneksi HTTP
                URL url = new URL(Urls.TAMBAH_KND_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Mengirim data ke server
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("nama_driver", "UTF-8") + "=" + URLEncoder.encode(namaDriver, "UTF-8") +
                        "&" + URLEncoder.encode("nomor_plat", "UTF-8") + "=" + URLEncoder.encode(nomorPlat, "UTF-8") +
                        "&" + URLEncoder.encode("merk", "UTF-8") + "=" + URLEncoder.encode(merk, "UTF-8") +
                        "&" + URLEncoder.encode("jadwal_service", "UTF-8") + "=" + URLEncoder.encode(tanggalService, "UTF-8") +
                        "&" + URLEncoder.encode("nip", "UTF-8") + "=" + URLEncoder.encode(nipDriver, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(passwordDriver, "UTF-8") +
                        "&" + URLEncoder.encode("role", "UTF-8") + "=" + URLEncoder.encode(role, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                outputStream.close();

                // Menerima respon dari server (jika ada)
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();

                // Menutup koneksi
                connection.disconnect();

                return response.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(TambahKendaraan.this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TambahKendaraan.this, "Tambah Gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getSelectedRole() {
        String selectedRole = spinnerRole.getSelectedItem().toString();

        if (selectedRole.equals("Direksi")) {
            return "1";
        } else if (selectedRole.equals("Komisaris")) {
            return "2";
        } else if (selectedRole.equals("Karyawan")) {
            return "3";
        }

        return "";
    }
}
