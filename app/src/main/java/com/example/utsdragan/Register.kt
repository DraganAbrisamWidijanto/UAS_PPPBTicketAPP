package com.example.utsdragan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import android.widget.TextView

class Register : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var tglLahirEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var daftarButton: Button
    private lateinit var linkLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Menggunakan layout activity_register.xml

        // Inisialisasi elemen-elemen antarmuka pengguna
        emailEditText = findViewById(R.id.ETEMail) // Mendapatkan referensi EditText dari XML
        usernameEditText = findViewById(R.id.ETUsername) // Mendapatkan referensi EditText dari XML
        tglLahirEditText = findViewById(R.id.tanggal) // Mendapatkan referensi EditText dari XML
        passwordEditText = findViewById(R.id.ETPassword) // Mendapatkan referensi EditText dari XML
        daftarButton = findViewById(R.id.btnRegister) // Mendapatkan referensi Button dari XML
        linkLogin = findViewById(R.id.linkLogin) // Mendapatkan referensi TextView dari XML

        // Menambahkan listener klik pada TextView untuk navigasi ke halaman login
        linkLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Menambahkan listener klik pada Button untuk proses pendaftaran
        daftarButton.setOnClickListener {
            // Mendapatkan nilai dari input pengguna
            val email = emailEditText.text.toString()
            val username = usernameEditText.text.toString()
            val tglLahir = tglLahirEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Memastikan semua kolom isian terisi
            if (email.isEmpty() || username.isEmpty() || tglLahir.isEmpty() || password.isEmpty()) {
                showMessage("Semua kolom harus diisi")
                return@setOnClickListener
            }

            // Mengonversi tanggal lahir dari string ke objek Date
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val today = Date()
            val tglLahirDate = dateFormat.parse(tglLahir)

            // Memeriksa apakah format tanggal lahir valid
            if (tglLahirDate == null) {
                showMessage("Format tanggal lahir tidak valid")
                return@setOnClickListener
            }

            // Menghitung usia berdasarkan tanggal lahir
            val age = calculateAge(tglLahirDate, today)

            // Memeriksa apakah usia mencukupi untuk pendaftaran
            if (age < 15) {
                showMessage("Anda harus berusia 15 tahun atau lebih")
            } else {
                showMessage("Pendaftaran berhasil")

                // Menyimpan data pengguna ke SharedPreferences
                val sharedPrefsEditor =
                    getSharedPreferences("user_preferences", Context.MODE_PRIVATE).edit()
                sharedPrefsEditor.putString("username", username)
                sharedPrefsEditor.putString("password", password)
                sharedPrefsEditor.apply()

                // Navigasi ke halaman login setelah pendaftaran berhasil
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }
    }

    // Fungsi untuk menghitung usia berdasarkan tanggal lahir
    private fun calculateAge(birthDate: Date, currentDate: Date): Int {
        val birthYear = SimpleDateFormat("yyyy").format(birthDate).toInt()
        val currentYear = SimpleDateFormat("yyyy").format(currentDate).toInt()
        return currentYear - birthYear
    }

    // Fungsi untuk menampilkan pesan Toast
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
