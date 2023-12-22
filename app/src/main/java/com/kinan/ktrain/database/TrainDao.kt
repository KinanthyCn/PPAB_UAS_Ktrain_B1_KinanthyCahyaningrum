package com.kinan.ktrain.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface TrainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(train: TrainDB)

    @Update
    fun update(train: TrainDB)

    @Delete
    fun delete(train: TrainDB)

    @get:Query("SELECT * FROM train order by id Asc")
    val allTrain: LiveData<List<TrainDB>>

    // delete all
    @Query("DELETE FROM train")
    fun deleteAll()

    @Query("SELECT * FROM train WHERE uid = :uid")
    fun getTrainById(uid: String): LiveData<MutableList<TrainDB>>
}