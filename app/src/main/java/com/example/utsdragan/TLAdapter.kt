package com.example.utsdragan

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

// Kelas TLAdapter berfungsi sebagai adaptor untuk mengelola fragmen pada tampilan berbasis tab (TabLayout). Untuk login dan register
class TLAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    // Array yang berisi daftar fragmen yang akan ditampilkan pada tampilan berbasis tab.
    private val page = arrayOf(LoginFragment(), RegisterFragment())

    // Metode ini mengembalikan jumlah total item (fragmen) yang akan ditampilkan pada tampilan berbasis tab.
    override fun getItemCount(): Int {
        return page.size
    }

    // Metode ini membuat fragmen pada posisi tertentu.
    override fun createFragment(position: Int): Fragment {
        return page[position]
    }

    // Metode ini digunakan untuk mendapatkan fragmen pada posisi tertentu.
    fun getFragment(position: Int): Fragment {
        return page[position]
    }
}