package com.example.utsdragan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
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
    @ColumnInfo(name = "pesananTambahan")
    val pesananTambahan: List<String> = emptyList(),

)