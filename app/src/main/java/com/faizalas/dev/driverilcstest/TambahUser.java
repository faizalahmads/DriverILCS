package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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

public class TambahUser extends AppCompatActivity {

    EditText etNama, etDivisi, etNip, etPassword, etConfirmPassword;
    Button btnTambahUser;
    Spinner spinnerRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        etNama = findViewById(R.id.etNamaUser);
        etDivisi = findViewById(R.id.etDivisiUser);
        etNip = findViewById(R.id.etNipUser);
        etPassword = findViewById(R.id.etPasswordUser);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnTambahUser = findViewById(R.id.btnTambahUser);
        spinnerRole = findViewById(R.id.spinnerRole);

        final Context context = this;

        // Ambil array role dari strings.xml
        String[] roleArray = getResources().getStringArray(R.array.role_array);

        // Buat adapter untuk spinner
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleArray);
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Atur adapter pada spinnerRole
        spinnerRole.setAdapter(roleAdapter);

        btnTambahUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString().trim();
                String divisi = etDivisi.getText().toString().trim();
                String nip = etNip.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String role = String.valueOf(spinnerRole.getSelectedItemPosition() + 1);

                // Validasi semua kolom terisi
                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(divisi) ||
                        TextUtils.isEmpty(nip) || TextUtils.isEmpty(password) ||
                        TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(TambahUser.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(TambahUser.this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    TambahUserTask task = new TambahUserTask();
                    task.execute(nama, divisi, nip, password, role);
                }
            }
        });
    }

    private class TambahUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String nama = params[0];
            String divisi = params[1];
            String nip = params[2];
            String password = params[3];
            String role = params[4];

            try {
                // Membuat koneksi HTTP
                URL url = new URL(Urls.TAMBAH_USR_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Mengirim data ke server
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8") +
                        "&" + URLEncoder.encode("divisi", "UTF-8") + "=" + URLEncoder.encode(divisi, "UTF-8") +
                        "&" + URLEncoder.encode("nip", "UTF-8") + "=" + URLEncoder.encode(nip, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
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
                Toast.makeText(TambahUser.this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TambahUser.this, "Tambah Gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
