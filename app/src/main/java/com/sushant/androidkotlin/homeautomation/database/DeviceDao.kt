package com.sushant.androidkotlin.homeautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushant.androidkotlin.homeautomation.models.Device

@Dao
interface DeviceDao {

    @Insert
    fun insert(device: Device)

    @Query("DELETE FROM device")
    fun clear()

    @Query("SELECT * FROM device ORDER BY id DESC")
    fun getAllDevices(): LiveData<List<Device>>


}