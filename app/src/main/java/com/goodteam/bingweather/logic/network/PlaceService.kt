package com.goodteam.bingweather.logic.network

import com.goodteam.bingweather.BingWeatherApplication
import com.goodteam.bingweather.logic.model.CityResponse
import com.goodteam.bingweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PlaceService {
    @GET("v2.6/place?token=${BingWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

    @GET("v3/config/district?key=${BingWeatherApplication.GAO_KEY}")
    fun searchCity(@Query("keywords") query: String, @Query("subdistrict") subdistrict: Int): Call<CityResponse>
}