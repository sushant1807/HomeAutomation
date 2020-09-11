package com.sushant.androidkotlin.homeautomation.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager {

    companion object {
        fun isOnline(context: Context) : Boolean {
            val connectivityManager  : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }
    }
}