package com.faizalas.dev.driverilcstest;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailDriver extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ListView listViewDetail;
    SwipeRefreshLayout swipe;
    List<DataDetail> itemList = new ArrayList<DataDetail>();
    DtlAdapter adapter;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_driver);

        swipe = findViewById(R.id.swipe);
        listViewDetail = findViewById(R.id.listdetail);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm", new Locale("id", "ID"));

        String currentDate = sdfDate.format(new Date());
        String currentTime = sdfTime.format(new Date());

        TextView tanggalTextView = findViewById(R.id.tanggal);
        TextView jamTextView = findViewById(R.id.jam);

        tanggalTextView.setText(currentDate);
        jamTextView.setText(currentTime);



        adapter = new DtlAdapter(DetailDriver.this, itemList);
        adapter.sortItemsByJam();
        listViewDetail.setAdapter(adapter);

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
        listViewDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String idx = itemList.get(position).getId();
                final CharSequence [] pilihAksi = {"Selesai"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailDriver.this);
                dialog.setItems(pilihAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                selesaiData(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    public void selesaiData(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailDriver.this);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Konfirmasi");
        dialog.setMessage("Apakah Anda yakin ingin selesaikan pesanan ini?");

        dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                prosesSelesaiData(id);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void prosesSelesaiData(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.SELESAI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DetailDriver.this, response, Toast.LENGTH_SHORT).show();
                        callVolley();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailDriver.this, "Gagal Koneksi ke Server, Silahkan Cek Koneksi Anda", Toast.LENGTH_SHORT).show();
                    }
                }) {
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
        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(Request.Method.GET, Urls.TAMPILAN_DTL_USR_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String id = obj.getString("id");
                                String jam = obj.getString("jam");
                                String nama = obj.getString("nama");
                                String titik_awal = obj.getString("titik_awal");
                                String titik_akhir = obj.getString("titik_akhir");
                                String jumlah_penumpang = obj.getString("jumlah_penumpang");
                                String nama_driver = obj.getString("nama_driver");

                                DataDetail item = new DataDetail(id, jam, nama, titik_awal, titik_akhir, jumlah_penumpang, nama_driver);
                                itemList.add(item);
                            }

                            Collections.sort(itemList, new Comparator<DataDetail>() {
                                @Override
                                public int compare(DataDetail item1, DataDetail item2) {
                                    String jam1 = item1.getJam();
                                    String jam2 = item2.getJam();
                                    return jam1.compareTo(jam2);
                                }
                            });

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