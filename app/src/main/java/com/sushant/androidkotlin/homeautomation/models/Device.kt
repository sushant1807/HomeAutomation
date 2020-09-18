package com.sushant.androidkotlin.homeautomation.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "device")
data class Device (

    @PrimaryKey @ColumnInfo @SerializedName("id") val id : Int,
    @ColumnInfo @SerializedName("deviceName") val deviceName : String,
    @ColumnInfo @SerializedName("intensity") val intensity : Int,
    @ColumnInfo @SerializedName ("mode") val mode : String?,
    @ColumnInfo @SerializedName("productType") val productType : String
) : Serializable
