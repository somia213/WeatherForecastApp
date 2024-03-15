package com.example.wheatherapp.data.models

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    // used SerializedName -> write name of api comp in SerializedName and
    // named property as i like
    @SerializedName("city")
    val city: City?= null,
    @SerializedName("cnt")
    val cnt: Int?= null,
    @SerializedName("cod")
    val cod: String?= null,
    @SerializedName("list")
    val list: List<ListResponse> = emptyList(),
    @SerializedName("message")
    val message: Int?= null
)