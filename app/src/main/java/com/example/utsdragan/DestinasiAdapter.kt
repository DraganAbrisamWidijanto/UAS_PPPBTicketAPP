package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityDestinationViewHolderBinding

// Kelas DestinasiAdapter merupakan adapter khusus untuk RecyclerView pada aplikasi Destinasi
class DestinasiAdapter (private val destinasiList: List<Destinasi>, // List destinasi yang akan ditampilkan
                        private val isAdmin: Boolean = false, // Parameter opsional, menandakan apakah pengguna adalah admin
                        private val onClick: (Destinasi) -> Unit = {}, // Parameter opsional, menangani klik pada item destinasi
                        private val onLongClick: (Destinasi) -> Unit = {} // Parameter opsional, menangani tahan klik pada item destinasi
    )    : RecyclerView.Adapter<DestinasiAdapter.DestinasiViewHolder>() {

    // Kelas DestinasiViewHolder sebagai ViewHolder untuk setiap item destinasi
    inner class DestinasiViewHolder(private val binding: ActivityDestinationViewHolderBinding): RecyclerView.ViewHolder(binding.root){

        // Fungsi bind digunakan untuk mengisi data destinasi pada tampilan ViewHolder
        fun bind(destinasi: Destinasi) {
            with(binding) {
                // Menetapkan teks nama destinasi pada TextView
                textViewDestinasi.text = destinasi.nama

                // Memeriksa apakah pengguna adalah admin
                if(isAdmin) {
                    // Menangani aksi ketika item di-tahan-klik (long click)
                    root.setOnLongClickListener {
                        onLongClick(destinasi)
                        true
                    }
                    // Menangani aksi ketika item di-klik (click)
                    root.setOnClickListener {
                        onClick(destinasi)
                    }

                }
            }
        }
    }

    // Metode onCreateViewHolder untuk membuat instance DestinasiViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinasiViewHolder {
        // Membuat instance dari ViewDataBinding berdasarkan layout XML
        val binding = ActivityDestinationViewHolderBinding.inflate(
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