package com.example.wheatherapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wheatherapp.models.WeatherResponse

@Entity(tableName = "favourite_table")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int ?=null,

    val weather:WeatherResponse

    // using Converter to save model as string in db and
    // convert string to model(object) when retrieve it
    // make converter class

)