package com.sushant.androidkotlin.homeautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushant.androidkotlin.homeautomation.models.Address

@Dao
interface AddressDao {

    @Insert
    fun insertIntoUserAddress(address: Address)

    @Query("DELETE FROM address")
    fun clearAddress()

    @Query("SELECT * FROM address")
    fun getUserAddressDetails(): LiveData<Address>
}