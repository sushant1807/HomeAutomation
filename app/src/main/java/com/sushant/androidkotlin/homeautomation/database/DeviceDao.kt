package com.sushant.androidkotlin.homeautomation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sushant.androidkotlin.homeautomation.models.Device
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.concurrent.Callable

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(device: Device)

    @Query("DELETE FROM device")
    fun clear()

    @Query("SELECT * FROM device")
    fun getAllDevices(): Flowable<List<Device>>


}