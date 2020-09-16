package com.sushant.androidkotlin.homeautomation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.adapters.DevicesAdapter
import com.sushant.androidkotlin.homeautomation.database.HomeAutomationDatabase
import com.sushant.androidkotlin.homeautomation.databinding.ActivityDeviceListBinding
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.models.User
import com.sushant.androidkotlin.homeautomation.viewmodels.DeviceListViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: DevicesAdapter
    private lateinit var mViewModel: DeviceListViewModel
    private lateinit var mActivityBinding: ActivityDeviceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_device_list)

        mViewModel = ViewModelProvider(this).get(DeviceListViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this
        Timber.plant(Timber.DebugTree());

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mActivityBinding.recyclerView.setHasFixedSize(true)
        mActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = DevicesAdapter(ArrayList())
        mActivityBinding.recyclerView.adapter = mAdapter
    }

    private fun initializeObservers() {
        mViewModel.fetchDevicesFromServer(this, false).observe(this,
            Observer { kt ->
            Timber.e("initializeObservers ")
            try {
                //if (kt.isNotEmpty()) {
                storeDevicesLocally(kt.devices, application)
                Timber.e("initializeObservers:  %s ", kt.user)
                getDevicesFromLocal(application)
                storeUserLocally(kt.user, application)
                //}
            } catch (e: Exception ) {
                e.printStackTrace()
                Timber.e("Catch exception %s ", e.message)
            }

        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun storeDevicesLocally(devices: List<Device>, context: Context) {
        Timber.e("putToRoomDb %s ", devices.toString())
        val db = HomeAutomationDatabase.getInstance(context)
        val list = arrayListOf<String>()
        for (device in devices) {
            Completable.fromRunnable {
                db.devicesDao.insert(device)
            }
                .subscribeOn(Schedulers.io())
                .subscribe( {
                    Timber.e("Inserted into device")
                    list.add(device.deviceName)
                } , { throwable ->
                Timber.e("Inserted into device failure %s ", throwable.message)
            })
        }

        if (devices.size === list.size) {
            getDevicesFromLocal(context)
        }
    }

    @SuppressLint("CheckResult")
    private fun storeUserLocally(user: User, context: Context) {
        Timber.e("storeUserLocally ")
        val db = HomeAutomationDatabase.getInstance(context)
            Completable.fromRunnable {
                db.userDao.insertIntoUser(user)
            }
                .subscribeOn(Schedulers.io())
                .doOnError(Consumer {
                    Timber.e("Reached doOnError in storeUserLocally")
                })
                .subscribe ( {
                    Timber.e("Inserted into User")
                    storeUserAddressLocally(user, context)
                }, { throwable ->
                    Timber.e("Inserted into User failure %s ", throwable.message)
                })
    }

    @SuppressLint("CheckResult")
    private fun storeUserAddressLocally(user: User, context: Context) {
        Timber.e("storeUserAddressLocally ")
        val db = HomeAutomationDatabase.getInstance(context)
        Completable.fromRunnable {
            db.addressDao.insertIntoUserAddress(user.address)
        }
            .subscribeOn(Schedulers.io())
            .doOnError(Consumer {
                Timber.e("Reached doOnError in storeUserAddressLocally")
            })
            .subscribe ( {
                Timber.e("Inserted into Address")
            }, { throwable ->
                Timber.e("Inserted into Address failure %s ", throwable.message)
            })
    }

    @SuppressLint("CheckResult")
    private fun getDevicesFromLocal(context: Context) {
        val db = HomeAutomationDatabase.getInstance(context)

        db.devicesDao.getAllDevices()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                it -> mAdapter.setData(it);
                Timber.e("getDevicesFromLocal %s ", it.toString())
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_profile_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.profile -> {
                //newGame()
                Timber.e("Clicked on menu item")
                val intent = Intent(this, UserActivity::class.java)
                // To pass any data to next activity
                //intent.putExtra("keyIdentifier", value)
                // start your next activity
                startActivity(intent)
                true
            }
//            R.id.help -> {
//                showHelp()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
