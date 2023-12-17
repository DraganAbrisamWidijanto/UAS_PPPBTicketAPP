package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityDestinationViewHolderBinding


class DestinasiAdapter (private val destinasiList: List<Destinasi>, private val isAdmin: Boolean = false,
                        private val onClick: (Destinasi) -> Unit = {}, private val onLongClick: (Destinasi) -> Unit = {})
    : RecyclerView.Adapter<DestinasiAdapter.DestinasiViewHolder>() {
    inner class DestinasiViewHolder(private val binding: ActivityDestinationViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(destinasi: Destinasi) {
            with(binding) {
                textViewDestinasi.text = destinasi.nama
                if(isAdmin) {
                    root.setOnLongClickListener {
                        onLongClick(destinasi)
                        true
                    }
                    root.setOnClickListener {
                        onClick(destinasi)
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinasiViewHolder {
        val binding = ActivityDestinationViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DestinasiViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return destinasiList.size
    }

    override fun onBindViewHolder(holder: DestinasiViewHolder, position: Int) {
        holder.bind(destinasiList[position])
    }
}