package com.example.utsdragan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.utsdragan.databinding.ActivityContainerLoginBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContainerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContainerLoginBinding
    private lateinit var tlAdapter: TLAdapter
    private lateinit var viewPager2: ViewPager2

    companion object {
        private var instance: ContainerLoginActivity? = null
        // Fungsi untuk mendapatkan instance dari ContainerLoginActivity
        fun getInstance(): ContainerLoginActivity {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menginisialisasi tata letak dengan menggunakan View Binding
        binding = ActivityContainerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menginisialisasi TLAdapter untuk ViewPager2
        tlAdapter = TLAdapter(this)

        // Menghubungkan TLAdapter dengan ViewPager2
        with(binding) {
            viewpager.adapter = tlAdapter
            viewPager2 = viewpager
            // Menyesuaikan TabLayout dengan ViewPager2
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                tab.text = when(position) {
                    1 -> "Register"
                    0 -> "Login"
                    else -> "Undefined"
                }
            }.attach()
        }

        // Mendapatkan data dari intent (jika ada)
        val intentdata = intent.extras?.get("number")

        if (intentdata == 1) {
            viewPager2.setCurrentItem(1, true)
        } else {
            viewPager2.setCurrentItem(0, true)
        }

        // Menetapkan tampilan awal berdasarkan data intent
        instance = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // Fungsi untuk perpindahan antar fragment dalam ViewPager2
    fun goToFragment(item: Int) {
        viewPager2.setCurrentItem(item, true)
    }

}