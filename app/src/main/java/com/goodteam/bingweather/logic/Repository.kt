package com.goodteam.bingweather.logic

import android.content.Context
import android.util.Log
import androidx.lifecycle.liveData
import com.goodteam.bingweather.logic.dao.PlaceDao
import com.goodteam.bingweather.logic.dao.PlaceLocalDataSource
import com.goodteam.bingweather.logic.model.Place
import com.goodteam.bingweather.logic.model.PlaceData
import com.goodteam.bingweather.logic.model.Weather
import com.goodteam.bingweather.logic.model.WeatherGao
import com.goodteam.bingweather.logic.network.BingWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    fun loadCSV(context: Context) {
        PlaceLocalDataSource.loadCSV(context)
    }

    fun searchPlacesLocal(query: String) = fire(Dispatchers.IO) {
        coroutineScope {
            var filterPlaces = ArrayList<PlaceData>()
            val runFilterPlaces = async {
                val places = PlaceLocalDataSource.places.filter {
                    it.name.contains(query)
                }
                filterPlaces = places as ArrayList<PlaceData>
            }
            runFilterPlaces.await()
            if (filterPlaces.isNotEmpty()) {
                Result.success(filterPlaces)
            } else {
                Result.failure(
                    RuntimeException("can't find query name in local place data source")
                )
            }
        }
    }

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = BingWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }

    fun searchCity(query: String, subd: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val cityResponse = BingWeatherNetwork.searchCity(query, subd)
            if (cityResponse.status == "1") {
                val city = cityResponse.districts
                Result.success(city)
            } else {
                Result.failure(RuntimeException("response status is ${cityResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun refreshWeather(lng: String, lat: String, days: Int) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                BingWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                BingWeatherNetwork.getDailyWeather(lng, lat, days)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status} " +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    fun refreshWeatherGao(city: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                BingWeatherNetwork.getRealtimeWeatherGao(city)
            }
            val deferredDaily = async {
                BingWeatherNetwork.getDailyWeatherGao(city)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "1" && dailyResponse.status == "1") {
                val weather = WeatherGao(realtimeResponse, dailyResponse)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status} " +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    // 存储上次选择的城市
    fun savePlace(place: PlaceData) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}