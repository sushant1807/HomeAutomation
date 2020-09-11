package com.sushant.androidkotlin.homeautomation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sushant.androidkotlin.homeautomation.R
import com.sushant.androidkotlin.homeautomation.databinding.DeviceListItemBinding
import com.sushant.androidkotlin.homeautomation.models.Device

class DevicesAdapter (var mList: List<Device>? = listOf()) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {

    fun setData(list: List<Device>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevicesAdapter.ViewHolder {
        val binding: DeviceListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.device_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: DevicesAdapter.ViewHolder, position: Int) {
        holder.itemBinding.device = mList!!.get(position)
    }

    class ViewHolder(var itemBinding: DeviceListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }
}