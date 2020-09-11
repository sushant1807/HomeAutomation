package com.sushant.androidkotlin.homeautomation.models

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("address") val address : Address,
    @SerializedName("birthDate") val birthDate : String
)