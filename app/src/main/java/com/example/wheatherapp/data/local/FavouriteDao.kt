package com.example.wheatherapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourite_table")
    suspend fun getFavourites():List<FavouriteEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourites(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteEntity)

}