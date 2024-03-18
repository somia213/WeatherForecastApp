package com.example.wheatherapp.ui.home.viewmodel

import Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.data.local.FavouriteEntity
import com.example.wheatherapp.data.models.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// I send Repository not context bec : depend on MVVM View should not be in ViewModel
// so I send repo
class HomeViewModel(private val repository: Repository):ViewModel() {

    // make weatherDetails private to prevent any class from update (change in ) data
   private val _weatherDetails = MutableStateFlow<WeatherResponse>(WeatherResponse())
    val weatherDetails: StateFlow<WeatherResponse>
        get() = _weatherDetails

    // Data Came (Receive Data)
    fun getWeatherDetails( latitude:Double,
                           longitude:Double){
        viewModelScope.launch {
             repository.getWeatherDetails(latitude,longitude)
                 .catch {  } // catch errors
                 .collect{
                     repository.insertFavourites(FavouriteEntity(weather = it))
                     _weatherDetails.value = it
                 }
        }
    }
}