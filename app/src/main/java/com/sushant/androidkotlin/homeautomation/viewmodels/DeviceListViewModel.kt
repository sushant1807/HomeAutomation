package com.sushant.androidkotlin.homeautomation.viewmodels

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushant.androidkotlin.homeautomation.interfaces.NetworkCallbacks
import com.sushant.androidkotlin.homeautomation.models.Device
import com.sushant.androidkotlin.homeautomation.repositories.DeviceRepository
import com.sushant.androidkotlin.homeautomation.utils.NetworkManager
import timber.log.Timber

class DeviceListViewModel : ViewModel() {

    private var mList: MutableLiveData<List<Device>> =
        MutableLiveData<List<Device>>().apply { value = null}
    var mShowProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowApiError: MutableLiveData<Boolean> = MutableLiveData()
    private var mRepository = DeviceRepository.getInstance()

    fun getDeviceList() = mList

    fun fetchDevicesFromServer(context: Context, forceFetch : Boolean): MutableLiveData<List<Device>> {
        if (NetworkManager.isOnline(context)) {
            Timber.e("fetchDevicesFromServer if ")
            mShowProgressBar.value = true
            mList = mRepository.getDevices(object : NetworkCallbacks {
                override fun onNetworkDisconnected(th: Throwable) {
                    Timber.e("fetchDevicesFromServer disconnected ")
                    mShowApiError.value = true
                }

                override fun onNetworkConnected() {
                    Timber.e("fetchDevicesFromServer connected ")
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
            Timber.e("fetchDevicesFromServer else ")
        }
        return mList
    }

    fun onRefreshClicked(view : View){
        fetchDevicesFromServer(view.context, true)
    }
}