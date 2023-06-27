package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginDriver extends AppCompatActivity {

    EditText EtNip, EtPassword;
    Button BtnLogin;

    String PHP_URL = "http://192.168.1.16/driverILCS/logindriver.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_driver);

        EtNip = findViewById(R.id.etNip);
        EtPassword = findViewById(R.id.etPassword);
        BtnLogin = findViewById(R.id.btnLogin);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nip = EtNip.getText().toString().trim();
                String password = EtPassword.getText().toString().trim();

                if (!nip.isEmpty() && !password.isEmpty()) {
                    new LoginDriverTask().execute(nip, password);
                } else {
                    Toast.makeText(LoginDriver.this, "Isi Semua Kolom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class LoginDriverTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String nip = params[0];
            String password = params[1];

            try {
                URL url = new URL(PHP_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                String data = URLEncoder.encode("nip", "UTF-8") + "=" + URLEncoder.encode(nip, "UTF-8") +
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
                Toast.makeText(LoginDriver.this, result, Toast.LENGTH_SHORT).show();

                if (result.equals("Login berhasil")) {
                    Intent intent = new Intent(LoginDriver.this, DashboardDriver.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(LoginDriver.this, "Login gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
