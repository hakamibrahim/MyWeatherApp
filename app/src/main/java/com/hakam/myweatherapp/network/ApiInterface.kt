package com.hakam.myweatherapp.network

import com.hakam.myweatherapp.model.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(
        @Query("id") cityId: Int
    ): Call<WeatherInfoResponse>
}