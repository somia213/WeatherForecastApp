package com.example.wheatherapp.data.local

import androidx.room.TypeConverter
import com.example.wheatherapp.data.models.WeatherResponse
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun fromStringToWeather(weather:String?): WeatherResponse?{
        return weather?.let { Gson().fromJson(it, WeatherResponse::class.java) }
    }

    @TypeConverter
    fun fromWeatherToString(weather: WeatherResponse?):String?{
        return weather?.let { Gson().toJson(it) }
    }
}