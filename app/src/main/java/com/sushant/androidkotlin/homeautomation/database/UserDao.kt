package com.sushant.androidkotlin.homeautomation.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sushant.androidkotlin.homeautomation.models.User
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert
    fun insertIntoUser(user: User)

    @Query("DELETE FROM user")
    fun clearUsers()

    @Query("SELECT * FROM user")
    fun getUserDetails(): Single<User>

}