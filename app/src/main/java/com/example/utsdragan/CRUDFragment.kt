package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.utsdragan.databinding.FragmentCrudBinding
import com.google.android.material.tabs.TabLayoutMediator


class CRUDFragment : Fragment() {

    private lateinit var binding: FragmentCrudBinding
    private lateinit var tabAdapter: TLAdapter2
    private lateinit var viewpager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabAdapter = TLAdapter2(requireActivity())
        with(binding) {
            viewPager2.adapter = tabAdapter
            viewpager2 = viewPager2
            TabLayoutMediator(scadi, viewPager2) { tab, position ->
                tab.text = when(position) {
                    1 -> "CRUD Admin"
                    0 -> "CRUD Destinasi"
                    else -> "Undefined"
                }
            }.attach()
        }
    }

}