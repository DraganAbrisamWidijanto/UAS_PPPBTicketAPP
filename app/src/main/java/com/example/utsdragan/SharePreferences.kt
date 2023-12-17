package com.example.utsdragan

import android.content.Context
import android.content.SharedPreferences

class SharePreferences private constructor(context: Context) {
    private val sharePreferences: SharedPreferences
    companion object {
        private const val PREF_FILE_NAME = "session"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val IS_ADMIN = "is_admin"
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val EMAIL = "email"
        private const val NIM = "nim"


        @Volatile
        private var instance: SharePreferences? = null
        fun getInstance(context: Context): SharePreferences {
            return instance ?: synchronized(this) {
                instance ?: SharePreferences(context.applicationContext).also { instance = it }
            }
        }
    }
    init {
        sharePreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun setLoggedIn(isLoggedIn: Boolean, isAdmin: Boolean) {
        val editor = sharePreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn)
        editor.putBoolean(IS_ADMIN, isAdmin)
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharePreferences.getBoolean(IS_LOGGED_IN, false)
    }
    fun isAdmin(): Boolean {
        return sharePreferences.getBoolean(IS_ADMIN, false)
    }

    fun setAdmin() {
        val editor = sharePreferences.edit()
        editor.putBoolean(IS_ADMIN, true)
        editor.apply()
    }

    fun saveUsername(username: String) {
        val editor = sharePreferences.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String {
        return sharePreferences.getString(USERNAME, "")!!
    }

    fun savePassword(password: String) {
        val editor = sharePreferences.edit()
        editor.putString(PASSWORD, password)
        editor.apply()
    }
    fun getPassword(): String {
        return sharePreferences.getString(PASSWORD, "")!!
    }

    fun saveNim(nim: String) {
        val editor = sharePreferences.edit()
        editor.putString(NIM, nim)
        editor.apply()
    }

    fun getNim(): String {
        return sharePreferences.getString(NIM, "")!!
    }

    fun saveEmail(email: String) {
        val editor = sharePreferences.edit()
        editor.putString(EMAIL, email)
        editor.apply()
    }
    fun getEmail(): String {
        return sharePreferences.getString(EMAIL, "")!!
    }
    fun clear() {
        val editor = sharePreferences.edit()
        editor.clear()
        editor.apply()
    }
}