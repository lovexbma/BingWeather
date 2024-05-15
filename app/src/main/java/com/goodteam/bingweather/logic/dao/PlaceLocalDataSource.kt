package com.goodteam.bingweather.logic.dao

import android.content.Context
import com.goodteam.bingweather.logic.model.Location
import com.goodteam.bingweather.logic.model.PlaceData
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object PlaceLocalDataSource {

    var places = ArrayList<PlaceData>()

    fun loadCSV(context: Context){
        try {
            val assetsManager = context.assets
            var reader = BufferedReader(InputStreamReader(assetsManager.open("adcode.csv")))
            reader.use {
                reader.forEachLine {
                    val strs = it.split(',')
                    val name = strs[1]
                    val lng = strs[2]
                    val lat = strs[3]
                    places.add(PlaceData(name, Location(lng,lat), name))
                }
            }
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}