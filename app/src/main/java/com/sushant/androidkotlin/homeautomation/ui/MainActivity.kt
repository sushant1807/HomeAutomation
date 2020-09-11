package com.sushant.androidkotlin.homeautomation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.adapters.DevicesAdapter
import com.sushant.androidkotlin.homeautomation.databinding.ActivityDeviceListBinding
import com.sushant.androidkotlin.homeautomation.viewmodels.DeviceListViewModel
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
            mAdapter.setData(kt)
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
}
