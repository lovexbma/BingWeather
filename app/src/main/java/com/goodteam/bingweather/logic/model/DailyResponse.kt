package com.goodteam.bingweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.Date

// 彩云天气
data class DailyResponse(val status: String, val result: Result){

    data class Result(val daily: Daily)

    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)
}

// 高德天气 3日
data class DailyResponseGao(val status: String, val forecasts: List<Forecast>) {

    data class Forecast(
        val city: String, val province: String, val reporttime: String,
        val casts: List<Cast>
    )

    data class Cast(
        val date: String,//日期
        val week: String,//星期几
        val dayweather: String,//白天天气
        val nightweather: String,//夜晚天气
        val daytemp: String,//白天温度
        val nighttemp: String,//晚上温度
        val daywind: String,//白天风向
        val nightwind: String,//晚上风向
        val daypower: String,//白天风力
        val nightpower: String,//晚上风力
    )
}