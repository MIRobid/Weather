package com.example.android.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel (
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl:Repository=RepositoryImpl()) :

ViewModel() {
    fun getLiveData()=liveDataToObserve

    fun getWeatherFromLocalSource()=getDataFromLocalSource()
    fun getWeatherFromRemoteSource()=getDataFromLocalSource()

    private fun getDataFromLocalSource(){
        liveDataToObserve.value=AppState.Loading
        Thread{
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
        }.start()
    }
}