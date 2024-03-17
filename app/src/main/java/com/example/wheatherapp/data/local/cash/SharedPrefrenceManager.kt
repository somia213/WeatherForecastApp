package com.example.wheatherapp.data.local.cash

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng

// extension function ->
// Datastore and SharedPreference
const val USER_LOCATION_SETTINGS_KEY_NAME = "USER_LOCATION_SETTINGS_KEY_NAME"
const val LANGUAGE_SETTINGS_KEY_NAME = "LANGUAGE_SETTINGS_KEY_NAME"
const val TEMPERATURE_SETTINGS_KEY_NAME = "TEMPERATURE_SETTINGS_KEY_NAME"
const val WIND_SPEED_SETTINGS_KEY_NAME = "WIND_SPEED_SETTINGS_KEY_NAME"
const val LOCATION_PROVIDER_SETTINGS_KEY_NAME = "LOCATION_PROVIDER_SETTINGS_KEY_NAME"
const val ALERT_PROVIDER_SETTINGS_KEY_NAME = "ALERT_PROVIDER_SETTINGS_KEY_NAME"
const val SHARED_PREF = "SHARED_PREF"
const val PREFERRED_LANGUAGE = "PREFERRED_LANGUAGE"

const val SHARED_SETTINGS = "SHARED_SETTINGS"
private const val SEPRATE = "$$"

fun Context.getSharedSettings(): Settings {
    return Settings(
        language = getSettingsLanguage(),
        locationProvider = getSettingsLocationProvider(),
        alertProvider = getSettingsAlertProvider(),
        windSpeed = getSettingsWindSpeed(),
        temperature = getSettingsTemperature(),
        userLocation = getSettingsUserLocation()
    )
}

fun Context.getSettingsUserLocation(): LatLng {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val userLocation =
        (preferences.getString(USER_LOCATION_SETTINGS_KEY_NAME, "0.0" + SEPRATE + "0.0")
            ?: ("0.0" + SEPRATE + "0.0")).split(SEPRATE)
    return if (userLocation.size == 2)
        LatLng(userLocation[0].toDouble(), userLocation[1].toDouble())
    else
        LatLng(0.0, 0.0)
}

fun Context.getSettingsTemperature(): Temperature {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return enumValueOf<Temperature>(
        preferences.getString(
            TEMPERATURE_SETTINGS_KEY_NAME,
            Temperature.Celsius.name
        ) ?: Temperature.Celsius.name
    )
}

fun Context.getSettingsLanguage(): Language {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return enumValueOf<Language>(
        preferences.getString(
            LANGUAGE_SETTINGS_KEY_NAME,
            Language.English.name
        ) ?: Language.English.name
    )
}

fun Context.getSettingsWindSpeed(): WindSpeed {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return enumValueOf<WindSpeed>(
        preferences.getString(
            WIND_SPEED_SETTINGS_KEY_NAME,
            WindSpeed.Meter.name
        ) ?: WindSpeed.Meter.name
    )
}

fun Context.getSettingsAlertProvider(): AlertProvider {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return enumValueOf<AlertProvider>(
        preferences.getString(
            ALERT_PROVIDER_SETTINGS_KEY_NAME,
            AlertProvider.Notification.name
        ) ?: AlertProvider.Notification.name
    )
}

fun Context.getSettingsLocationProvider(): LocationProvider {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return enumValueOf<LocationProvider>(
        preferences.getString(
            LOCATION_PROVIDER_SETTINGS_KEY_NAME,
            LocationProvider.Nothing.name
        ) ?: LocationProvider.Nothing.name
    )
}


// Settings
fun Context.setSharedLocation(latlng: LatLng) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(
        USER_LOCATION_SETTINGS_KEY_NAME,
        latlng.latitude.toString() + SEPRATE + latlng.longitude.toString()
    )
    editor.apply()
}


// Language
fun Context.setSharedSettings(language: Language) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(LANGUAGE_SETTINGS_KEY_NAME, language.name)
    editor.apply()
}

// Temperature
fun Context.setSharedSettings(temperature: Temperature) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(TEMPERATURE_SETTINGS_KEY_NAME, temperature.name)
    editor.apply()
}

// WindSpeed
fun Context.setSharedSettings(windSpeed: WindSpeed) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(WIND_SPEED_SETTINGS_KEY_NAME, windSpeed.name)
    editor.apply()
}

// AlertProvider
fun Context.setSharedSettings(alertProvider: AlertProvider) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(ALERT_PROVIDER_SETTINGS_KEY_NAME, alertProvider.name)
    editor.apply()
}

// Location Provider
fun Context.setSharedSettings(locationProvider: LocationProvider) {
    val preferences: SharedPreferences =
        this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString(LOCATION_PROVIDER_SETTINGS_KEY_NAME, locationProvider.name)
    editor.apply()
}



