package com.mogador.banksampah;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEditAnggotaActivity extends AppCompatActivity {

    public static final String EXTRA_ANGGOTA_ID = "extra_anggota_id";

    private TextInputLayout tilNama;
    private TextInputEditText etNama, etAlamat, etEmail, etTelepon;
    private MaterialButton btnSimpan, btnBatal;

    private DatabaseHelper dbHelper;
    private int anggotaId = -1;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_anggota);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);

        tilNama = findViewById(R.id.tilNama);
        etNama = findViewById(R.id.etNama);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        etTelepon = findViewById(R.id.etTelepon);
        btnSimpan = findViewById(R.id.btnSimpanAnggota);
        btnBatal = findViewById(R.id.btnBatalAnggota);

        anggotaId = getIntent().getIntExtra(EXTRA_ANGGOTA_ID, -1);

        if (anggotaId != -1) {
            isEditMode = true;
            toolbar.setTitle(R.string.edit_anggota_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.edit_anggota_title);
            loadAnggotaData();
        } else {
            toolbar.setTitle(R.string.add_anggota_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.add_anggota_title);
        }

        btnSimpan.setOnClickListener(v -> saveData());
        btnBatal.setOnClickListener(v -> finish());
    }

    private void loadAnggotaData() {
        Anggota anggota = dbHelper.getAnggotaById(anggotaId);
        if (anggota != null) {
            etNama.setText(anggota.getNama());
            etAlamat.setText(anggota.getAlamat());
            etEmail.setText(anggota.getEmail());
            etTelepon.setText(anggota.getTelepon());
        }
    }

    private void saveData() {
        tilNama.setError(null);

        String nama = etNama.getText().toString().trim();
        String alamat = etAlamat.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String telepon = etTelepon.getText().toString().trim();

        boolean valid = true;

        if (nama.isEmpty()) {
            tilNama.setError(getString(R.string.error_nama_anggota));
            valid = false;
        }

        if (!valid) return;

        String tanggalDaftar = isEditMode ? "" :
                new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());

        if (isEditMode) {
            Anggota existing = dbHelper.getAnggotaById(anggotaId);
            if (existing != null) tanggalDaftar = existing.getTanggalDaftar();

            Anggota anggota = new Anggota(anggotaId, nama, alamat, email, telepon, tanggalDaftar, 0);
            dbHelper.updateAnggota(anggota);
            Toast.makeText(this, getString(R.string.success_update_anggota), Toast.LENGTH_SHORT).show();
        } else {
            Anggota anggota = new Anggota(nama, alamat, email, telepon, tanggalDaftar, 0);
            dbHelper.insertAnggota(anggota);
            Toast.makeText(this, getString(R.string.success_add_anggota), Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }
}
