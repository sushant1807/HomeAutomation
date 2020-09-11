package com.sushant.androidkotlin.homeautomation.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.adapters.DevicesAdapter
import com.sushant.androidkotlin.homeautomation.database.HomeAutomationDatabase
import com.sushant.androidkotlin.homeautomation.databinding.ActivityDeviceListBinding
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.viewmodels.DeviceListViewModel
import io.reactivex.Completable
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

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mActivityBinding.recyclerView.setHasFixedSize(true)
        mActivityBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = DevicesAdapter(mViewModel.getDeviceList().value)
        mActivityBinding.recyclerView.adapter = mAdapter
    }

    private fun initializeObservers() {
        mViewModel.fetchDevicesFromServer(this, false).observe(this, Observer { kt ->
            Timber.e("initializeObservers ")
            //if (kt!= null) {
            try {
                mAdapter.setData(kt)
                putToRoomDb(kt, application)
            } catch (e: Exception ) {
                e.printStackTrace()
                Timber.e("Catch exception %s ", e.message)
            }

        })
        /*mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
               // mActivityBinding.floatingActionButton.In()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
               // mActivityBinding.floatingActionButton.show()
            }
        })*/
    }

    @SuppressLint("CheckResult")
    private fun putToRoomDb(devices: List<Device>, context: Context) {
        for (device in devices) {
            val db = HomeAutomationDatabase.getInstance(context)
            Completable.fromRunnable(Runnable {
                db.devicesDao.insert(device)
            })
                .subscribeOn(Schedulers.io())
                .subscribe();

        }
    }
}
