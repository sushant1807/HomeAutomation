package com.sushant.androidkotlin.homeautomation.repositories

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

    private var mDeviceList : MutableLiveData<Devices> = MutableLiveData<Devices>().apply {
        value = null;
    }

    //private var mUser =

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

    fun getDevices(callback: NetworkCallbacks, forceFetch : Boolean): MutableLiveData<Devices> {
        mCallback = callback
        //if (!mDeviceList.value!!.devices.isNotEmpty() && !forceFetch) {
            //mCallback.onNetworkConnected()
            //return mDeviceList
        //}
        mDevicesResponse = ApiServiceModule.getInstance().getApiService().getDevicesList()
        mDevicesResponse.enqueue(object : Callback<Devices> {

            override fun onResponse(call: Call<Devices>, response: Response<Devices>) {
                //Log.e("Error", "Reached into success response " +response.body()!!.devices.size)
                //Timber.e("onResponse %s ", response.body())
                mDeviceList.value = response.body()!!
                Timber.e("Get User details: %s ", response.body()!!.user);
                mCallback.onNetworkConnected()
            }

            override fun onFailure(call: Call<Devices>, t: Throwable) {
                Timber.e("onFailure %s ", t.message)
                mDeviceList.value = null
                mCallback.onNetworkDisconnected(t)

            }

        })
        return mDeviceList
    }
}