package com.example.utsdragan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

//kode dari uts


// Deklarasi kelas Dashboard yang merupakan turunan dari AppCompatActivity
class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menetapkan tata letak (layout) activity dengan menggunakan file XML activity_dashboard
        setContentView(R.layout.activity_dashboard)

    }
}
