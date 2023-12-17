package com.example.utsdragan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utsdragan.databinding.ActivityDataadminViewHolderBinding

class AkunAdapter(private val akunList: List<Akun>, private val onClick : (Akun) -> Unit, private val onLongClick : (Akun) -> Unit): RecyclerView.Adapter<AkunAdapter.AkunViewHolder>() {
    inner class AkunViewHolder(private val binding: ActivityDataadminViewHolderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(akun: Akun) {
            with(binding) {
                name.text = akun.username
                email.text = akun.email
                NIM.text = akun.nim
                root.setOnLongClickListener {
                    onLongClick(akun)
                    true
                }
                root.setOnClickListener {
                    onClick(akun)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AkunViewHolder {
        val binding = ActivityDataadminViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AkunViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return akunList.size
    }

    override fun onBindViewHolder(holder: AkunViewHolder, position: Int) {
        holder.bind(akunList[position])
    }
}