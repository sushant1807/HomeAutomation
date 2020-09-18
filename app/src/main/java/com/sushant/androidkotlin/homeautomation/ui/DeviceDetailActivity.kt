package com.sushant.androidkotlin.homeautomation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.databinding.ActivityDeviceDetailsBinding
import com.sushant.androidkotlin.homeautomation.models.Device
import kotlinx.android.synthetic.main.activity_device_details.*

class DeviceDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDeviceDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_details)

        val device = intent.getSerializableExtra("DEVICE") as? Device

        device_name.setText(device!!.deviceName)
        device_description.setText(device!!.productType)
    }
}