package com.example.wheatherapp.ui.favourite

import Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.data.local.FavouriteEntity
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: Repository):ViewModel() {

    private val _favouriteList = MutableLiveData<List<FavouriteEntity>>()
    val favouriteList : LiveData<List<FavouriteEntity>>
        get() = _favouriteList

    fun getFavouriteList(){
        viewModelScope.launch{
            _favouriteList.value = repository.getFavourites()

        }
    }

    fun deleteFavourite(favourite:FavouriteEntity){
        viewModelScope.launch {
            repository.deleteFavourite(favourite)
        }
    }

    fun insertFavourite(favourite: FavouriteEntity){
        viewModelScope.launch{
            repository.insertFavourites(favourite)
        }
    }
}