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

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.ViewHolder> {

    private List<Anggota> list = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Anggota anggota);
        void onDeleteClick(Anggota anggota);
    }

    public AnggotaAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Anggota> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anggota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anggota a = list.get(position);
        holder.tvNama.setText(a.getNama());
        holder.tvAlamat.setText(a.getAlamat() != null ? a.getAlamat() : "—");
        holder.tvTelepon.setText(a.getTelepon() != null ? a.getTelepon() : "—");
        holder.tvSaldo.setText(String.format(Locale.US, "Rp %,.0f", a.getSaldo()));

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(a));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(a));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvAlamat, tvTelepon, tvSaldo;
        ImageButton btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaAnggota);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            tvTelepon = itemView.findViewById(R.id.tvTelepon);
            tvSaldo = itemView.findViewById(R.id.tvSaldoAnggota);
            btnEdit = itemView.findViewById(R.id.btnEditAnggota);
            btnDelete = itemView.findViewById(R.id.btnDeleteAnggota);
        }
    }
}
