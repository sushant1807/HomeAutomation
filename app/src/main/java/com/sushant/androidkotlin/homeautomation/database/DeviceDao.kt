package com.sushant.androidkotlin.homeautomation.database

import androidx.room.*
import com.sushant.androidkotlin.homeautomation.models.Device
import io.reactivex.Flowable

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(device: Device)

    @Query("DELETE FROM device")
    fun clear()

    @Query("SELECT * FROM device")
    fun getAllDevices(): Flowable<List<Device>>

    @Update
    fun updateDevice(vararg device: Device)


}