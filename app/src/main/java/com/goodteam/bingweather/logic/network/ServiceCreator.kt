package com.goodteam.bingweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"
    private const val BASE_GAO_URL = "https://restapi.amap.com/"

    //查询行政区
    //https://restapi.amap.com/v3/config/district?keywords=北京&subdistrict=2&key=<用户的key>
    //未来3天天气
    //https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=<用户的key>&extensions=all
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}