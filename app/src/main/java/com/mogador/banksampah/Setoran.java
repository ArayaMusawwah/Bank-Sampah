package com.mogador.banksampah;

public class Setoran {
    private int id;
    private int anggotaId;
    private String namaAnggota;
    private String jenisSampah;
    private double berat;
    private double saldo;

    public Setoran() {}

    public Setoran(int id, int anggotaId, String namaAnggota, String jenisSampah,
                   double berat, double saldo) {
        this.id = id;
        this.anggotaId = anggotaId;
        this.namaAnggota = namaAnggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    public Setoran(int anggotaId, String namaAnggota, String jenisSampah,
                   double berat, double saldo) {
        this.anggotaId = anggotaId;
        this.namaAnggota = namaAnggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    public Setoran(String namaAnggota, String jenisSampah, double berat, double saldo) {
        this.anggotaId = -1;
        this.namaAnggota = namaAnggota;
        this.jenisSampah = jenisSampah;
        this.berat = berat;
        this.saldo = saldo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAnggotaId() { return anggotaId; }
    public void setAnggotaId(int anggotaId) { this.anggotaId = anggotaId; }

    public String getNamaAnggota() { return namaAnggota; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }

    public String getJenisSampah() { return jenisSampah; }
    public void setJenisSampah(String jenisSampah) { this.jenisSampah = jenisSampah; }

    public double getBerat() { return berat; }
    public void setBerat(double berat) { this.berat = berat; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}
