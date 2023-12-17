package com.example.utsdragan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var username: String = "",
    var nama: String = "",
    var uri: String = "https://w0.peakpx.com/wallpaper/723/517/HD-wallpaper-sunaookami-shiroko-blue-archive-anime-board.jpg",
    var isChecked: Boolean = false
)