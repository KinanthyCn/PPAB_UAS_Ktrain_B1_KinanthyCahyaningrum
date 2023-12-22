package com.kinan.ktrain.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "train")
data class TrainDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "departure")
    val departure : String,

    @ColumnInfo(name = "destination")
    val destination : String,

    @ColumnInfo(name = "train")
    val train : String,

    @ColumnInfo(name = "classTrain")
    val classTrain : String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite : Boolean = false,

    @ColumnInfo(name = "uid")
    var uid : String = ""

)