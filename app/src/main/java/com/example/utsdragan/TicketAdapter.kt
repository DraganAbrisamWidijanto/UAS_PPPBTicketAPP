package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityTicketViewHolderBinding

class TicketAdapter(private val ticketList: List<Ticket>): RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(private val binding: ActivityTicketViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(ticket: Ticket) {
            with(binding) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ActivityTicketViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TicketViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(ticketList[position])
    }
}