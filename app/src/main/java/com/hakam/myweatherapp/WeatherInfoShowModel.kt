package com.hakam.myweatherapp

import com.hakam.myweatherapp.model.City
import com.hakam.myweatherapp.model.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}