package com.mogador.banksampah;

public class Anggota {
    private int id;
    private String nama;
    private String alamat;
    private String email;
    private String telepon;
    private String tanggalDaftar;
    private double saldo;

    public Anggota() {}

    public Anggota(int id, String nama, String alamat, String email,
                   String telepon, String tanggalDaftar, double saldo) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.telepon = telepon;
        this.tanggalDaftar = tanggalDaftar;
        this.saldo = saldo;
    }

    public Anggota(String nama, String alamat, String email,
                   String telepon, String tanggalDaftar, double saldo) {
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
        this.telepon = telepon;
        this.tanggalDaftar = tanggalDaftar;
        this.saldo = saldo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }

    public String getTanggalDaftar() { return tanggalDaftar; }
    public void setTanggalDaftar(String tanggalDaftar) { this.tanggalDaftar = tanggalDaftar; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}
