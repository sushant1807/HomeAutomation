package com.sushant.androidkotlin.homeautomation.interfaces

import com.sushant.androidkotlin.homeautomation.models.Device

interface DeviceItemClickListner {

    fun onItemClick(device: Device, position: Int)
}