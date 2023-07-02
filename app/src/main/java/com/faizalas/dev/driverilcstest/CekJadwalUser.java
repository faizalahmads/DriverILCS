package com.faizalas.dev.driverilcstest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CekJadwalUser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listViewKendaraan;
    SwipeRefreshLayout swipe;
    List<DataKendaraanUser> itemList = new ArrayList<DataKendaraanUser>();
    KndAdapterUser adapter;
    Button BtnDetail, BtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_jadwal_user);

        BtnDetail = findViewById(R.id.btnDetail);
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

        adapter = new KndAdapterUser(CekJadwalUser.this, itemList);
        listViewKendaraan.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        BtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailUser.class);
                startActivity(intent);
            }
        });

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley();
            }
        });
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
                                String status = obj.getString("status");

                                if (role.equals("3")) {
                                    DataKendaraanUser item = new DataKendaraanUser(id, nama_driver, nomor_plat, role, status);
                                    itemList.add(item);
                                }
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
        Intent intent = new Intent(CekJadwalUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
