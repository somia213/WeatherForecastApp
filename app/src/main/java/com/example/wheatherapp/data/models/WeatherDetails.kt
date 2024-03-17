package com.example.wheatherapp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(

    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("population")
    val population: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("timezone")
    val timezone: Int?
)

data class Clouds(
    @SerializedName("all")
    val all: Int?
)

data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)

data class ListResponse(
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("dt")
    val dt: Long?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("pop")
    val pop: Double?,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("visibility")
    val visibility: Double?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
    @SerializedName("wind")
    val wind: Wind?
)

data class Main(
    //SerializedName -->
    @SerializedName("feels_like")
    val feelsLike: Double?,
    @SerializedName("grnd_level")
    val grndLevel: Double?,
    @SerializedName("humidity")
    val humidity: Double?,
    @SerializedName("pressure")
    val pressure:Double?,
    @SerializedName("sea_level")
    val seaLevel:Double?,
    @SerializedName("temp")
    val temp: Double?,
    @SerializedName("temp_kf")
    val tempKf: Double?,
    @SerializedName("temp_max")
    val tempMax: Double?,
    @SerializedName("temp_min")
    val tempMin: Double?
)

data class Rain(
    @SerializedName("3h")
    val h: Double?
)
data class Sys(
    @SerializedName("pod")
    val pod: String?
)

data class Weather(
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?
)

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

data class Wind(
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Double?,
    @SerializedName("speed")
    val speed: Double?
)