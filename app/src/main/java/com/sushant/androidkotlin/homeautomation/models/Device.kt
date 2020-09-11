package com.sushant.androidkotlin.homeautomation.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/*
data class Device(val id: Int, val deviceName: String, val position: Int, val productType: String) {
    override fun toString(): String {
        return super.toString()
    }
}*/

@Entity(tableName = "device")
data class Device (

    @SerializedName("id") val id : Int,
    @SerializedName("deviceName") val deviceName : String,
    @SerializedName("intensity") val intensity : Int,
    @SerializedName("mode") val mode : String,
    @SerializedName("productType") val productType : String
)
