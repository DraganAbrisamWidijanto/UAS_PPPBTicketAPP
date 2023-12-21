package com.example.utsdragan

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.TypeConverters

/**
 * Kelas `RoomDb` merupakan representasi dari database Room yang digunakan untuk menyimpan data tiket dan favorit.
 * Kelas ini mewarisi `RoomDatabase`.
 */
@Database(entities = [Ticket::class, Favorite::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class RoomDb: RoomDatabase() {
    //Mendapatkan objek DAO (Data Access Object) untuk entitas tiket.
    //@return Objek `TicketDao` yang digunakan untuk berinteraksi dengan data tiket.
    abstract fun ticketDao(): TicketDao

    //Mendapatkan objek DAO (Data Access Object) untuk entitas favorit.
    //@return Objek `FavoriteDao` yang digunakan untuk berinteraksi dengan data favorit.
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: RoomDb? = null

        /**
         * Mendapatkan instance tunggal dari database Room.
         *
         * @param context Konteks aplikasi.
         * @return Instance dari `RoomDb`.
         */
        fun getDatabase(context: Context): RoomDb? {
            synchronized(RoomDb::class.java) {
                if (instance == null) {
                    // Membangun database Room jika belum ada instans yang ada
                    instance = androidx.room.Room.databaseBuilder(context.applicationContext,
                        RoomDb::class.java, "ticket_database")
                        .build()
                }
                return instance
            }
        }
    }
}