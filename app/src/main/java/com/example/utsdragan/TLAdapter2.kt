package com.example.utsdragan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Kelas TLAdapter2 berfungsi sebagai adaptor untuk mengelola fragmen pada tampilan berbasis tab (TabLayout)
// untuk setelah login ada halaman crud admin dan destinasi.
class TLAdapter2(activity: FragmentActivity): FragmentStateAdapter(activity) {

    // Array yang berisi daftar fragmen yang akan ditampilkan pada tampilan berbasis tab.
    private val page = arrayOf(AdmincrudDestinasiFragment(), AdmincrudPlusadminFragment())

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