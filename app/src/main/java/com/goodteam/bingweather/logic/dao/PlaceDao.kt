package com.goodteam.bingweather.logic.dao

import android.content.Context
import com.goodteam.bingweather.BingWeatherApplication
import com.goodteam.bingweather.logic.model.PlaceData
import com.google.gson.Gson

object PlaceDao {

    fun savePlace(place: PlaceData){
        val editor = sharedPreferences().edit()
        editor.putString("place", Gson().toJson(place))
        editor.apply()
    }

    fun getSavedPlace(): PlaceData {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, PlaceData::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = BingWeatherApplication.context
        .getSharedPreferences("bing_weather", Context.MODE_PRIVATE)
}