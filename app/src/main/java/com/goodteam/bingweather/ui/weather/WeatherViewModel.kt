package com.goodteam.bingweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.goodteam.bingweather.logic.Repository
import com.goodteam.bingweather.logic.model.Location

class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Pair<Location, Int>>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    var days = 1

    val weatherLiveData = locationLiveData.switchMap { d ->
        Repository.refreshWeather(d.first.lng, d.first.lat, d.second)
    }

    fun refreshWeather(lng: String, lat: String, days: Int){
        locationLiveData.value = Pair(Location(lng, lat), days)
    }
}