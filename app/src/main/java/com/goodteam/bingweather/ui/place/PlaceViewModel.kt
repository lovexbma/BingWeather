package com.goodteam.bingweather.ui.place

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.goodteam.bingweather.logic.Repository
import com.goodteam.bingweather.logic.model.City
import com.goodteam.bingweather.logic.model.Place
import com.goodteam.bingweather.logic.model.PlaceData

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    private val searchCityData = MutableLiveData<Pair<String,Int>>()

    val placeDataList = ArrayList<PlaceData>()

    val placeList = ArrayList<Place>()

    val cityList = ArrayList<City>()

    val placeLiveData = searchLiveData.switchMap { query ->
//        Repository.searchPlaces(query)
        Repository.searchPlacesLocal(query)
    }

    val cityLiveData = searchCityData.switchMap { pa ->
        Repository.searchCity(pa.first, pa.second)
    }

    fun loadPlaces(context: Context){
        Repository.loadCSV(context)
    }

    fun searchPlacesLocal(query: String){
        searchLiveData.value = query
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }

    fun searchCity(query: String, subd: Int){
        searchCityData.value = Pair(query, subd)
    }

    fun savePlace(place: PlaceData) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()
}