package com.hakam.myweatherapp

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hakam.myweatherapp.model.City
import com.hakam.myweatherapp.model.WeatherInfoResponse
import com.hakam.myweatherapp.network.ApiInterface
import com.hakam.myweatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context) : WeatherInfoShowModel {
    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList)

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!)
        }
    }

    override fun getWeatherInfo(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!)
                else
                    callback.onRequestFailed(response.message())
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }
        })
    }
}