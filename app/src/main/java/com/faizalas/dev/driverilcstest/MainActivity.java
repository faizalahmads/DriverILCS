package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    EditText EtNip, EtPassword;
    TextView regist;
    Button BtnLogin;

    String PHP_URL = "http://10.90.132.69/driverILCS/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regist = findViewById(R.id.regis);

        EtNip = findViewById(R.id.etNip);
        EtPassword = findViewById(R.id.etPassword);
        BtnLogin = findViewById(R.id.btnLogin);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Regist.class);
                startActivity(i);
            }
        });

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nip = EtNip.getText().toString().trim();
                String password = EtPassword.getText().toString().trim();

                LoginUserTask task = new LoginUserTask();
                task.execute(nip, password);
            }
        });
    }

    private class LoginUserTask extends AsyncTask<String, Void, String>{

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
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
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
                String[] splitResult = result.split("\\|");
                String loginMessage = splitResult[0];
                String role = splitResult[1];

                Toast.makeText(MainActivity.this, loginMessage, Toast.LENGTH_SHORT).show();

                if (loginMessage.equals("Login berhasil")) {
                    // Arahkan pengguna ke halaman dashboard yang sesuai berdasarkan role
                    if (role.equals("1")) {
                        // Pengguna dengan role 1 (admin) diarahkan ke DashboardAdminActivity
                        Intent intent = new Intent(MainActivity.this, DashboardAdmin.class);
                        startActivity(intent);
                        finish();
                    } else if (role.equals("2")) {
                        // Pengguna dengan role 2 (driver) diarahkan ke DashboardDriverActivity
                        Intent intent = new Intent(MainActivity.this, DashboardDriver.class);
                        startActivity(intent);
                        finish();
                    } else if (role.equals("3")) {
                        // Pengguna dengan role 3 (karyawan) diarahkan ke DashboardUserActivity
                        Intent intent = new Intent(MainActivity.this, DashboardUser.class);
                        startActivity(intent);
                        finish();
                    }
                }
            } else {
                Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}