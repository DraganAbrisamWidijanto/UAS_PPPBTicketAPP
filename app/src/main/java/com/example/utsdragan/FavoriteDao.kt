package com.example.utsdragan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Antarmuka FavoriteDao berfungsi sebagai kontrak untuk mengakses dan
// memanipulasi data destinasi favorit dalam database
@Dao
interface FavoriteDao {

    // Fungsi insertFavorite digunakan untuk menyisipkan destinasi favorit
    // ke dalam database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: Favorite)

    // Fungsi deleteFavorite digunakan untuk menghapus destinasi favorit
    // dari database
    @Delete
    fun deleteFavorite(favorite: Favorite)

    // Fungsi getAllFavorites digunakan untuk mendapatkan semua destinasi
    // favorit pengguna berdasarkan username
    @Query("SELECT * FROM favorite where username = :username")
    fun getAllFavorites(username : String): LiveData<List<Favorite>>

}