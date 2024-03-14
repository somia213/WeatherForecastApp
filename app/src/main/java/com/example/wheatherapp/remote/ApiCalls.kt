package com.example.wheatherapp.remote

import com.example.wheatherapp.Constants
import com.example.wheatherapp.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {
    @GET("forecast")

    suspend fun getWeatherDetails(
        @Query("lat") latitude:Double,
        @Query("lon") longitude:Double,
        @Query("appid") apiKey:String = Constants.API_KEY,
    ): Response<WeatherResponse>
    // make return type response to check call status and handle different cases

}