package com.example.wheatherapp.models

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("city")
    val city: City?,
    @SerializedName("cnt")
    val cnt: Int?,
    @SerializedName("cod")
    val cod: String?,
    @SerializedName("list")
    val list: List<ListResponse>?,
    @SerializedName("message")
    val message: Int?
)