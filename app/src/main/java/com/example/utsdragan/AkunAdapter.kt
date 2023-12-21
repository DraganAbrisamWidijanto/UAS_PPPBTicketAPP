package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityDataadminViewHolderBinding

class AkunAdapter(
    private val akunList: List<Akun>,
    private val onClick : (Akun) -> Unit,
    private val onLongClick : (Akun) -> Unit
): RecyclerView.Adapter<AkunAdapter.AkunViewHolder>() {

    // ViewHolder untuk menangani tampilan setiap item dalam RecyclerView
    inner class AkunViewHolder(private val binding: ActivityDataadminViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        // Metode ini mengikat data Akun ke tampilan
        fun bind(akun: Akun) {
            with(binding) {
                // Menetapkan teks nama, email, dan NIM
                name.text = akun.username
                email.text = akun.email
                NIM.text = akun.nim
                // Menangani aksi ketika item di-klik-tahan (onLongClick)
                root.setOnLongClickListener {
                    onLongClick(akun)
                    true
                }
                // Menangani aksi ketika item diklik (onClick)
                root.setOnClickListener {
                    onClick(akun)
                }
            }
        }
    }

    // Metode ini dipanggil saat ViewHolder baru diperlukan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkunViewHolder {
        val binding = ActivityDataadminViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AkunViewHolder(binding)
    }

    // Metode ini mengembalikan jumlah item dalam RecyclerView
    override fun getItemCount(): Int {
        return akunList.size
    }

    // Metode ini dipanggil untuk menetapkan data ke ViewHolder
    override fun onBindViewHolder(holder: AkunViewHolder, position: Int) {
        holder.bind(akunList[position])
    }
}