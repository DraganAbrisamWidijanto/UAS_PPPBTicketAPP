package com.example.utsdragan

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TLAdapter2(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val page = arrayOf(AdmincrudDestinasiFragment(), AdmincrudPlusadminFragment())

    override fun getItemCount(): Int {
        return page.size
    }
    override fun createFragment(position: Int): Fragment {
        return page[position]
    }

    fun getFragment(position: Int): Fragment {
        return page[position]
    }
}