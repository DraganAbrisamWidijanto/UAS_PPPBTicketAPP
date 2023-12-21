package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityTicketViewHolderBinding

// Adapter untuk RecyclerView yang menangani tampilan item tiket.
class TicketAdapter(private val ticketList: List<Ticket>): RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
    // ViewHolder berisi referensi ke tampilan masing-masing item tiket.
    inner class TicketViewHolder(private val binding: ActivityTicketViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        // Metode untuk mengikat data tiket ke tampilan item.
        fun bind(ticket: Ticket) {
            with(binding) {
                // Mengatur nilai ID tiket ke TextView 'id'.
                id.text = ticket.id.toString()
                trainName.text = ticket.nama
                from.text = ticket.asal
                to.text = ticket.tujuan
                date.text = ticket.tanggal
                remillia.text = ticket.jam
                price.text = ticket.harga.toString()
                berapabanyak.text = ticket.pesananTambahan.toString()
            }
        }
    }

    // Metode untuk membuat ViewHolder baru ketika RecyclerView memerlukan.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        // Menginflate tata letak item tiket menggunakan ViewDataBinding.
        val binding = ActivityTicketViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // Mengembalikan instance dari TicketViewHolder yang dibuat.
        return TicketViewHolder(binding)
    }

    // Metode untuk mendapatkan jumlah total item dalam dataset.
    override fun getItemCount(): Int {
        return ticketList.size
    }

    // Metode untuk mengikat data dari dataset ke tampilan item pada posisi tertentu.
    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(ticketList[position])
    }
}