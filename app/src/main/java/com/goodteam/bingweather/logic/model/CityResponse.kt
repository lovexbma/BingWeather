package com.goodteam.bingweather.logic.model

// 之前彩云获取城市列表api不能使用了，这里使用高德api，获取省内城市
data class CityResponse(val status: String, val districts: List<Province>)

// 省/直辖市
data class Province(val name: String, val center: String, val level: String, val districts: List<City>)

//市
data class City(val name: String, val center: String, val cityCode: String)