package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.utsdragan.databinding.ActivityFavoritChecklistViewHolderBinding

class DestinasttUAdapter(private val destinasiList: List<Favorite>, private val isDeletable: Boolean = false, private val onItemClick: (Favorite) -> Unit): RecyclerView.Adapter<DestinasttUAdapter.DestinasiViewHolder>() {

    inner class DestinasiViewHolder(private val binding: ActivityFavoritChecklistViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(destinasi: Favorite) {
            with(binding) {
                textViewDestinasi.text = destinasi.nama

                heartfavorite.setOnClickListener {
                    if(destinasi.isChecked) {
                        heartfavorite.setBackgroundResource(R.drawable.baseline_favoritedisable_border_24)
                        destinasi.isChecked = false
                    } else {
                        heartfavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
                        destinasi.isChecked = true
                    }
                    onItemClick(destinasi)
                }
                if(isDeletable) {
                    root.setOnLongClickListener {
                        onItemClick(destinasi)
                        true
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinasiViewHolder {
        val binding = ActivityFavoritChecklistViewHolderBinding.inflate(
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