package com.mogador.banksampah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SetoranAdapter.OnItemClickListener {

    private static final String PREFS_NAME = "bank_sampah_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private RecyclerView rvSetoran;
    private TextView tvEmpty;
    private LinearLayout emptyStateContainer;
    private EditText etSearch;
    private Spinner spinnerSort;
    private TextView tvStatAnggota, tvStatBerat, tvStatSaldo;
    private SetoranAdapter adapter;
    private DatabaseHelper dbHelper;
    private String currentSort = null;
    private String currentQuery = "";

    private final ActivityResultLauncher<Intent> addEditLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> loadData());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logout();
                return true;
            }
            return false;
        });
        toolbar.inflateMenu(R.menu.menu_main);

        rvSetoran = findViewById(R.id.rvSetoran);
        tvEmpty = findViewById(R.id.tvEmpty);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        etSearch = findViewById(R.id.etSearch);
        spinnerSort = findViewById(R.id.spinnerSort);
        tvStatAnggota = findViewById(R.id.tvStatAnggota);
        tvStatBerat = findViewById(R.id.tvStatBerat);
        tvStatSaldo = findViewById(R.id.tvStatSaldo);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        adapter = new SetoranAdapter(this);
        rvSetoran.setLayoutManager(new LinearLayoutManager(this));
        rvSetoran.setAdapter(adapter);

        setupSortSpinner();
        setupSearch();
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditSetoranActivity.class);
            addEditLauncher.launch(intent);
        });

        loadData();
    }

    private void setupSortSpinner() {
        String[] sortLabels = {
                getString(R.string.sort_newest),
                getString(R.string.sort_nama_asc),
                getString(R.string.sort_nama_desc),
                getString(R.string.sort_berat_asc),
                getString(R.string.sort_berat_desc),
                getString(R.string.sort_saldo_asc),
                getString(R.string.sort_saldo_desc)
        };
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sortLabels);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] sortValues = {null, "nama_asc", "nama_desc",
                        "berat_asc", "berat_desc", "saldo_asc", "saldo_desc"};
                currentSort = sortValues[position];
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString().trim();
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        List<Setoran> list;
        if (currentQuery.isEmpty()) {
            list = dbHelper.getAllSetoran(currentSort);
        } else {
            list = dbHelper.searchSetoran(currentQuery);
        }
        adapter.setData(list);

        boolean isEmpty = list.isEmpty();
        emptyStateContainer.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvSetoran.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

        updateStats(list);
    }

    private void updateStats(List<Setoran> list) {
        int totalAnggota = list.size();
        double totalBerat = 0;
        double totalSaldo = 0;

        for (Setoran s : list) {
            totalBerat += s.getBerat();
            totalSaldo += s.getSaldo();
        }

        tvStatAnggota.setText(String.valueOf(totalAnggota));
        tvStatBerat.setText(String.format(Locale.US, "%.1f kg", totalBerat));
        tvStatSaldo.setText(String.format(Locale.US, "Rp %,.0f", totalSaldo));
    }

    @Override
    public void onEditClick(Setoran setoran) {
        Intent intent = new Intent(this, AddEditSetoranActivity.class);
        intent.putExtra(AddEditSetoranActivity.EXTRA_SETORAN_ID, setoran.getId());
        addEditLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(Setoran setoran) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_message)
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    dbHelper.deleteSetoran(setoran.getId());
                    loadData();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.btn_logout)
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
    }
}
