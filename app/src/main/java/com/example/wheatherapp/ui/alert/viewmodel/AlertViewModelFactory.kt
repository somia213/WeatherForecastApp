package com.example.wheatherapp.ui.alert.viewmodel

import Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wheatherapp.ui.home.viewmodel.HomeViewModel
import java.lang.IllegalArgumentException

class AlertViewModelFactory (
    private val repository: Repository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(AlertViewModel::class.java)){
                AlertViewModel(repository) as T
            }else{
                throw IllegalArgumentException("ViewModel Class is not Found")
            }
        }
}