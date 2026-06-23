package com.mogador.banksampah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetoranAdapter extends RecyclerView.Adapter<SetoranAdapter.ViewHolder> {

    private List<Setoran> list = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Setoran setoran);
        void onDeleteClick(Setoran setoran);
    }

    public SetoranAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Setoran> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setoran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setoran s = list.get(position);
        holder.tvNama.setText(s.getNamaAnggota());
        holder.chipJenis.setText(s.getJenisSampah());
        holder.tvBerat.setText(String.format(Locale.US, "%.1f kg", s.getBerat()));
        holder.tvSaldo.setText(String.format(Locale.US, "Rp %,.0f", s.getSaldo()));

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(s));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(s));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvBerat, tvSaldo, chipJenis;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaAnggota);
            chipJenis = itemView.findViewById(R.id.chipJenis);
            tvBerat = itemView.findViewById(R.id.tvBerat);
            tvSaldo = itemView.findViewById(R.id.tvSaldo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
