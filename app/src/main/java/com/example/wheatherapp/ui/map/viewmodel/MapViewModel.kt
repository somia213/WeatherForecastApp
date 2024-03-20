package com.example.wheatherapp.ui.map.viewmodel

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.data.local.FavouriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val repository: Repository): ViewModel() {

    fun setFavorite(favourite: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertFavourites(favourite)
            } catch (e: Exception) {
                throw e
            }

        }
    }

}