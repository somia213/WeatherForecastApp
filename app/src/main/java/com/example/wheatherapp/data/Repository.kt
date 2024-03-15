package com.example.wheatherapp.data

import android.content.Context
import com.example.wheatherapp.Constants
import com.example.wheatherapp.data.local.FavouriteDataBase
import com.example.wheatherapp.data.local.FavouriteEntity
import com.example.wheatherapp.data.models.WeatherResponse
import com.example.wheatherapp.data.remote.ApiCalls
import com.example.wheatherapp.data.remote.RetrofitInstance
import retrofit2.Response
import retrofit2.http.Query

class Repository(context: Context) {

    lateinit var network  : ApiCalls
    lateinit var room : FavouriteDataBase

    init {
        network =  RetrofitInstance().api
        room = FavouriteDataBase.invoke(context)
    }

    // Local
    suspend fun getFavourites():List<FavouriteEntity>{
        return room.favouriteDao().getFavourites()
    }


    suspend fun insertFavourites(favourite: FavouriteEntity){
        room.favouriteDao().insertFavourites(favourite)
    }

    suspend fun deleteFavourite(favourite: FavouriteEntity){
        room.favouriteDao().deleteFavourite(favourite)
    }

    // Remote
    suspend fun getWeatherDetails(
        latitude:Double,
        longitude:Double,
    ): Response<WeatherResponse>{
        return network.getWeatherDetails(
            latitude= latitude,
            longitude = longitude,
            )
    }
}