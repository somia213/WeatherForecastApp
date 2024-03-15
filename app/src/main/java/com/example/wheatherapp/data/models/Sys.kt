package com.example.wheatherapp.data.models

import com.google.gson.annotations.SerializedName


data class Sys(
    @SerializedName("pod")
    val pod: String?
)