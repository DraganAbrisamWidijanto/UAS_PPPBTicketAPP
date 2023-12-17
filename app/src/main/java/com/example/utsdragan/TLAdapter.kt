package com.example.utsdragan

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TLAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    private val page = arrayOf(LoginFragment(), RegisterFragment())

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