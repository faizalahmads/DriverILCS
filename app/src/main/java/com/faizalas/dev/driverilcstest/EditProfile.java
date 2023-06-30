package com.faizalas.dev.driverilcstest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    TextView edituser, etNamaEdit, etDivisiEdit, etNipEdit;
    Button BtnLogout, BtnGantiPassword;
    ProgressDialog progressDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        edituser = findViewById(R.id.edituser);
        BtnLogout = findViewById(R.id.btnLogout);
        etNamaEdit = findViewById(R.id.etNamaEdit);
        etDivisiEdit = findViewById(R.id.etDivisiEdit);
        etNipEdit = findViewById(R.id.etNipEdit);
        BtnGantiPassword = findViewById(R.id.btnGantiPassword);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("nama");
        String divisi = intent.getStringExtra("divisi");
        String nip = intent.getStringExtra("nip");
        userId = intent.getStringExtra("userId");

        etNamaEdit.setText(nama);
        etDivisiEdit.setText(divisi);
        etNipEdit.setText(nip);

        getUserProfile();

        BtnGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View gantipassword = LayoutInflater.from(EditProfile.this).inflate(R.layout.change_password, null);
                EditText oldPass = gantipassword.findViewById(R.id.etOldPassword);
                EditText newPass = gantipassword.findViewById(R.id.etNewPassword);
                EditText confirmPass = gantipassword.findViewById(R.id.etNewConPassword);

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle("Ganti Password");
                builder.setView(gantipassword);
                builder.setPositiveButton("UBAH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String oldPassword = oldPass.getText().toString().trim();
                        String newPassword = newPass.getText().toString().trim();
                        String confirmPassword = confirmPass.getText().toString().trim();

                        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                            showMessage("Kolom tidak boleh kosong");
                        } else {
                            changePasswordRequest(oldPassword, newPassword, confirmPassword);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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

    private void getUserProfile() {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.PROFILE_USR_URL + "?userId=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Log.d("ProfileResponse", response);

                        try {
                            JSONObject profileObject = new JSONObject(response);
                            String nama = profileObject.getString("nama");
                            String divisi = profileObject.getString("divisi");
                            String nip = profileObject.getString("nip");

                            etNamaEdit.setText(nama);
                            etDivisiEdit.setText(divisi);
                            etNipEdit.setText(nip);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showMessage("Error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        showMessage(error.getMessage());
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


    private void changePasswordRequest(String oldPassword, String newPassword, String confirmPassword) {
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GANTIPASS_USR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        showMessage(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        showMessage(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("oldpassword", oldPassword);
                params.put("newpassword", newPassword);
                params.put("conforpassword", confirmPassword);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearSession() {
    }

    private void navigateToLogin() {
        Intent intent = new Intent(EditProfile.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
