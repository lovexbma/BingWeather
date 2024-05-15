package com.goodteam.bingweather

import android.app.Application
import android.content.Context

class BingWeatherApplication : Application() {

    companion object{
        const val TOKEN = ""
        const val GAO_KEY = ""
        lateinit var context: Context
    }

    override fun onCreate(){
        super.onCreate()
        context = applicationContext
    }
}