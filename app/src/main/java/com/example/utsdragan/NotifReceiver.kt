package com.example.utsdragan

import android.content.BroadcastReceiver
import android.content.Intent
import android.widget.Toast

class NotifReceiver: BroadcastReceiver() {

    override fun onReceive(p0: android.content.Context?, p1: android.content.Intent?) {
        val msg = p1?.getStringExtra("message")
        Toast.makeText(p0, msg, Toast.LENGTH_LONG).show()
    }
}