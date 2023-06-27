package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class Pesan extends AppCompatActivity {

    EditText ETnamaPesan, ETtitikAwal, ETtitikAkhir, ETjumlahPenumpang;
    Button BTNpesan;
    Spinner SpinnerJam, SpinnerPenumpang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        ETnamaPesan = findViewById(R.id.etNamaPesan);
        ETtitikAwal = findViewById(R.id.etTitikAwal);
        ETtitikAkhir = findViewById(R.id.etTitikAkhir);
        SpinnerJam = findViewById(R.id.spinnerJam);
        SpinnerPenumpang = findViewById(R.id.spinnerPenumpang);
        BTNpesan = findViewById(R.id.btnPesan);

        final Context context = this;

        // Ambil array role dari strings.xml
        String[] jamArray = getResources().getStringArray(R.array.jam_array);
        String[] penumpangArray = getResources().getStringArray(R.array.penumpang_array);

        // Buat adapter untuk spinner
        ArrayAdapter<String> jamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jamArray);
        ArrayAdapter<String> penumpangAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, penumpangArray);
        jamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerJam.setAdapter(jamAdapter);
        SpinnerPenumpang.setAdapter(penumpangAdapter);

        BTNpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = ETnamaPesan.getText().toString().trim();
                String titik_awal = ETtitikAwal.getText().toString().trim();
                String titik_akhir = ETtitikAkhir.getText().toString().trim();
                String jam = SpinnerJam.getSelectedItem().toString().trim();
                String jumlah_penumpang = SpinnerPenumpang.getSelectedItem().toString().trim();

                // Memeriksa apakah semua kolom telah diisi
                if (nama.isEmpty() || titik_awal.isEmpty() || titik_akhir.isEmpty() || jam.isEmpty() || jumlah_penumpang.isEmpty()) {
                    Toast.makeText(Pesan.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else {
                    TambahPesanTask task = new TambahPesanTask();
                    task.execute(nama, titik_awal, titik_akhir, jam, jumlah_penumpang);
                }
            }
        });
    }

    private class TambahPesanTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String nama = params[0];
            String titik_awal = params[1];
            String titik_akhir = params[2];
            String jam = params[3];
            String jumlah_penumpang = params[4];

            try {
                URL url = new URL(Urls.TAMBAH_PSN_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8") +
                        "&" + URLEncoder.encode("titik_awal", "UTF-8") + "=" + URLEncoder.encode(titik_awal, "UTF-8") +
                        "&" + URLEncoder.encode("titik_akhir", "UTF-8") + "=" + URLEncoder.encode(titik_akhir, "UTF-8") +
                        "&" + URLEncoder.encode("jam", "UTF-8") + "=" + URLEncoder.encode(jam, "UTF-8") +
                        "&" + URLEncoder.encode("jumlah_penumpang", "UTF-8") + "=" + URLEncoder.encode(jumlah_penumpang, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();

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
                Toast.makeText(Pesan.this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Pesan.this, "Tambah Gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}