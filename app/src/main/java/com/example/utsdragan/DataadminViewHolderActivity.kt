package com.example.utsdragan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// Deklarasi kelas DataadminViewHolderActivity yang merupakan turunan dari AppCompatActivity
class DataadminViewHolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menetapkan tata letak (layout) activity dengan menggunakan
        // file XML activity_dataadmin_view_holder
        setContentView(R.layout.activity_dataadmin_view_holder)
    }
}