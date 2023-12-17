package com.example.utsdragan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTicket(ticket: Ticket)

    // query to get all ticket
    @Query("SELECT * FROM ticket where username = :username")
    fun getAllTicket(username: String): LiveData<List<Ticket>>
}