package com.example.wheatherapp.models

import com.google.gson.annotations.SerializedName


data class Rain(
    @SerializedName("3h")
    val h: Double?
)