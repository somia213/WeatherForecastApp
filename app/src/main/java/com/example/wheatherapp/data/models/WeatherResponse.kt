package com.example.wheatherapp.data.models

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    // used SerializedName -> write name of api comp in SerializedName and
    // named property as i like
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