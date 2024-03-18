package com.example.wheatherapp.ui.favourite

import Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.data.local.FavouriteEntity
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: Repository):ViewModel() {

    private val _favouriteList = MutableLiveData<List<FavouriteEntity>>()
    val favouriteList : LiveData<List<FavouriteEntity>>
        get() = _favouriteList

    fun getFavouriteList(){
        viewModelScope.launch{
            repository.getFavourites()
                // by use flow i can use many benefits operations
                .catch {  }
                .collect{favourites->
                _favouriteList.value =favourites
            }

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