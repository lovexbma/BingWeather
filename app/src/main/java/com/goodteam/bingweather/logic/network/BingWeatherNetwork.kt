package com.goodteam.bingweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object BingWeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    suspend fun searchCity(query: String, subdistrict: Int) =
        placeService.searchCity(query, subdistrict).await()

    suspend fun getDailyWeather(lng: String, lat: String, days: Int) =
        weatherService.getDailyWeather(lng,lat,days).await()
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun getDailyWeatherGao(city: String) = weatherService.getDailyWeatherGao(city).await()
    suspend fun getRealtimeWeatherGao(city: String) = weatherService.getRealtimeWeatherGao(city).await()

    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine{ continuation ->
            enqueue(object: Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>){
                    val body = response.body()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable){
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}