package com.faizalas.dev.driverilcstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

    public class KndAdapterUser extends BaseAdapter {
        private Context context;
        private List<DataKendaraanUser> items;
        private LayoutInflater inflater;

        public KndAdapterUser(Context context, List<DataKendaraanUser> items) {
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
            TextView status;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            KndAdapterUser.ViewHolder viewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listkendaraanuser, parent, false);

                viewHolder = new KndAdapterUser.ViewHolder();
                viewHolder.id = convertView.findViewById(R.id.id);
                viewHolder.nama_driver = convertView.findViewById(R.id.nama_driver);
                viewHolder.nomor_plat = convertView.findViewById(R.id.nomor_plat);
                viewHolder.role = convertView.findViewById(R.id.role);
                viewHolder.status = convertView.findViewById(R.id.status);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (KndAdapterUser.ViewHolder) convertView.getTag();
            }

            DataKendaraanUser data = items.get(position);

            viewHolder.id.setText(data.getId());
            viewHolder.nama_driver.setText(data.getNama_driver());
            viewHolder.nomor_plat.setText(data.getNomor_plat());
            viewHolder.role.setText(getRoleText(data.getRole()));
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
                    return "Konsultan";
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
