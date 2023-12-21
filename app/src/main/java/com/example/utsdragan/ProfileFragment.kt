package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.utsdragan.databinding.FragmentProfileBinding

/**
 * Fragment untuk menampilkan profil pengguna.
 */
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginisialisasi dan mengatur tata letak untuk fragmen profil
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Mendapatkan instance dari MainActivity dan preferences yang terkait
        val mainActivity = MainActivity.getInstance()
        val sharePreferences = mainActivity.getSharedPreferences()

        // Mengisi tampilan profil dengan informasi pengguna yang disimpan di SharedPreferences
        with(binding) {
            profileFragmentName.text = sharePreferences.getUsername()
            profileFragmentEmail.text = sharePreferences.getEmail()
            profileFragmentNIM.text = sharePreferences.getNim()

            // Menambahkan aksi klik pada tombol logout
            btnLogout.setOnClickListener {
                // Menghapus semua data SharedPreferences dan menutup aktivitas saat logout
                sharePreferences.clear()
                activity?.finish()
            }
        }
    }
}