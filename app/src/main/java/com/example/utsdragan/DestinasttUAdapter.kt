package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.utsdragan.databinding.ActivityFavoritChecklistViewHolderBinding

// Kelas DestinasttUAdapter adalah adapter khusus untuk RecyclerView pada aplikasi FavoriteChecklist
class DestinasttUAdapter(private val destinasiList: List<Favorite>, // List destinasi yang akan ditampilkan
                         private val isDeletable: Boolean = false, // Parameter opsional, menandakan apakah item dapat dihapus
                         private val onItemClick: (Favorite) -> Unit): // Parameter berupa fungsi yang menangani klik pada item
            RecyclerView.Adapter<DestinasttUAdapter.DestinasiViewHolder>() {

    // Kelas DestinasiViewHolder sebagai ViewHolder untuk setiap item destinasi
    inner class DestinasiViewHolder(
        private val binding: ActivityFavoritChecklistViewHolderBinding)
                            : RecyclerView.ViewHolder(binding.root){

        // Fungsi bind digunakan untuk mengisi data destinasi pada tampilan ViewHolder
        fun bind(destinasi: Favorite) {
            with(binding) {
                // Menetapkan teks nama destinasi pada TextView
                textViewDestinasi.text = destinasi.nama

                // Menangani aksi ketika hati (heartfavorite) diklik
                heartfavorite.setOnClickListener {
                    // Memeriksa status isChecked pada objek destinasi
                    if(destinasi.isChecked) {
                        // Jika sudah dichecked, set background ke non-checked dan ubah status isChecked
                        heartfavorite.setBackgroundResource(R.drawable.baseline_favoritedisable_border_24)
                        destinasi.isChecked = false
                    } else {
                        // Jika belum dichecked, set background ke checked dan ubah status isChecked
                        heartfavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
                        destinasi.isChecked = true
                    }
                    // Memanggil fungsi onItemClick untuk menangani klik pada item
                    onItemClick(destinasi)
                }
                // Memeriksa apakah item dapat dihapus (isDeletable)
                if(isDeletable) {
                    // Menangani aksi ketika item di-tahan-klik (long click)
                    root.setOnLongClickListener {
                        // Memanggil fungsi onItemClick untuk menangani klik pada item
                        onItemClick(destinasi)
                        true
                    }
                }
            }
        }
    }

    // Metode onCreateViewHolder untuk membuat instance DestinasiViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinasiViewHolder {
        // Membuat instance dari ViewDataBinding berdasarkan layout XML
        val binding = ActivityFavoritChecklistViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DestinasiViewHolder(binding)
    }

    // Metode getItemCount untuk mendapatkan jumlah total item dalam RecyclerView
    override fun getItemCount(): Int {
        return destinasiList.size
    }

    // Metode onBindViewHolder untuk menghubungkan data dengan ViewHolder pada posisi tertentu
    override fun onBindViewHolder(holder: DestinasiViewHolder, position: Int) {
        // Memanggil fungsi bind pada ViewHolder untuk mengisi data destinasi
        holder.bind(destinasiList[position])
    }
}