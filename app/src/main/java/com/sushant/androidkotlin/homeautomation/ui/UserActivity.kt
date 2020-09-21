package com.sushant.androidkotlin.homeautomation.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.database.HomeAutomationDatabase
import com.sushant.androidkotlin.homeautomation.databinding.ActivityUserDetailsBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityUserDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_user_details)

        initInstance(binding)
    }

    @SuppressLint("CheckResult")
    private fun initInstance(binding: ActivityUserDetailsBinding) {
        val db = HomeAutomationDatabase.getInstance(application)
        db.userDao.getUserDetails()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { it ->
                val userModel = it
                binding.user = userModel
                Timber.e("getDevicesFromLocal %s ", it.toString())

            }
    }


}

/* //Uncomment while implementing the Update functionality in User
fun onSaveButtonClick() {
    Timber.e("On Save button clicked")
    //TODO Implement the Update functionality in User
}

fun onCancelButtonClick() {
    Timber.e("On Cancel button clicked")
    //TODO Implement the Update functionality in User

}*/


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

