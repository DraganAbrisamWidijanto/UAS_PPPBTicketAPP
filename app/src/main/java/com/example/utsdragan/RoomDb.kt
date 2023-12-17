package com.example.utsdragan

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.TypeConverters

@Database(entities = [Ticket::class, Favorite::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class RoomDb: RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: RoomDb? = null
        fun getDatabase(context: Context): RoomDb? {
            synchronized(RoomDb::class.java) {
                if (instance == null) {
                    instance = androidx.room.Room.databaseBuilder(context.applicationContext,
                        RoomDb::class.java, "ticket_database")
                        .build()
                }
                return instance
            }
        }
    }
}