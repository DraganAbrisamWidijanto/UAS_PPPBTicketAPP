package com.example.utsdragan

import android.content.BroadcastReceiver
import android.content.Intent
import android.widget.Toast

/**
 * Kelas penerima siaran (broadcast) yang menangani penerimaan notifikasi.
 */
class NotifReceiver: BroadcastReceiver() {

    override fun onReceive(p0: android.content.Context?, p1: android.content.Intent?) {
        // Mendapatkan pesan notifikasi dari intent
        val msg = p1?.getStringExtra("message")
        // Menampilkan pesan notifikasi menggunakan Toast
        Toast.makeText(p0, msg, Toast.LENGTH_LONG).show()
    }
}