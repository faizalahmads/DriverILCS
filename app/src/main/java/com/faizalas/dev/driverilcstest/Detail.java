package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Detail extends AppCompatActivity {

    TextView tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Mendapatkan tanggal saat ini
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        String currentDate = sdf.format(new Date());

        // Menggunakan tanggal dalam format Bahasa Indonesia
        TextView textView = findViewById(R.id.tanggal);
        textView.setText(currentDate);
    }
}