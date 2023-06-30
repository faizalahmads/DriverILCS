package com.faizalas.dev.driverilcstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DtlAdapter extends BaseAdapter {
    private Context context;
    private List<DataDetail> items;
    private LayoutInflater inflater;

    public DtlAdapter(Context context, List<DataDetail> items) {
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
        TextView jam;
        TextView nama;
        TextView titik_awal;
        TextView titik_akhir;
        TextView jumlah_penumpang;
        TextView nama_driver;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listdetail, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.id = convertView.findViewById(R.id.id);
            viewHolder.jam = convertView.findViewById(R.id.jam);
            viewHolder.nama = convertView.findViewById(R.id.nama);
            viewHolder.titik_awal = convertView.findViewById(R.id.titik_awal);
            viewHolder.titik_akhir = convertView.findViewById(R.id.titik_akhir);
            viewHolder.jumlah_penumpang = convertView.findViewById(R.id.jumlah_penumpang);
            viewHolder.nama_driver = convertView.findViewById(R.id.nama_driver);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DataDetail data = items.get(position);

        viewHolder.id.setText(data.getId());
        viewHolder.jam.setText(data.getJam());
        viewHolder.nama.setText(data.getNama());
        viewHolder.titik_awal.setText(data.getTitik_awal());
        viewHolder.titik_akhir.setText(data.getTitik_akhir());
        viewHolder.jumlah_penumpang.setText(data.getJumlah_penumpang());

        String namaDriver = "";
        String namaDriverValue = data.getNama_driver();
        if (namaDriverValue.equals("1")) {
            namaDriver = "Wawan";
        } else if (namaDriverValue.equals("2")) {
            namaDriver = "Rizal";
        } else if (namaDriverValue.equals("3")) {
            namaDriver = "Endra";
        }
        viewHolder.nama_driver.setText(namaDriver);

        return convertView;
    }

    public void sortItemsByJam() {
        Collections.sort(items, new Comparator<DataDetail>() {
            @Override
            public int compare(DataDetail item1, DataDetail item2) {
                int jam1 = Integer.parseInt(item1.getJam());
                int jam2 = Integer.parseInt(item2.getJam());
                return Integer.compare(jam1, jam2);
            }
        });
        notifyDataSetChanged();
    }
}

