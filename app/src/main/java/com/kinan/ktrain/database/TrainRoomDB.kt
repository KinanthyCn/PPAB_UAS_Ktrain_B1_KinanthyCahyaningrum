package com.kinan.ktrain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kinan.ktrain.database.TrainDB

@Database(entities = [TrainDB::class], version = 1, exportSchema = false)
abstract class TrainRoomDB : RoomDatabase(){
    abstract fun trainDao(): TrainDao?
    companion object{
        @Volatile
        private var INSTANCE: TrainRoomDB? = null
        fun getDatabase(context : android.content.Context) : TrainRoomDB?{
            if (INSTANCE == null){
                synchronized(TrainRoomDB::class.java){
                    INSTANCE = androidx.room.Room.databaseBuilder(
                        context.applicationContext,
                        TrainRoomDB::class.java,
                        "train_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}