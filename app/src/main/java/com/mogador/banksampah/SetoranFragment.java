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
import android.widget.TextView;

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

public class SetoranFragment extends Fragment implements SetoranAdapter.OnItemClickListener {

    private RecyclerView rvSetoran;
    private TextView tvEmpty;
    private LinearLayout emptyStateContainer;
    private EditText etSearch;
    private Spinner spinnerSort;
    private SetoranAdapter adapter;
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
        return inflater.inflate(R.layout.fragment_setoran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());

        rvSetoran = view.findViewById(R.id.rvSetoran);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        emptyStateContainer = view.findViewById(R.id.emptyStateContainer);
        etSearch = view.findViewById(R.id.etSearch);
        spinnerSort = view.findViewById(R.id.spinnerSort);
        ExtendedFloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);

        adapter = new SetoranAdapter(this);
        rvSetoran.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSetoran.setAdapter(adapter);

        setupSortSpinner();
        setupSearch();
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddEditSetoranActivity.class);
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
                getString(R.string.sort_berat_asc),
                getString(R.string.sort_berat_desc),
                getString(R.string.sort_saldo_asc),
                getString(R.string.sort_saldo_desc)
        };
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(requireContext(),
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
    }

    @Override
    public void onEditClick(Setoran setoran) {
        Intent intent = new Intent(requireContext(), AddEditSetoranActivity.class);
        intent.putExtra(AddEditSetoranActivity.EXTRA_SETORAN_ID, setoran.getId());
        addEditLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(Setoran setoran) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_message)
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    dbHelper.deleteSetoran(setoran.getId());
                    loadData();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
    }
}
