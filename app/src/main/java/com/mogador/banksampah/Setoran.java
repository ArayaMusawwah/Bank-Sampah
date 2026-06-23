package com.mogador.banksampah;

public class Setoran {
    private int id;
    private String namaAnggota;
    private String jenisSampah;
    private double berat;
    private double saldo;

    public Setoran() {}

    public Setoran(int id, String namaAnggota, String jenisSampah, double berat, double saldo) {
        this.id = id;
        this.namaAnggota = namaAnggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    public Setoran(String namaAnggota, String jenisSampah, double berat, double saldo) {
        this.namaAnggota = namaAnggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaAnggota() { return namaAnggota; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }

    public String getJenisSampah() { return jenisSampah; }
    public void setJenisSampah(String jenisSampah) { this.jenisSampah = jenisSampah; }

    public double getBerat() { return berat; }
    public void setBerat(double berat) { this.berat = berat; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}
