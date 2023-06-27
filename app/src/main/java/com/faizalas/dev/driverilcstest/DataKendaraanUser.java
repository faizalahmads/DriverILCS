package com.faizalas.dev.driverilcstest;


public class DataKendaraanUser {
    private String id, nama_driver, nomor_plat, role, status;
    public DataKendaraanUser(String id, String nama_driver, String nomor_plat, String role, String status) {
        this.id = id;
        this.nama_driver = nama_driver;
        this.nomor_plat = nomor_plat;
        this.role = role;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getNomor_plat() {
        return nomor_plat;
    }

    public void setNomor_plat(String nomor_plat) {
        this.nomor_plat = nomor_plat;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
