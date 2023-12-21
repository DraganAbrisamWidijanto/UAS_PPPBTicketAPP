package com.example.utsdragan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Mendefinisikan entitas Ticket untuk Room Database.
@Entity(tableName = "ticket")
data class Ticket(
    // ID unik yang dihasilkan secara otomatis.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    // Nama pengguna sampai harga tiket.
    @ColumnInfo(name = "username")
    val username: String = "",
    @ColumnInfo(name = "nama")
    val nama: String = "",
    @ColumnInfo(name = "tanggal")
    val tanggal: String = "",
    @ColumnInfo(name = "jam")
    val jam: String = "",
    @ColumnInfo(name = "asal")
    val asal: String = "",
    @ColumnInfo(name = "tujuan")
    val tujuan: String = "",
    @ColumnInfo(name = "harga")
    val harga: Int = 0,
    // List pesanan tambahan yang mungkin dipilih oleh pengguna.
    @ColumnInfo(name = "pesananTambahan")
    val pesananTambahan: List<String> = emptyList(),
)