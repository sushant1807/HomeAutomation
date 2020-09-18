package com.sushant.androidkotlin.homeautomation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.models.Device
import kotlinx.android.synthetic.main.device_list_item.view.*

class DeviceListAdapter(var items : List<Device>, var clickListner: OnDeviceItemClickListner) : RecyclerView.Adapter<DeviceViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        lateinit var viewHolder : DeviceViewHolder
        viewHolder = DeviceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.device_list_item,parent,false ))
        return viewHolder
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.initialize(items.get(position),clickListner)

    }

    fun setData(list: List<Device>) {
        items = list
        notifyDataSetChanged()
    }
}

class DeviceViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
    private var titleTv = itemView.titleTv
    private var descriptionTv = itemView.descriptionTv

    fun initialize(item: Device, action:OnDeviceItemClickListner){
        titleTv.text = item.deviceName
        descriptionTv.text = item.productType

        itemView.setOnClickListener{
            action.onItemClick(item,adapterPosition)
        }
    }
}

interface OnDeviceItemClickListner{
    fun onItemClick(item: Device, position: Int)
}