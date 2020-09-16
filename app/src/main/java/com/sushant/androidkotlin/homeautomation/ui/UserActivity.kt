package com.sushant.androidkotlin.homeautomation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.database.HomeAutomationDatabase
import com.sushant.androidkotlin.homeautomation.databinding.ActivityUserBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_user)

        initInstance(binding)
    }

    @SuppressLint("CheckResult")
    private fun initInstance(binding: ActivityUserBinding) {
        val db = HomeAutomationDatabase.getInstance(application)
        db.userDao.getUserDetails()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                    it ->
                val userModel = it
                binding.user = userModel
                Timber.e("getDevicesFromLocal %s ", it.toString())
            }
            }
    }

    /*@SuppressLint("CheckResult")
    private fun initInstance() {
        val db = HomeAutomationDatabase.getInstance(application)
        db.userDao.getUserDetails()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                    it -> bin
                Timber.e("getDevicesFromLocal %s ", it.toString())
            }
    }*/

