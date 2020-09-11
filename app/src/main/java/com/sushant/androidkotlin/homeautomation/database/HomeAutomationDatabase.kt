package com.sushant.androidkotlin.homeautomation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sushant.androidkotlin.homeautomation.models.Device

@Database(entities = [Device::class], version = DB_VERSION, exportSchema = false)
abstract class HomeAutomationDatabase : RoomDatabase() {

    abstract val devicesDao: DeviceDao

    companion object {
        @Volatile
        private var INSTANCE: HomeAutomationDatabase? = null

        fun getInstance(context: Context): HomeAutomationDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, HomeAutomationDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

const val DB_VERSION = 5

const val DB_NAME = "HomeAutomation.db"