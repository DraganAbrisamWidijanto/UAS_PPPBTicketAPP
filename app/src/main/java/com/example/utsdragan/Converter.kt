package com.example.utsdragan

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class Converter {
    // Metode untuk mengonversi String ke List<String>
    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        // Memeriksa apakah nilai input null
        if (value == null) {
            return null
        }

        // Membuat tipe data List<String> menggunakan Gson
        val listType = object : TypeToken<List<String>>() {}.type
        // Menggunakan Gson untuk mengonversi String menjadi List<String>
        return Gson().fromJson(value, listType)
    }

    // Metode untuk mengonversi List<String> ke String
    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        // Menggunakan Gson untuk mengonversi List<String> menjadi String
        return Gson().toJson(list)
    }
}