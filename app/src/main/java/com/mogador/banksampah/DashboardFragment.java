package com.mogador.banksampah;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private TextView tvDashTotalAnggota, tvDashTotalSetoran, tvDashTotalBerat, tvDashTotalSaldo;
    private LinearLayout containerJenisBreakdown, containerRecentSetoran;
    private TextView tvEmptyRecent;
    private DatabaseHelper dbHelper;

    private static final int[] BAR_COLORS = {
            Color.parseColor("#1B5E20"),
            Color.parseColor("#2E7D32"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#8D6E63"),
            Color.parseColor("#4E342E")
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());

        tvDashTotalAnggota = view.findViewById(R.id.tvDashTotalAnggota);
        tvDashTotalSetoran = view.findViewById(R.id.tvDashTotalSetoran);
        tvDashTotalBerat = view.findViewById(R.id.tvDashTotalBerat);
        tvDashTotalSaldo = view.findViewById(R.id.tvDashTotalSaldo);
        containerJenisBreakdown = view.findViewById(R.id.containerJenisBreakdown);
        containerRecentSetoran = view.findViewById(R.id.containerRecentSetoran);
        tvEmptyRecent = view.findViewById(R.id.tvEmptyRecent);

        loadDashboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDashboard();
    }

    private void loadDashboard() {
        tvDashTotalAnggota.setText(String.valueOf(dbHelper.getTotalAnggotaCount()));
        tvDashTotalSetoran.setText(String.valueOf(dbHelper.getTotalSetoranCount()));
        tvDashTotalBerat.setText(String.format(Locale.US, "%.1f kg", dbHelper.getTotalBerat()));
        tvDashTotalSaldo.setText(String.format(Locale.US, "Rp %,.0f", dbHelper.getTotalSaldo()));

        loadJenisBreakdown();
        loadRecentSetoran();
    }

    private void loadJenisBreakdown() {
        containerJenisBreakdown.removeAllViews();
        List<String[]> data = dbHelper.getBeratByJenis();

        if (data.isEmpty()) {
            TextView tv = new TextView(requireContext());
            tv.setText(R.string.dashboard_no_data);
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#6C757D"));
            tv.setPadding(0, 16, 0, 16);
            containerJenisBreakdown.addView(tv);
            return;
        }

        double maxBerat = 0;
        for (String[] item : data) {
            double berat = Double.parseDouble(item[1]);
            if (berat > maxBerat) maxBerat = berat;
        }

        for (int i = 0; i < data.size(); i++) {
            String jenis = data.get(i)[0];
            double berat = Double.parseDouble(data.get(i)[1]);
            int color = BAR_COLORS[i % BAR_COLORS.length];

            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            row.setPadding(0, 4, 0, 4);

            TextView tvJenis = new TextView(requireContext());
            tvJenis.setText(jenis);
            tvJenis.setTextSize(13);
            tvJenis.setTextColor(Color.parseColor("#495057"));
            tvJenis.setWidth(dpToPx(100));
            row.addView(tvJenis);

            LinearLayout barContainer = new LinearLayout(requireContext());
            barContainer.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(20), 1));
            barContainer.setPadding(dpToPx(8), 0, 0, 0);
            row.addView(barContainer);

            View bar = new View(requireContext());
            float fraction = maxBerat > 0 ? (float) (berat / maxBerat) : 0;
            LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT);
            barParams.weight = fraction;
            bar.setLayoutParams(barParams);
            bar.setBackgroundColor(color);
            barContainer.addView(bar);

            TextView tvBerat = new TextView(requireContext());
            tvBerat.setText(String.format(Locale.US, "%.1f kg", berat));
            tvBerat.setTextSize(12);
            tvBerat.setTextColor(Color.parseColor("#212529"));
            tvBerat.setTypeface(null, Typeface.BOLD);
            tvBerat.setPadding(dpToPx(8), 0, 0, 0);
            row.addView(tvBerat);

            containerJenisBreakdown.addView(row);
        }
    }

    private void loadRecentSetoran() {
        containerRecentSetoran.removeAllViews();
        List<Setoran> recent = dbHelper.getRecentSetoran(5);

        if (recent.isEmpty()) {
            tvEmptyRecent.setVisibility(View.VISIBLE);
            return;
        }

        tvEmptyRecent.setVisibility(View.GONE);
        for (Setoran s : recent) {
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            row.setPadding(0, 8, 0, 8);

            TextView tvNama = new TextView(requireContext());
            tvNama.setText(s.getNamaAnggota());
            tvNama.setTextSize(14);
            tvNama.setTextColor(Color.parseColor("#212529"));
            tvNama.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            row.addView(tvNama);

            TextView tvBerat = new TextView(requireContext());
            tvBerat.setText(String.format(Locale.US, "%.1f kg", s.getBerat()));
            tvBerat.setTextSize(13);
            tvBerat.setTextColor(Color.parseColor("#6C757D"));
            tvBerat.setPadding(dpToPx(8), 0, 0, 0);
            row.addView(tvBerat);

            TextView tvSaldo = new TextView(requireContext());
            tvSaldo.setText(String.format(Locale.US, "Rp %,.0f", s.getSaldo()));
            tvSaldo.setTextSize(13);
            tvSaldo.setTextColor(Color.parseColor("#2E7D32"));
            tvSaldo.setTypeface(null, Typeface.BOLD);
            tvSaldo.setPadding(dpToPx(12), 0, 0, 0);
            row.addView(tvSaldo);

            containerRecentSetoran.addView(row);

            View divider = new View(requireContext());
            divider.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1));
            divider.setBackgroundColor(Color.parseColor("#E9ECEF"));
            containerRecentSetoran.addView(divider);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}
