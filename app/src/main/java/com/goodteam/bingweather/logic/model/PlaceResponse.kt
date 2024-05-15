package com.goodteam.bingweather.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,
    @SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)

// 实际使用 model
data class PlaceData(val name: String, val location: Location, val address: String)