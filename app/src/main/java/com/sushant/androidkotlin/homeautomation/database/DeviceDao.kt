package com.sushant.androidkotlin.homeautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.models.Devices

@Dao
interface DevicesDao {

    @Insert
    suspend fun insert(device: Device)

    @Query("DELETE FROM device")
    fun clear()

    @Query("SELECT * FROM devices ORDER BY uid DESC")
    fun getAllDevices(): LiveData<List<Devices>>


}