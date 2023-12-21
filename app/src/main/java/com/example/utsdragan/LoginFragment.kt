package com.example.utsdragan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.utsdragan.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    // Deklarasi variabel untuk view binding
    private lateinit var binding: FragmentLoginBinding
    // Metode untuk membuat tata letak tampilan (inflate) fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menggunakan view binding untuk menginisialisasi binding
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance() // Mendapatkan instance dari MainActivity
        val sharedPreferences = mainActivity.getSharedPreferences() // Mendapatkan instance dari SharedPreferences

        // Menggunakan extension function with untuk mempersingkat penggunaan binding
        with(binding) {
            // Menambahkan event listener pada tombol login
            btnLogin.setOnClickListener {
                val usernameInput = usernamelogin.text.toString()  // Mendapatkan input username dari EditText
                val passwordInput = passwordlogin.text.toString()  // Mendapatkan input password dari EditText

                // Memanggil metode getAkun dari MainActivity dengan parameter username dan password
                mainActivity.getAkun(usernameInput, passwordInput)
            }
        }
    }
}