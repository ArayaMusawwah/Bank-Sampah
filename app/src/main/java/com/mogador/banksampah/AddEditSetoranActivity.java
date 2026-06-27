package com.mogador.banksampah;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddEditSetoranActivity extends AppCompatActivity {

    public static final String EXTRA_SETORAN_ID = "extra_setoran_id";

    private TextInputLayout tilBerat, tilSaldo, tilAnggota;
    private TextInputEditText etBerat, etSaldo;
    private MaterialAutoCompleteTextView etAnggota;
    private Spinner spinnerJenis;
    private MaterialButton btnSimpan, btnBatal;

    private DatabaseHelper dbHelper;
    private int setoranId = -1;
    private boolean isEditMode = false;

    private List<Anggota> anggotaList = new ArrayList<>();
    private Anggota selectedAnggota = null;
    private ArrayAdapter<String> anggotaAdapter;
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

        tilBerat = findViewById(R.id.tilBerat);
        tilSaldo = findViewById(R.id.tilSaldo);
        tilAnggota = findViewById(R.id.tilAnggota);
        etBerat = findViewById(R.id.etBerat);
        etSaldo = findViewById(R.id.etSaldo);
        etAnggota = findViewById(R.id.etAnggota);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        loadAnggotaDropdown();

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

    private void loadAnggotaDropdown() {
        anggotaList = dbHelper.getAllAnggota(null);
        Collections.sort(anggotaList, Comparator.comparing(Anggota::getNama));

        List<String> namaAnggota = new ArrayList<>();
        for (Anggota a : anggotaList) {
            namaAnggota.add(a.getNama());
        }

        anggotaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, namaAnggota);
        etAnggota.setAdapter(anggotaAdapter);

        etAnggota.setOnItemClickListener((parent, view, position, id) -> {
            String selected = anggotaAdapter.getItem(position);
            for (Anggota a : anggotaList) {
                if (a.getNama().equals(selected)) {
                    selectedAnggota = a;
                    break;
                }
            }
        });

        etAnggota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selectedAnggota = null;
                String typed = s.toString().trim();
                for (Anggota a : anggotaList) {
                    if (a.getNama().equalsIgnoreCase(typed)) {
                        selectedAnggota = a;
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        etAnggota.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                etAnggota.showDropDown();
            }
        });

        etAnggota.setOnClickListener(v -> etAnggota.showDropDown());
    }

    private void loadSetoranData() {
        Setoran setoran = dbHelper.getSetoranById(setoranId);
        if (setoran != null) {
            for (int i = 0; i < anggotaList.size(); i++) {
                if (anggotaList.get(i).getId() == setoran.getAnggotaId()) {
                    etAnggota.setText(anggotaList.get(i).getNama(), false);
                    selectedAnggota = anggotaList.get(i);
                    break;
                }
            }
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
        tilBerat.setError(null);
        tilSaldo.setError(null);
        tilAnggota.setError(null);

        String anggotaText = etAnggota.getText().toString().trim();
        int jenisPos = spinnerJenis.getSelectedItemPosition();
        String beratStr = etBerat.getText().toString().trim();
        String saldoStr = etSaldo.getText().toString().trim();

        boolean valid = true;

        if (selectedAnggota == null || anggotaText.isEmpty()) {
            tilAnggota.setError(getString(R.string.error_nama));
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

        String nama = selectedAnggota.getNama();
        int anggotaId = selectedAnggota.getId();
        String jenis = jenisOptions[jenisPos];

        if (isEditMode) {
            Setoran setoran = new Setoran(setoranId, anggotaId, nama, jenis, berat, saldo);
            dbHelper.updateSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_update), Toast.LENGTH_SHORT).show();
        } else {
            Setoran setoran = new Setoran(anggotaId, nama, jenis, berat, saldo);
            dbHelper.insertSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }
}
