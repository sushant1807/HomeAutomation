package com.sushant.androidkotlin.homeautomation.network

import com.sushant.androidkotlin.homeautomation.models.Devices
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("data")
    fun getDevicesList() : Call<Devices>
}