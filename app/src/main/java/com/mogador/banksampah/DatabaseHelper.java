package com.mogador.banksampah;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_sampah_231011400285.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "setoran";

    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama_anggota";
    private static final String COL_JENIS = "jenis_sampah";
    private static final String COL_BERAT = "berat";
    private static final String COL_SALDO = "saldo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAMA + " TEXT NOT NULL, "
                + COL_JENIS + " TEXT NOT NULL, "
                + COL_BERAT + " REAL NOT NULL, "
                + COL_SALDO + " REAL NOT NULL)";
        db.execSQL(createTable);
        insertSeedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertSeedData(SQLiteDatabase db) {
        insertRow(db, "Muhammad Iqbal Ramadhan", "Plastik", 5.5, 16500);
        insertRow(db, "Ahmad Fauzi", "Kertas", 3.2, 4800);
        insertRow(db, "Siti Nurhaliza", "Botol Kaca", 2.0, 6000);
        insertRow(db, "Budi Santoso", "Logam", 4.8, 19200);
        insertRow(db, "Dewi Lestari", "Organik", 7.5, 7500);
        insertRow(db, "Rizky Pratama", "Plastik", 6.0, 18000);
        insertRow(db, "Anisa Rahma", "Kertas", 2.8, 4200);
        insertRow(db, "Hendra Wijaya", "Kaca", 1.5, 4500);
    }

    private void insertRow(SQLiteDatabase db, String nama, String jenis, double berat, double saldo) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAMA, nama);
        cv.put(COL_JENIS, jenis);
        cv.put(COL_BERAT, berat);
        cv.put(COL_SALDO, saldo);
        db.insert(TABLE_NAME, null, cv);
    }

    public long insertSetoran(Setoran setoran) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_NAMA, setoran.getNamaAnggota());
        cv.put(COL_JENIS, setoran.getJenisSampah());
        cv.put(COL_BERAT, setoran.getBerat());
        cv.put(COL_SALDO, setoran.getSaldo());
        long id = db.insert(TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public List<Setoran> getAllSetoran(String sortOption) {
        List<Setoran> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String orderBy;
        if (sortOption == null) orderBy = COL_ID + " DESC";
        else switch (sortOption) {
            case "nama_asc": orderBy = COL_NAMA + " ASC"; break;
            case "nama_desc": orderBy = COL_NAMA + " DESC"; break;
            case "berat_asc": orderBy = COL_BERAT + " ASC"; break;
            case "berat_desc": orderBy = COL_BERAT + " DESC"; break;
            case "saldo_asc": orderBy = COL_SALDO + " ASC"; break;
            case "saldo_desc": orderBy = COL_SALDO + " DESC"; break;
            default: orderBy = COL_ID + " DESC";
        }

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, orderBy);
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
        Cursor cursor = db.query(TABLE_NAME, null, COL_ID + "=?",
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
        cv.put(COL_NAMA, setoran.getNamaAnggota());
        cv.put(COL_JENIS, setoran.getJenisSampah());
        cv.put(COL_BERAT, setoran.getBerat());
        cv.put(COL_SALDO, setoran.getSaldo());
        int rows = db.update(TABLE_NAME, cv, COL_ID + "=?",
                new String[]{String.valueOf(setoran.getId())});
        db.close();
        return rows;
    }

    public int deleteSetoran(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NAME, COL_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    public List<Setoran> searchSetoran(String query) {
        List<Setoran> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COL_NAMA + " LIKE ?", new String[]{"%" + query + "%"},
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
        return new Setoran(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_JENIS)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COL_BERAT)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SALDO))
        );
    }
}
