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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KendaraanDriver extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listViewKendaraan;
    SwipeRefreshLayout swipe;
    List<DataKendaraan> itemList = new ArrayList<DataKendaraan>();
    KndAdapter adapter;
    LayoutInflater inflater;
    TextView tvnamadriver, tvnoplat;
    EditText etid, etjadwal;
    String vid, vnamadriver, vnoplat, vjadwal;
    Button BtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_driver);

        BtnLogout = findViewById(R.id.btnLogout);

        BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSession();
                navigateToLogin();
            }
        });

        swipe = findViewById(R.id.swipe);
        listViewKendaraan = findViewById(R.id.listkendaraan);

        adapter = new KndAdapter(KendaraanDriver.this, itemList);
        listViewKendaraan.setAdapter(adapter);

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
        listViewKendaraan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String idx = itemList.get(position).getId();
                final CharSequence [] pilihanAksi = {"Ubah Jadwal Service"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(KendaraanDriver.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        switch (which){
                            case 0:
                                editJadwalServiceData(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    public void editJadwalServiceData(String id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_KND_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String idx = jObj.getString("id");
                            String namadriverx = jObj.getString("nama_driver");
                            String noplatx = jObj.getString("nomor_plat");
                            String jadwalx = jObj.getString("jadwal_service");

                            dialogForm(idx,namadriverx, noplatx, jadwalx, "UPDATE");

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KendaraanDriver.this, "Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
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

    public void dialogForm(String id, String nama_driver, String nomor_plat, String jadwal_service, String button){
        AlertDialog.Builder dialogForm = new AlertDialog.Builder(KendaraanDriver.this);
        inflater = getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.form_edit_kendaraan_driver, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("EDIT KENDARAAN");

        etid = viewDialog.findViewById(R.id.etIdEdit);
        tvnamadriver = viewDialog.findViewById(R.id.tvNamaEdit);
        tvnoplat = viewDialog.findViewById(R.id.tvNomorPlat);
        etjadwal = viewDialog.findViewById(R.id.etJadwalService);

        if (id.isEmpty()){
            etid.setText(null);
            tvnamadriver.setText(null);
            tvnoplat.setText(null);
            etjadwal.setText(null);
        } else {
            etid.setText(id);
            tvnamadriver.setText(nama_driver);
            tvnoplat.setText(nomor_plat);
            etjadwal.setText(jadwal_service);
        }

        dialogForm.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vid = etid.getText().toString();
                vnamadriver = tvnamadriver.getText().toString();
                vnoplat = tvnoplat.getText().toString();
                vjadwal = etjadwal.getText().toString();

                simpan();
                dialog.dismiss();
            }
        });
        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                etid.setText(null);
                tvnamadriver.setText(null);
                tvnoplat.setText(null);
                etjadwal.setText(null);
            }
        });
        dialogForm.show();
    }

    public void simpan(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.INSERT_KND_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callVolley();
                        Toast.makeText(KendaraanDriver.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KendaraanDriver.this, "Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                if (vid.isEmpty()){
                    params.put("nama_driver", vnamadriver);
                    params.put("nomor_plat", vnoplat);
                    params.put("jadwal_service", vjadwal);
                    return params;
                } else {
                    params.put("id", vid);
                    params.put("nama_driver", vnamadriver);
                    params.put("nomor_plat", vnoplat);
                    params.put("jadwal_service", vjadwal);
                    return params;
                }
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

        JsonArrayRequest jArr = new JsonArrayRequest(Request.Method.GET, Urls.TAMPILAN_KND_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String id = obj.getString("id");
                                String nama_driver = obj.getString("nama_driver");
                                String nomor_plat = obj.getString("nomor_plat");
                                String role = obj.getString("role");
                                String jadwal_service = obj.getString("jadwal_service");
                                String status = obj.getString("status");

                                DataKendaraan item = new DataKendaraan(id, nama_driver, nomor_plat, role, jadwal_service, status);
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

    private void clearSession() {
    }

    private void navigateToLogin() {
        Intent intent = new Intent(KendaraanDriver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}