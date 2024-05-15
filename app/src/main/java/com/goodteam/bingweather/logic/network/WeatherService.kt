package com.goodteam.bingweather.logic.network

import com.goodteam.bingweather.BingWeatherApplication
import com.goodteam.bingweather.logic.model.DailyResponse
import com.goodteam.bingweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2.6/${BingWeatherApplication.TOKEN}/{lng},{lat}/realtime")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.6/${BingWeatherApplication.TOKEN}/{lng},{lat}/daily") // 可以查询1-15天的数据
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String, @Query("dailysteps") days: Int): Call<DailyResponse>


    @GET("v3/weather/weatherInfo?&key=${BingWeatherApplication.GAO_KEY}&extensions=base")
    fun getRealtimeWeatherGao(@Query("city") city: String): Call<RealtimeResponse>

    @GET("v3/weather/weatherInfo?&key=${BingWeatherApplication.GAO_KEY}&extensions=all")
    fun getDailyWeatherGao(@Query("city") city: String): Call<DailyResponse>


    //"https://api.caiyunapp.com/v2.6/{Token}/101.6656,39.2072/daily?dailysteps=1"
    //"https://api.caiyunapp.com/v2.6/{Token}/101.6656,39.2072/realtime"
}