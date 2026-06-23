package com.mogador.banksampah;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class AddEditSetoranActivity extends AppCompatActivity {

    public static final String EXTRA_SETORAN_ID = "extra_setoran_id";

    private TextInputLayout tilNama, tilBerat, tilSaldo;
    private TextInputEditText etNama, etBerat, etSaldo;
    private Spinner spinnerJenis;
    private MaterialButton btnSimpan, btnBatal;

    private DatabaseHelper dbHelper;
    private int setoranId = -1;
    private boolean isEditMode = false;

    private final String[] jenisOptions = {
            "Pilih jenis sampah", "Plastik", "Kertas", "Botol Kaca", "Logam", "Organik"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_setoran);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);

        tilNama = findViewById(R.id.tilNama);
        tilBerat = findViewById(R.id.tilBerat);
        tilSaldo = findViewById(R.id.tilSaldo);
        etNama = findViewById(R.id.etNama);
        etBerat = findViewById(R.id.etBerat);
        etSaldo = findViewById(R.id.etSaldo);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        ArrayAdapter<String> jenisAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jenisOptions);
        jenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(jenisAdapter);

        setoranId = getIntent().getIntExtra(EXTRA_SETORAN_ID, -1);

        if (setoranId != -1) {
            isEditMode = true;
            toolbar.setTitle(R.string.edit_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.edit_title);
            loadSetoranData();
        } else {
            toolbar.setTitle(R.string.add_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.add_title);
        }

        btnSimpan.setOnClickListener(v -> saveData());
        btnBatal.setOnClickListener(v -> finish());
    }

    private void loadSetoranData() {
        Setoran setoran = dbHelper.getSetoranById(setoranId);
        if (setoran != null) {
            etNama.setText(setoran.getNamaAnggota());
            for (int i = 1; i < jenisOptions.length; i++) {
                if (jenisOptions[i].equals(setoran.getJenisSampah())) {
                    spinnerJenis.setSelection(i);
                    break;
                }
            }
            etBerat.setText(String.valueOf(setoran.getBerat()));
            etSaldo.setText(String.valueOf(setoran.getSaldo()));
        }
    }

    private void saveData() {
        tilNama.setError(null);
        tilBerat.setError(null);
        tilSaldo.setError(null);

        String nama = etNama.getText().toString().trim();
        int jenisPos = spinnerJenis.getSelectedItemPosition();
        String beratStr = etBerat.getText().toString().trim();
        String saldoStr = etSaldo.getText().toString().trim();

        boolean valid = true;

        if (nama.isEmpty()) {
            tilNama.setError(getString(R.string.error_nama));
            valid = false;
        }
        if (jenisPos == 0) {
            Toast.makeText(this, getString(R.string.error_jenis), Toast.LENGTH_SHORT).show();
            valid = false;
        }

        double berat = 0;
        double saldo = 0;
        try {
            if (beratStr.isEmpty()) {
                tilBerat.setError(getString(R.string.error_berat));
                valid = false;
            } else {
                berat = Double.parseDouble(beratStr);
                if (berat <= 0) {
                    tilBerat.setError(getString(R.string.error_berat));
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            tilBerat.setError(getString(R.string.error_berat));
            valid = false;
        }
        try {
            if (saldoStr.isEmpty()) {
                tilSaldo.setError(getString(R.string.error_saldo));
                valid = false;
            } else {
                saldo = Double.parseDouble(saldoStr);
                if (saldo <= 0) {
                    tilSaldo.setError(getString(R.string.error_saldo));
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            tilSaldo.setError(getString(R.string.error_saldo));
            valid = false;
        }

        if (!valid) return;
        String jenis = jenisOptions[jenisPos];

        if (isEditMode) {
            Setoran setoran = new Setoran(setoranId, nama, jenis, berat, saldo);
            dbHelper.updateSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_update), Toast.LENGTH_SHORT).show();
        } else {
            Setoran setoran = new Setoran(nama, jenis, berat, saldo);
            dbHelper.insertSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }
}
