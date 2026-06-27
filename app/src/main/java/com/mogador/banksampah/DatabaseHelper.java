package com.mogador.banksampah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_banksampah_231011400285.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_SETORAN = "setoran";
    private static final String TABLE_ANGGOTA = "anggota";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSetoranTable());
        db.execSQL(createAnggotaTable());
        insertSeedAnggota(db);
        insertSeedSetoran(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(createAnggotaTable());
            db.execSQL("ALTER TABLE " + TABLE_SETORAN + " ADD COLUMN anggota_id INTEGER DEFAULT NULL");
            insertSeedAnggota(db);
            linkSetoranToAnggota(db);
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANGGOTA);
            db.execSQL(createAnggotaTable());
            insertSeedAnggota(db);
            linkSetoranToAnggota(db);
        }
    }

    private String createSetoranTable() {
        return "CREATE TABLE " + TABLE_SETORAN + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "anggota_id INTEGER DEFAULT NULL, "
                + "nama_anggota TEXT NOT NULL, "
                + "jenis_sampah TEXT NOT NULL, "
                + "berat REAL NOT NULL, "
                + "saldo REAL NOT NULL)";
    }

    private String createAnggotaTable() {
        return "CREATE TABLE " + TABLE_ANGGOTA + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nama TEXT NOT NULL, "
                + "alamat TEXT, "
                + "email TEXT, "
                + "telepon TEXT, "
                + "tanggal_daftar TEXT)";
    }

    // ==================== SEED DATA ====================

    private void insertSeedAnggota(SQLiteDatabase db) {
        insertAnggotaRow(db, "Muhammad Iqbal Ramadhan",
                "Jl. Merdeka No. 10, Malang", "231011400285@unpam.ac.id",
                "081234567890", "2025-01-15");
        insertAnggotaRow(db, "Ridwan Rayap Besi",
                "Jl. Sudirman No. 25, Surabaya", "2132112312@mail.com",
                "082345678901", "2025-02-10");
        insertAnggotaRow(db, "Andika Warteg",
                "Jl. Diponegoro No. 8, Malang", "231333272812@mail.com",
                "083456789012", "2025-03-05");
        insertAnggotaRow(db, "Budi Anduk",
                "Jl. Pemuda No. 42, Jakarta", "294874055@mail.com",
                "084567890123", "2025-04-20");
        insertAnggotaRow(db, "Indah Alam",
                "Jl. Ahmad Yani No. 17, Bandung", "346736363@mail.com",
                "085678901234", "2025-05-12");
    }

    private void insertAnggotaRow(SQLiteDatabase db, String nama, String alamat,
                                   String email, String telepon, String tgl) {
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("alamat", alamat);
        cv.put("email", email);
        cv.put("telepon", telepon);
        cv.put("tanggal_daftar", tgl);
        db.insert(TABLE_ANGGOTA, null, cv);
    }

    private void insertSeedSetoran(SQLiteDatabase db) {
        insertSetoranRow(db, "Muhammad Iqbal Ramadhan", "Plastik", 5.5, 16500);
        insertSetoranRow(db, "Ridwan Rayap Besi", "Kertas", 3.2, 4800);
        insertSetoranRow(db, "Andika Warteg", "Botol Kaca", 2.0, 6000);
        insertSetoranRow(db, "Budi Anduk", "Logam", 4.8, 19200);
        insertSetoranRow(db, "Indah Alam", "Organik", 7.5, 7500);
        insertSetoranRow(db, "Muhammad Iqbal Ramadhan", "Plastik", 6.0, 18000);
        insertSetoranRow(db, "Ridwan Rayap Besi", "Kertas", 2.8, 4200);
        insertSetoranRow(db, "Andika Warteg", "Kaca", 1.5, 4500);
        linkSetoranToAnggota(db);
    }

    private void insertSetoranRow(SQLiteDatabase db, String nama, String jenis,
                                   double berat, double saldo) {
        ContentValues cv = new ContentValues();
        cv.put("nama_anggota", nama);
        cv.put("jenis_sampah", jenis);
        cv.put("berat", berat);
        cv.put("saldo", saldo);
        db.insert(TABLE_SETORAN, null, cv);
    }

    private void linkSetoranToAnggota(SQLiteDatabase db) {
        db.execSQL("UPDATE " + TABLE_SETORAN
                + " SET anggota_id = (SELECT id FROM " + TABLE_ANGGOTA
                + " WHERE " + TABLE_ANGGOTA + ".nama = " + TABLE_SETORAN + ".nama_anggota)"
                + " WHERE anggota_id IS NULL");
    }

    // ==================== SETORAN CRUD ====================

    public long insertSetoran(Setoran setoran) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if (setoran.getAnggotaId() > 0) cv.put("anggota_id", setoran.getAnggotaId());
        cv.put("nama_anggota", setoran.getNamaAnggota());
        cv.put("jenis_sampah", setoran.getJenisSampah());
        cv.put("berat", setoran.getBerat());
        cv.put("saldo", setoran.getSaldo());
        long id = db.insert(TABLE_SETORAN, null, cv);
        db.close();
        return id;
    }

    public List<Setoran> getAllSetoran(String sortOption) {
        List<Setoran> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        if (sortOption == null) orderBy = "id DESC";
        else switch (sortOption) {
            case "nama_asc": orderBy = "nama_anggota ASC"; break;
            case "nama_desc": orderBy = "nama_anggota DESC"; break;
            case "berat_asc": orderBy = "berat ASC"; break;
            case "berat_desc": orderBy = "berat DESC"; break;
            case "saldo_asc": orderBy = "saldo ASC"; break;
            case "saldo_desc": orderBy = "saldo DESC"; break;
            default: orderBy = "id DESC";
        }

        Cursor cursor = db.query(TABLE_SETORAN, null, null, null, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToSetoran(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<Setoran> getRecentSetoran(int limit) {
        List<Setoran> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETORAN, null, null, null, null, null,
                "id DESC", String.valueOf(limit));
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToSetoran(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Setoran getSetoranById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETORAN, null, "id=?",
                new String[]{String.valueOf(id)}, null, null, null);
        Setoran setoran = null;
        if (cursor.moveToFirst()) {
            setoran = cursorToSetoran(cursor);
        }
        cursor.close();
        db.close();
        return setoran;
    }

    public int updateSetoran(Setoran setoran) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("anggota_id", setoran.getAnggotaId() > 0 ? setoran.getAnggotaId() : null);
        cv.put("nama_anggota", setoran.getNamaAnggota());
        cv.put("jenis_sampah", setoran.getJenisSampah());
        cv.put("berat", setoran.getBerat());
        cv.put("saldo", setoran.getSaldo());
        int rows = db.update(TABLE_SETORAN, cv, "id=?",
                new String[]{String.valueOf(setoran.getId())});
        db.close();
        return rows;
    }

    public int deleteSetoran(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_SETORAN, "id=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<Setoran> searchSetoran(String query) {
        List<Setoran> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SETORAN, null,
                "nama_anggota LIKE ?", new String[]{"%" + query + "%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToSetoran(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private Setoran cursorToSetoran(Cursor cursor) {
        int anggotaIdIdx = cursor.getColumnIndex("anggota_id");
        int anggotaId = (anggotaIdIdx >= 0 && !cursor.isNull(anggotaIdIdx))
                ? cursor.getInt(anggotaIdIdx) : -1;
        return new Setoran(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                anggotaId,
                cursor.getString(cursor.getColumnIndexOrThrow("nama_anggota")),
                cursor.getString(cursor.getColumnIndexOrThrow("jenis_sampah")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("berat")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"))
        );
    }

    // ==================== ANGGOTA CRUD ====================

    private static final String ANGGOTA_SELECT_WITH_SALDO =
            "SELECT a.id, a.nama, a.alamat, a.email, a.telepon, a.tanggal_daftar, "
                    + "COALESCE(SUM(s.saldo), 0) as saldo "
                    + "FROM " + TABLE_ANGGOTA + " a "
                    + "LEFT JOIN " + TABLE_SETORAN + " s ON a.id = s.anggota_id "
                    + "GROUP BY a.id";

    public long insertAnggota(Anggota anggota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", anggota.getNama());
        cv.put("alamat", anggota.getAlamat());
        cv.put("email", anggota.getEmail());
        cv.put("telepon", anggota.getTelepon());
        cv.put("tanggal_daftar", anggota.getTanggalDaftar());
        long id = db.insert(TABLE_ANGGOTA, null, cv);
        db.close();
        return id;
    }

    public List<Anggota> getAllAnggota(String sortOption) {
        List<Anggota> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        if (sortOption == null) orderBy = "a.id DESC";
        else switch (sortOption) {
            case "nama_asc": orderBy = "a.nama ASC"; break;
            case "nama_desc": orderBy = "a.nama DESC"; break;
            case "saldo_asc": orderBy = "saldo ASC"; break;
            case "saldo_desc": orderBy = "saldo DESC"; break;
            case "terbaru": orderBy = "a.id DESC"; break;
            default: orderBy = "a.id DESC";
        }

        Cursor cursor = db.rawQuery(ANGGOTA_SELECT_WITH_SALDO + " ORDER BY " + orderBy, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToAnggota(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Anggota getAnggotaById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT a.id, a.nama, a.alamat, a.email, a.telepon, a.tanggal_daftar, "
                        + "COALESCE(SUM(s.saldo), 0) as saldo "
                        + "FROM " + TABLE_ANGGOTA + " a "
                        + "LEFT JOIN " + TABLE_SETORAN + " s ON a.id = s.anggota_id "
                        + "WHERE a.id=? "
                        + "GROUP BY a.id",
                new String[]{String.valueOf(id)});
        Anggota anggota = null;
        if (cursor.moveToFirst()) {
            anggota = cursorToAnggota(cursor);
        }
        cursor.close();
        db.close();
        return anggota;
    }

    public int updateAnggota(Anggota anggota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", anggota.getNama());
        cv.put("alamat", anggota.getAlamat());
        cv.put("email", anggota.getEmail());
        cv.put("telepon", anggota.getTelepon());
        cv.put("tanggal_daftar", anggota.getTanggalDaftar());
        int rows = db.update(TABLE_ANGGOTA, cv, "id=?",
                new String[]{String.valueOf(anggota.getId())});
        db.close();
        return rows;
    }

    public int deleteAnggota(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_ANGGOTA, "id=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<Anggota> searchAnggota(String query) {
        List<Anggota> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT a.id, a.nama, a.alamat, a.email, a.telepon, a.tanggal_daftar, "
                        + "COALESCE(SUM(s.saldo), 0) as saldo "
                        + "FROM " + TABLE_ANGGOTA + " a "
                        + "LEFT JOIN " + TABLE_SETORAN + " s ON a.id = s.anggota_id "
                        + "WHERE a.nama LIKE ? "
                        + "GROUP BY a.id",
                new String[]{"%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(cursorToAnggota(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    private Anggota cursorToAnggota(Cursor cursor) {
        return new Anggota(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                cursor.getString(cursor.getColumnIndexOrThrow("alamat")),
                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                cursor.getString(cursor.getColumnIndexOrThrow("telepon")),
                cursor.getString(cursor.getColumnIndexOrThrow("tanggal_daftar")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"))
        );
    }

    // ==================== DASHBOARD STATS ====================

    public int getTotalAnggotaCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ANGGOTA, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public int getTotalSetoranCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_SETORAN, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public double getTotalBerat() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COALESCE(SUM(berat), 0) FROM " + TABLE_SETORAN, null);
        double total = 0;
        if (cursor.moveToFirst()) total = cursor.getDouble(0);
        cursor.close();
        db.close();
        return total;
    }

    public double getTotalSaldo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COALESCE(SUM(saldo), 0) FROM " + TABLE_SETORAN, null);
        double total = 0;
        if (cursor.moveToFirst()) total = cursor.getDouble(0);
        cursor.close();
        db.close();
        return total;
    }

    public List<String[]> getBeratByJenis() {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT jenis_sampah, SUM(berat) as total_berat FROM " + TABLE_SETORAN
                        + " GROUP BY jenis_sampah ORDER BY total_berat DESC", null);
        if (cursor.moveToFirst()) {
            do {
                String[] item = new String[]{
                        cursor.getString(0),
                        String.valueOf(cursor.getDouble(1))
                };
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
