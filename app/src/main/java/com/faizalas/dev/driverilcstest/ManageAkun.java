package com.faizalas.dev.driverilcstest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.faizalas.dev.driverilcstest.R;
import com.faizalas.dev.driverilcstest.Data;
import com.faizalas.dev.driverilcstest.UsrAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageAkun extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    UsrAdapter adapter;
    LayoutInflater inflater;
    TextView tvid, tvnama, tvdivisi, tvnip;
    EditText etpassword;
    String vid, vnama, vdivisi, vnip, vpassword;
    Button btnLogout, btnTambahUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_akun);
        btnTambahUser = findViewById(R.id.btnTambahUser);
        swipe = findViewById(R.id.swipe);
        list = findViewById(R.id.list);

        btnTambahUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TambahUser.class);
                startActivity(i);
            }
        });

        adapter = new UsrAdapter(ManageAkun.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String idx = itemList.get(position).getId();
                final CharSequence [] pilihanAksi = {"Hapus", "Edit"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManageAkun.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                hapusData(idx);
                                break;

                            case 1:
                                ubahData(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    public void ubahData(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_USR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String namax = jObj.getString("nama");
                            String divisix = jObj.getString("divisi");
                            String nipx = jObj.getString("nip");
                            String passwordx = jObj.getString("password");

                            dialogForm(idx,namax,divisix,nipx,passwordx, "UPDATE");

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageAkun.this,"Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void dialogForm(String id, String nama, String divisi, String nip, String password, String button){
        AlertDialog.Builder dialogForm = new AlertDialog.Builder(ManageAkun.this);
        inflater = getLayoutInflater();
        View viewDialog =  inflater.inflate(R.layout.form_edit_user, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("EDIT USER");


        tvid = viewDialog.findViewById(R.id.tvIdEdit);
        tvnama = viewDialog.findViewById(R.id.tvNamaEdit);
        tvdivisi = viewDialog.findViewById(R.id.tvDivisiEdit);
        tvnip = viewDialog.findViewById(R.id.tvNipEdit);
        etpassword = viewDialog.findViewById(R.id.etPasswordEdit);

        if (id.isEmpty()){
            tvid.setText(null);
            tvnama.setText(null);
            tvdivisi.setText(null);
            tvnip.setText(null);
            etpassword.setText(null);
        } else {
            tvid.setText(id);
            tvnama.setText(nama);
            tvdivisi.setText(divisi);
            tvnip.setText(nip);
            etpassword.setText(null);
        }

        dialogForm.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vid = tvid.getText().toString();
                vnama = tvnama.getText().toString();
                vdivisi = tvdivisi.getText().toString();
                vnip = tvnip.getText().toString();
                vpassword = etpassword.getText().toString();

                simpan();
                dialog.dismiss();
            }
        });
        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tvid.setText(null);
                tvnama.setText(null);
                tvdivisi.setText(null);
                tvnip.setText(null);
                etpassword.setText(null);
            }
        });
        dialogForm.show();
    }

    public void simpan(){
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, Urls.INSERT_USR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(ManageAkun.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageAkun.this,"Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                if (vid.isEmpty()){
                    params.put("nama", vnama);
                    params.put("divisi", vdivisi);
                    params.put("nip", vnip);
                    params.put("password", vpassword);
                    return params;
                } else {
                    params.put("id", vid);
                    params.put("nama", vnama);
                    params.put("divisi", vdivisi);
                    params.put("nip", vnip);
                    params.put("password", vpassword);
                    return params;
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    public void hapusData(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_USR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ManageAkun.this, response, Toast.LENGTH_SHORT).show();
                        callVolley();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageAkun.this,"Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // Request to fetch data from select.php
        JsonArrayRequest jArr = new JsonArrayRequest(Request.Method.GET, Urls.TAMPILAN_USR_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Inside the onResponse method of JsonArrayRequest
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String id = obj.getString("id");
                                String nama = obj.getString("nama");
                                String divisi = obj.getString("divisi");
                                int role = obj.getInt("role");

                                String roleString;
                                switch (role) {
                                    case 1:
                                        roleString = "Admin";
                                        break;
                                    case 2:
                                        roleString = "Karyawan";
                                        break;
                                    default:
                                        roleString = "Unknown";
                                        break;
                                }

                                Data item = new Data(id, nama, divisi, roleString);
                                itemList.add(item);
                            }

                            adapter.notifyDataSetChanged();
                            swipe.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        swipe.setRefreshing(false);
                    }
                });

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);
    }

}