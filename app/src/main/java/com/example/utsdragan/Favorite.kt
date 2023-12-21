package com.example.utsdragan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var username: String = "",
    var nama: String = "",
    var uri:String = "",
    //var uri: String = "https://github.com/DraganAbrisamWidijanto/imgdatamyclass/blob/main/poto3.jpg?raw=true",
    var isChecked: Boolean = false
)