package com.example.android.weather.ui.main

sealed class AppState{
    data class Success(val weatherData:Weather):AppState()
    data class Error(val error:Throwable):AppState()
    object Loading:AppState()
}
