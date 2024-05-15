package com.goodteam.bingweather.logic.model

import com.google.gson.annotations.SerializedName

// 彩云实时天气
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality)

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}

// 高德天气 实时
data class RealtimeResponseGao(val status: String, val lives: Lives) {
    data class Lives(
        val weather: String,
        val temperature: String,
        val winddirection: String,
        val windpower: String,
        val humidity: String,//湿度
        val reporttime: String,
    )
}