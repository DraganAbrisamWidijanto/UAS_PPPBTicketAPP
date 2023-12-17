package com.example.utsdragan

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Kelas `Login` merupakan aktivitas yang memungkinkan pengguna untuk melakukan login ke aplikasi.
 * Pengguna memasukkan nama pengguna (username) dan kata sandi (password) untuk masuk.
 */
class Login : AppCompatActivity() {
    private lateinit var usernameEditText: EditText // Input field untuk memasukkan nama pengguna
    private lateinit var passwordEditText: EditText // Input field untuk memasukkan kata sandi
    private lateinit var loginButton: Button // Tombol untuk melakukan login
    private lateinit var linkregis: TextView // Teks untuk menuju halaman registrasi

    private lateinit var sharedPreferences: SharedPreferences // Objek untuk menyimpan data pengguna

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.ETUsername) // Menggunakan ID EditText untuk username dari XML Anda
        passwordEditText = findViewById(R.id.ETPassword) // Menggunakan ID EditText untuk password dari XML Anda
        loginButton = findViewById(R.id.btnLogin) // Menggunakan ID Button dari XML Anda
        linkregis = findViewById(R.id.linkregister)

        // Mendengarkan klik pada teks "Daftar" untuk membuka aktivitas pendaftaran
        linkregis.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Mendapatkan objek SharedPreferences untuk menyimpan data pengguna
        sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)

        // Mendengarkan klik pada tombol Login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Dapatkan data pengguna yang telah mendaftar dari SharedPreferences
            val registeredUsername = sharedPreferences.getString("username", null)
            val registeredPassword = sharedPreferences.getString("password", null)

            // Memeriksa apakah nama pengguna dan kata sandi yang dimasukkan cocok dengan data yang terdaftar
            if (username == registeredUsername && password == registeredPassword) {
                showMessage("Login berhasil") // Menampilkan pesan sukses
                // Arahkan pengguna ke halaman beranda atau aktivitas lain yang sesuai
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            } else {
                showMessage("Login gagal. Username atau password salah.") // Menampilkan pesan kesalahan
            }
        }
    }

    /**
     * Fungsi ini menampilkan pesan toast dengan teks yang diberikan.
     * @param message Pesan yang akan ditampilkan dalam toast.
     */
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
