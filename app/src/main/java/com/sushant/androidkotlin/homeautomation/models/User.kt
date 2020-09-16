package com.sushant.androidkotlin.homeautomation.models

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "user")
data class User (

    @PrimaryKey @ColumnInfo @SerializedName("firstName") val firstName : String,
    @ColumnInfo @SerializedName("lastName") val lastName : String,
    @ColumnInfo @SerializedName("birthDate") val birthDate : String,
    @TypeConverters(UserAddressConverter::class)
    @ColumnInfo @SerializedName("address") val address : Address


) {
     class UserAddressConverter {
        @TypeConverter
        fun fromString(value: String?): Address {
            val type = object : TypeToken<Address?>() {}.type
            return Gson().fromJson(value, type)
        }

        @TypeConverter
        fun toString(address: Address?): String {
            val gson = Gson()
            return gson.toJson(address)
        }
    }

    override fun toString(): String {
        return "User(firstName='$firstName', lastName='$lastName', birthDate='$birthDate', address=$address)"
    }


}