package com.example.wheatherapp.ui.home

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherapp.ui.favourite.FavouriteViewModel
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Class is not Found")
        }
    }
}