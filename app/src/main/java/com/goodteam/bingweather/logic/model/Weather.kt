package com.goodteam.bingweather.logic.model

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)

data class WeatherGao(val realtime: RealtimeResponse, val daily: DailyResponse)