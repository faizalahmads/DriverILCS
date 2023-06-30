package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
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

public class Regist extends AppCompatActivity {

    EditText Etnama, Etdiv, Etnip, Etpassword, ETconfirmpass;
    TextView login;
    Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Etnama = findViewById(R.id.etNama);
        Etdiv = findViewById(R.id.etDivisi);
        Etnip = findViewById(R.id.etNip);
        Etpassword = findViewById(R.id.etPassword);
        ETconfirmpass = findViewById(R.id.etConfirmPassword);
        login = findViewById(R.id.teLogin);
        regist = findViewById(R.id.btnRegist);

        final Context context = this;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = Etnama.getText().toString().trim();
                String divisi = Etdiv.getText().toString().trim();
                String nip = Etnip.getText().toString().trim();
                String password = Etpassword.getText().toString().trim();
                String confirmPassword = ETconfirmpass.getText().toString().trim();
                String role = "2";

                if (nama.isEmpty() || divisi.isEmpty() || nip.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Regist.this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Regist.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    RegisterUserTask task = new RegisterUserTask();
                    task.execute(nama, divisi, nip, password, role);
                }
            }
        });
    }

    private class RegisterUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String nama = params[0];
            String divisi = params[1];
            String nip = params[2];
            String password = params[3];
            String role = params[4];

            try {
                URL url = new URL(Urls.REGIST_URL + "?role=" + role);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8") +
                        "&" + URLEncoder.encode("divisi", "UTF-8") + "=" + URLEncoder.encode(divisi, "UTF-8") +
                        "&" + URLEncoder.encode("nip", "UTF-8") + "=" + URLEncoder.encode(nip, "UTF-8") +
                        "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                Toast.makeText(Regist.this, result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Regist.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
