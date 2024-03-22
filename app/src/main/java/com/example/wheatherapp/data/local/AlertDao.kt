package com.example.wheatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wheatherapp.data.models.AlertModel
import kotlinx.coroutines.flow.Flow


//Step 2
@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts")
    fun getAlerts(): Flow<List<AlertModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // long -> for receive id
    suspend fun insertAlert(alert: AlertModel): Long

    // i use id instead of model to send to worker
    @Query("DELETE FROM alerts where id = :id")
    suspend fun deleteAlert(id: Int)

    @Query("SELECT * FROM alerts WHERE id = :id")
    fun getAlert(id: Int): AlertModel
}