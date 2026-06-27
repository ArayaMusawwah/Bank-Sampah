package com.mogador.banksampah;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class AnggotaFragment extends Fragment implements AnggotaAdapter.OnItemClickListener {

    private RecyclerView rvAnggota;
    private LinearLayout emptyStateAnggota;
    private EditText etSearchAnggota;
    private Spinner spinnerSortAnggota;
    private AnggotaAdapter adapter;
    private DatabaseHelper dbHelper;
    private String currentSort = null;
    private String currentQuery = "";

    private final ActivityResultLauncher<Intent> addEditLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> loadData());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anggota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());

        rvAnggota = view.findViewById(R.id.rvAnggota);
        emptyStateAnggota = view.findViewById(R.id.emptyStateAnggota);
        etSearchAnggota = view.findViewById(R.id.etSearchAnggota);
        spinnerSortAnggota = view.findViewById(R.id.spinnerSortAnggota);
        ExtendedFloatingActionButton fabAdd = view.findViewById(R.id.fabAddAnggota);

        adapter = new AnggotaAdapter(this);
        rvAnggota.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvAnggota.setAdapter(adapter);

        setupSortSpinner();
        setupSearch();
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddEditAnggotaActivity.class);
            addEditLauncher.launch(intent);
        });

        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dbHelper != null) loadData();
    }

    private void setupSortSpinner() {
        String[] sortLabels = {
                getString(R.string.sort_newest),
                getString(R.string.sort_nama_asc),
                getString(R.string.sort_nama_desc),
                getString(R.string.sort_saldo_asc),
                getString(R.string.sort_saldo_desc)
        };
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, sortLabels);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortAnggota.setAdapter(sortAdapter);

        spinnerSortAnggota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] sortValues = {"terbaru", "nama_asc", "nama_desc", "saldo_asc", "saldo_desc"};
                currentSort = sortValues[position];
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        etSearchAnggota.addTextChangedListener(new TextWatcher() {
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
        List<Anggota> list;
        if (currentQuery.isEmpty()) {
            list = dbHelper.getAllAnggota(currentSort);
        } else {
            list = dbHelper.searchAnggota(currentQuery);
        }
        adapter.setData(list);

        boolean isEmpty = list.isEmpty();
        emptyStateAnggota.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvAnggota.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onEditClick(Anggota anggota) {
        Intent intent = new Intent(requireContext(), AddEditAnggotaActivity.class);
        intent.putExtra(AddEditAnggotaActivity.EXTRA_ANGGOTA_ID, anggota.getId());
        addEditLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(Anggota anggota) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_anggota_message)
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    dbHelper.deleteAnggota(anggota.getId());
                    loadData();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
    }
}
