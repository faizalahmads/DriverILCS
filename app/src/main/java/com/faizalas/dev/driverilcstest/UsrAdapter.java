package com.faizalas.dev.driverilcstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UsrAdapter extends BaseAdapter {
    private Context context;
    private List<Data> items;
    private LayoutInflater inflater;

    public UsrAdapter(Context context, List<Data> items) {
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
        TextView number;
        TextView id;
        TextView nama;
        TextView divisi;
        TextView role;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.number = convertView.findViewById(R.id.number);
            viewHolder.id = convertView.findViewById(R.id.id);
            viewHolder.nama = convertView.findViewById(R.id.nama);
            viewHolder.divisi = convertView.findViewById(R.id.divisi);
            viewHolder.role = convertView.findViewById(R.id.role);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Data data = items.get(position);

        viewHolder.number.setText(String.valueOf(position + 1));
        viewHolder.id.setText(data.getId());
        viewHolder.nama.setText(data.getNama());
        viewHolder.divisi.setText(data.getDivisi());
        viewHolder.role.setText(data.getRole());

        return convertView;
    }
}

