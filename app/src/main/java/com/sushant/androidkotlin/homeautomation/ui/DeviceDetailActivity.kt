package com.sushant.androidkotlin.homeautomation.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.database.HomeAutomationDatabase
import com.sushant.androidkotlin.homeautomation.databinding.ActivityDeviceDetailsBinding
import com.sushant.androidkotlin.homeautomation.models.Device
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_device_details.*
import timber.log.Timber


class DeviceDetailActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    lateinit var binding : ActivityDeviceDetailsBinding
    private lateinit var thumbView: View
    private var isSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_details)

        val device = intent.getSerializableExtra("DEVICE") as? Device

        device_name.setText(device!!.deviceName)
        device_description.setText(device!!.productType)

        val seekbar = SeekBar(this)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(30, 30, 30, 30)
        seekbar.layoutParams = layoutParams

        thumbView = LayoutInflater.from(this)
            .inflate(R.layout.layout_seekbar_thumb, null, false)
        val sk = findViewById<SeekBar>(R.id.seekBar)
        sk.thumb = getThumb(0)

        initInstance(device)
    }

    private fun initInstance(device: Device) {

        when (device.productType) {
            "RollerShutter" -> {
                position_layout.visibility = View.VISIBLE
                vertical_text.setText(R.string.text_position)
                seekBar.setProgress(device.position)
                val value = device.position
                seekBar_value.setText("$value")

            }
            "Heater" -> {
                mode_layout.visibility = View.VISIBLE
                position_layout.visibility = View.VISIBLE
                vertical_text.setText(R.string.text_temperature)
                seekBar.setProgress(device.temperature)
                val value = device.temperature
                seekBar_value.setText("$value")
                if (device.mode == "ON") {
                    mySwitch.isChecked = true
                }
            }
            else -> {
                mode_layout.visibility = View.VISIBLE
                position_layout.visibility = View.VISIBLE
                vertical_text.setText(R.string.text_intensity)
                seekBar.setProgress(device.intensity)
                val value = device.intensity
                seekBar_value.setText("$value")
                if (device.mode == "ON") {
                    mySwitch.isChecked = true
                }
            }
        }

        mySwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            // do something, the isChecked will be
            // true if the switch is in the On position
            isSelected = isChecked
            Timber.e("Updated into mySwitch %s : %s ", isChecked, isSelected)

        })

        seekBar.setOnSeekBarChangeListener(this)

        accept_btn.setOnClickListener {

            val db = HomeAutomationDatabase.getInstance(applicationContext)
            when (device.productType) {
                "RollerShutter" -> {
                    val value = seekBar_value.text.toString().toInt()
                    val deviceObject = Device(device.id, device.deviceName, device.intensity,
                        device.mode, device.productType, value , device.temperature)

                    Completable.fromRunnable {
                        db.devicesDao.updateDevice(deviceObject)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe ( {
                            Timber.e("Updated into RollerShutter")
                            afterSafeUpdatedIntoDeviceTableLocally()
                        }, { throwable ->
                            Timber.e("Inserted into RollerShutter failure %s ", throwable.message)
                        })
                }
                "Heater" -> {
                    val value = seekBar_value.text.toString().toInt()
                    var modeValue = ""
                    modeValue = if (isSelected) {
                        "ON"
                    } else {
                        "OFF"
                    }
                    val deviceObject = Device(device.id, device.deviceName, device.intensity,
                        modeValue, device.productType, device.position ,value)

                    Completable.fromRunnable {
                        db.devicesDao.updateDevice(deviceObject)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe ( {
                            Timber.e("Updated into Heater")
                            afterSafeUpdatedIntoDeviceTableLocally()
                        }, { throwable ->
                            Timber.e("Inserted into Heater failure %s ", throwable.message)
                        })
                }
                else -> {
                    val value = seekBar_value.text.toString().toInt()
                    var modeValue = ""
                    modeValue = if (isSelected) {
                        "ON"
                    } else {
                        "OFF"
                    }
                    val deviceObject = Device(device.id, device.deviceName, value,
                        modeValue, device.productType, device.position ,device.temperature)
                    Timber.e("Updated into Light %s" , deviceObject.toString())
                    Completable.fromRunnable {
                        db.devicesDao.updateDevice(deviceObject)
                    }
                        .subscribeOn(Schedulers.io())
                        .subscribe ( {
                            Timber.e("Updated into Light")
                            afterSafeUpdatedIntoDeviceTableLocally()
                        }, { throwable ->
                            Timber.e("Inserted into Light failure %s ", throwable.message)
                        })
                }
            }
        }

        cancel__btn.setOnClickListener(View.OnClickListener {
            //Toast.makeText(applicationContext, "Cancel here", Toast.LENGTH_LONG).show()
            onBackPressed()
        })

    }

    private fun afterSafeUpdatedIntoDeviceTableLocally() {
        val intent = Intent(application, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getThumb(progress: Int): Drawable? {
        (thumbView.findViewById(R.id.tvProgress) as TextView).text = progress.toString() + ""
        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(thumbView.measuredWidth, thumbView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        thumbView.layout(0, 0, thumbView.measuredWidth, thumbView.measuredHeight)
        thumbView.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

    override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
        //Toast.makeText(applicationContext, "Seekbar value: $progress " , Toast.LENGTH_LONG).show()
        seekBar.thumb = getThumb(progress)
        seekBar_value.text = "$progress"
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}