package com.example.wheatherapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


// Step 1
@Entity(tableName = "alerts")
data class AlertModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var city: String? = null,
    // Mandatory
    var startTime: Long? = null,
    var endTime: Long? = null,
    var startDate: Long? = null,
    var endDate: Long? = null,
)
