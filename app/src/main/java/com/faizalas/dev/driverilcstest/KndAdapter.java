package com.faizalas.dev.driverilcstest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class KndAdapter extends BaseAdapter {
    private Context context;
    private List<DataKendaraan> items;
    private LayoutInflater inflater;

    public KndAdapter(Context context, List<DataKendaraan> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView id;
        TextView nama_driver;
        TextView nomor_plat;
        TextView role;
        TextView jadwal_service;
        TextView status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KndAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listkendaraan, parent, false);

            viewHolder = new KndAdapter.ViewHolder();
            viewHolder.id = convertView.findViewById(R.id.id);
            viewHolder.nama_driver = convertView.findViewById(R.id.nama_driver);
            viewHolder.nomor_plat = convertView.findViewById(R.id.nomor_plat);
            viewHolder.role = convertView.findViewById(R.id.role);
            viewHolder.jadwal_service = convertView.findViewById(R.id.jadwal_service);
            viewHolder.status = convertView.findViewById(R.id.status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (KndAdapter.ViewHolder) convertView.getTag();
        }

        DataKendaraan data = items.get(position);

        viewHolder.id.setText(data.getId());
        viewHolder.nama_driver.setText(data.getNama_driver());
        viewHolder.nomor_plat.setText(data.getNomor_plat());
        viewHolder.role.setText(getRoleText(data.getRole()));
        viewHolder.jadwal_service.setText(data.getJadwal_service());
        viewHolder.status.setText(getStatusText(data.getStatus()));

        if (data.getStatus().equals("4")) {
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.unavailable_text_color));
        } else {
            viewHolder.status.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        return convertView;
    }


    private String getRoleText(String role) {
        switch (role) {
            case "1":
                return "Direksi";
            case "2":
                return "Komisaris";
            case "3":
                return "Karyawan";
            default:
                return "";
        }
    }

    private String getStatusText(String status){
        switch (status){
            case "1":
                return "On Witel";
            case "2":
                return "On Peltow";
            case "3":
                return "On The Way";
            case "4":
                return "Unavailable";
            default:
                return "";
        }
    }
}
