package com.sushant.androidkotlin.homeautomation.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceModule private constructor(){

    companion object {
        private val BASE_URL = "http://storage42.com/modulotest/"
        private lateinit var mApiServices: ApiServices
        private var mInstance: ApiServiceModule? = null
        fun getInstance(): ApiServiceModule {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ApiServiceModule()
                }
            }
            return mInstance!!
        }
    }

    init {
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build();
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        mApiServices = retrofit.create(ApiServices::class.java)
    }

    fun getApiService() = mApiServices
}