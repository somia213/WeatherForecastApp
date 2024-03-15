package com.example.wheatherapp.ui.favourite

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavouriteViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    // used factory to add repository as dependency in viewModel
    // so to add repo in viewModel as dependency be through factory and shouldn't be through constructor

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            FavouriteViewModel(repository) as T
        }else{
            throw IllegalArgumentException("ViewModel Class is not Found")
        }
    }
}
