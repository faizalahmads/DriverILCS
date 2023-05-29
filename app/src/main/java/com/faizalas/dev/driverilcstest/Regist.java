package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Regist extends AppCompatActivity {

    EditText Etnama,Etdiv,Etnip,Etpassword;
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
        login = findViewById(R.id.teLogin);
        regist = findViewById(R.id.btnRegist);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = Etnama.getText().toString().trim();
                String divisi = Etdiv.getText().toString().trim();
                String nip = Etnip.getText().toString().trim();
                String password = Etpassword.getText().toString().trim();

                if (nama.isEmpty() || divisi.isEmpty() || nip.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Regist.this, "Silahkan isi data", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(nama, divisi, nip, password);
                }

            }
        });
    }

    private void registerUser(String nama, String divisi, String nip, String password) {
        Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
        finish();
    }
}