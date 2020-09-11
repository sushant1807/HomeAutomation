package com.sushant.androidkotlin.homeautomation.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User (

    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("address") val address : Address,
    @SerializedName("birthDate") val birthDate : String
)