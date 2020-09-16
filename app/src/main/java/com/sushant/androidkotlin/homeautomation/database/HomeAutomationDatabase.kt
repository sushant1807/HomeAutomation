package com.sushant.androidkotlin.homeautomation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sushant.androidkotlin.homeautomation.models.Address
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.models.User

@Database(entities = [Device::class, User::class, Address::class], version = DB_VERSION,
    exportSchema = false)
@TypeConverters(User.UserAddressConverter::class)
abstract class HomeAutomationDatabase : RoomDatabase() {

    abstract val devicesDao: DeviceDao
    abstract val userDao: UserDao
    abstract val addressDao: AddressDao

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

const val DB_VERSION = 4

const val DB_NAME = "HomeAutomation.db"