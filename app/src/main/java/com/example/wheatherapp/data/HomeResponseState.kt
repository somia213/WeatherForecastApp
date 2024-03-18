package com.example.wheatherapp.data

import com.example.wheatherapp.data.models.WeatherResponse

sealed class HomeResponseState<T>{
    // make it generic type to be used in any other class

//    class OnNoLocationDetected<T> : HomeResponseState<T>()
    class OnSuccess<T>(var data: T) : HomeResponseState<T>()
    class OnError<T>(var message: String) : HomeResponseState<T>()
    class OnLoading<T> : HomeResponseState<T>()
//    class OnNothingData<T> : HomeResponseState<T>()
}
