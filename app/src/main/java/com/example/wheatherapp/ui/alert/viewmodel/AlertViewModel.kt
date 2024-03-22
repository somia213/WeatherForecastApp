package com.example.wheatherapp.ui.alert.viewmodel

import Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.data.models.AlertModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlertViewModel(private val repository: Repository) : ViewModel() {

    private val _stateGetAlert = MutableStateFlow<List<AlertModel>>(emptyList())
    val stateGetAlert : StateFlow<List<AlertModel>>
        get() = _stateGetAlert



    private val _stateInsetAlert = MutableStateFlow<Long>(0)
    val stateInsetAlert : StateFlow<Long>
        get() = _stateInsetAlert



    private val _stateSingleAlert = MutableStateFlow<AlertModel>(AlertModel())
    val stateSingleAlert :StateFlow<AlertModel>
        get() = _stateSingleAlert

    fun getAlerts(){
        viewModelScope.launch {
            repository.getAlerts().collect{
                _stateGetAlert.value = it
            }
        }
    }
    fun deleteAlert(alert : AlertModel){
        viewModelScope.launch {
            // pass id of alert model
            repository.deleteAlert(alert.id?:-1)
        }
    }

    fun getAlert(id:Int){
        viewModelScope.launch{
            val alertModel = repository.getAlert(id)
            _stateSingleAlert.value = alertModel
        }
    }

    fun insertAlert(alert: AlertModel){
        viewModelScope.launch {
            // after Insert Model get id
            val id = repository.insertAlert(alert)

            // pass id in state flow
            _stateInsetAlert.value = id
        }
    }
}