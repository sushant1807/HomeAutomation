package com.sushant.androidkotlin.homeautomation.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "address")
data class Address (

    @PrimaryKey @ColumnInfo @SerializedName("city") val city : String,
    @ColumnInfo @SerializedName("postalCode") val postalCode : Int,
    @ColumnInfo @SerializedName("street") val street : String,
    @ColumnInfo @SerializedName("streetCode") val streetCode : String,
    @ColumnInfo @SerializedName("country") val country : String
)