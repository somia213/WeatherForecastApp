package com.example.wheatherapp.ui.home.viewmodel

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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