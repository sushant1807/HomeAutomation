package com.sushant.androidkotlin.homeautomation.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sushant.androidkotlin.homeautomation.interfaces.NetworkCallbacks
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.models.Devices
import com.sushant.androidkotlin.homeautomation.network.ApiServiceModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DeviceRepository(){

    private lateinit var mCallback: NetworkCallbacks

    private var mDeviceList : MutableLiveData<List<Device>> = MutableLiveData<List<Device>>().apply {
        value = emptyList();
    }

    companion object{
        private var mInstance: DeviceRepository? =null
        fun getInstance(): DeviceRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = DeviceRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mDevicesResponse: Call<Devices>

    fun getDevices(callback: NetworkCallbacks, forceFetch : Boolean): MutableLiveData<List<Device>> {
        mCallback = callback
        if (!mDeviceList.value!!.isEmpty() && !forceFetch) {
            mCallback.onNetworkConnected()
            return mDeviceList
        }
        mDevicesResponse = ApiServiceModule.getInstance().getApiService().getDevicesList()
        mDevicesResponse.enqueue(object : Callback<Devices> {

            override fun onResponse(call: Call<Devices>, response: Response<Devices>) {
                //Log.e("Error", "Reached into success response " +response.body()!!.devices.size)
                //Timber.e("onResponse %s ", response.body())
                mDeviceList.value = response.body()!!.devices
                mCallback.onNetworkConnected()
            }

            override fun onFailure(call: Call<Devices>, t: Throwable) {
                Timber.e("onFailure %s ", t.message)
                mDeviceList.value = emptyList()
                mCallback.onNetworkDisconnected(t)

            }

        })
        return mDeviceList
    }
}