package com.example.wheatherapp.ui.map.viewmodel

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import java.lang.IllegalArgumentException


class MapViewModelFactory (
    private val repository: Repository ):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Class is not Found")
        }
    }
}
