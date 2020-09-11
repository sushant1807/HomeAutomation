package com.sushant.androidkotlin.homeautomation.models

import com.google.gson.annotations.SerializedName


data class Address (

    @SerializedName("city") val city : String,
    @SerializedName("postalCode") val postalCode : Int,
    @SerializedName("street") val street : String,
    @SerializedName("streetCode") val streetCode : String,
    @SerializedName("country") val country : String
)