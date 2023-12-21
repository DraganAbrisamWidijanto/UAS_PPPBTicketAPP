package com.example.utsdragan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Antarmuka (interface) untuk mengakses dan memanipulasi data tiket dalam database menggunakan Room.
@Dao
interface TicketDao {

    // Metode untuk menyisipkan tiket ke dalam database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTicket(ticket: Ticket)

    // Query untuk mendapatkan semua tiket berdasarkan nama pengguna.
    @Query("SELECT * FROM ticket where username = :username")
    // Menggunakan LiveData agar dapat mengamati perubahan data secara otomatis.
    fun getAllTicket(username: String): LiveData<List<Ticket>>
}