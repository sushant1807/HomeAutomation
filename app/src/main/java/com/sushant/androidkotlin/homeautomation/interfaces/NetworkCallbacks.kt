package com.sushant.androidkotlin.homeautomation.interfaces

interface NetworkCallbacks {

    fun onNetworkConnected()

    fun onNetworkDisconnected(th : Throwable)

}