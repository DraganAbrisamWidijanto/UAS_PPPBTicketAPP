package com.example.utsdragan

import android.content.Context
import android.content.SharedPreferences

// Kelas untuk mengelola SharedPreferences.
class SharePreferences private constructor(context: Context) {
    // Objek SharedPreferences untuk menyimpan data sesi pengguna.
    private val sharePreferences: SharedPreferences
    companion object {
        // Nama file Preferences
        private const val PREF_FILE_NAME = "session"
        // Kunci untuk menyimpan status login hingga NIM pengguna.
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val IS_ADMIN = "is_admin"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val EMAIL = "email"
        private const val NIM = "nim"


        @Volatile
        private var instance: SharePreferences? = null

        // Mendapatkan instans dari SharePreferences.
        fun getInstance(context: Context): SharePreferences {
            // Jika instance belum ada, lakukan inisialisasi secara thread-safe.
            return instance ?: synchronized(this) {
                // Double-checking untuk memastikan instance belum ada sebelumnya.
                instance ?: SharePreferences(context.applicationContext).also { instance = it }
            }
        }
    }
    // Inisialisasi SharePreferences dengan nama file dan mode akses.
    init {
        sharePreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    // Menyimpan status login dan status admin ke SharedPreferences.
    fun setLoggedIn(isLoggedIn: Boolean, isAdmin: Boolean) {
        // Mendapatkan editor untuk melakukan perubahan pada SharedPreferences.
        val editor = sharePreferences.edit()
        // Menyimpan status login dan status admin.
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putBoolean(IS_ADMIN, isAdmin)
        // Melakukan apply agar perubahan disimpan secara asynchronous.
        editor.apply()
    }

    // Memeriksa apakah pengguna sudah login.
    fun isLoggedIn(): Boolean {
        return sharePreferences.getBoolean(IS_LOGGED_IN, false)
    }

    // Memeriksa apakah pengguna memiliki status admin.
    fun isAdmin(): Boolean {
        return sharePreferences.getBoolean(IS_ADMIN, false)
    }

    // Mengeset status admin pengguna ke true.
    fun setAdmin() {
        val editor = sharePreferences.edit()
        editor.putBoolean(IS_ADMIN, true)
        editor.apply()
    }

    // Menyimpan username ke SharedPreferences.
    fun saveUsername(username: String) {
        val editor = sharePreferences.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    // Mendapatkan username dari SharedPreferences.
    fun getUsername(): String {
        return sharePreferences.getString(USERNAME, "")!!
    }

    // Menyimpan password ke SharedPreferences.
    fun savePassword(password: String) {
        val editor = sharePreferences.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    // Mendapatkan password dari SharedPreferences.
    fun getPassword(): String {
        return sharePreferences.getString(PASSWORD, "")!!
    }

    // Menyimpan NIM (Nomor Induk Mahasiswa) ke SharedPreferences.
    fun saveNim(nim: String) {
        val editor = sharePreferences.edit()
        editor.putString(NIM, nim)
        editor.apply()
    }

    // Mendapatkan NIM (Nomor Induk Mahasiswa) dari SharedPreferences.
    fun getNim(): String {
        return sharePreferences.getString(NIM, "")!!
    }

    // Menyimpan email ke SharedPreferences.
    fun saveEmail(email: String) {
        val editor = sharePreferences.edit()
        editor.putString(EMAIL, email)
        editor.apply()
    }

    // Mendapatkan email dari SharedPreferences.
    fun getEmail(): String {
        return sharePreferences.getString(EMAIL, "")!!
    }

    // Menghapus semua data di SharedPreferences.
    fun clear() {
        val editor = sharePreferences.edit()
        editor.clear()
        editor.apply()
    }
}