package com.example.utsdragan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.utsdragan.databinding.ActivityMemberContainerBinding

class MemberActivityContainer : AppCompatActivity() {

    private lateinit var binding: ActivityMemberContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainActivity = MainActivity.getInstance()
        val sharedPreferences = mainActivity.getSharedPreferences()

        with(binding) {
            val navController = findNavController(R.id.nav_host_fragment)
            if(sharedPreferences.isAdmin()) {
                bottonNavigationView.menu.clear()
                bottonNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
            }
            bottonNavigationView.setupWithNavController(navController)

        }
    }
}