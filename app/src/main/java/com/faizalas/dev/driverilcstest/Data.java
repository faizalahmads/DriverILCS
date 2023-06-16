package com.faizalas.dev.driverilcstest;

public class Data {
    private String id, nama, divisi, role;
    public Data(String id, String nama, String divisi, String role){
        this.id = id;
        this.nama = nama;
        this.divisi = divisi;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
