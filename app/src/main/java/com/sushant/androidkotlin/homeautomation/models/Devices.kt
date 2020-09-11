package com.sushant.androidkotlin.homeautomation.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "devices")
data class Devices(
    @PrimaryKey (autoGenerate = true) @ColumnInfo val uid: Int,
    @ColumnInfo @SerializedName("devices") val devices : List<Device>,
    @ColumnInfo @SerializedName("user") val user : User

) {
    override fun toString(): String {
        return super.toString()
    }


}