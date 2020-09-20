package com.sushant.androidkotlin.homeautomation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sushant.androidkotlin.homeautomation.models.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert
    fun insertIntoUser(user: User)

    @Query("DELETE FROM user")
    fun clearUsers()

    @Query("SELECT * FROM user")
    fun getUserDetails(): Single<User>

    @Update
    fun updateUser(vararg user: User)

}