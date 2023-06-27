package com.faizalas.dev.driverilcstest;

public class DataDetail {
    private String id, jam, nama, titik_awal, titik_akhir, jumlah_penumpang, nama_driver;
    public DataDetail(String id,String jam, String nama, String titik_awal, String titik_akhir, String jumlah_penumpang, String nama_driver) {
        this.id = id;
        this.jam = jam;
        this.nama = nama;
        this.titik_awal = titik_awal;
        this.titik_akhir = titik_akhir;
        this.jumlah_penumpang = jumlah_penumpang;
        this.nama_driver = nama_driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTitik_awal() {
        return titik_awal;
    }

    public void setTitik_awal(String titik_awal) {
        this.titik_awal = titik_awal;
    }

    public String getTitik_akhir() {
        return titik_akhir;
    }

    public void setTitik_akhir(String titik_akhir) {
        this.titik_akhir = titik_akhir;
    }

    public String getJumlah_penumpang() {
        return jumlah_penumpang;
    }

    public void setJumlah_penumpang(String jumlah_penumpang) {
        this.jumlah_penumpang = jumlah_penumpang;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }
}
