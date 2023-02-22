package com.ibm.rides.ui.fragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibm.rides.databinding.ItemRideBinding
import com.ibm.rides.remote.dto.VehicleModelItem


class VehicleListAdapter(
    private val itemPressed: (item: VehicleModelItem) -> Unit
) :
    ListAdapter<VehicleModelItem, VehicleListAdapter.ViewHolder>(DiffUtilCallBacks()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }


    inner class ViewHolder(
        private val binding: ItemRideBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myItem: VehicleModelItem, position: Int) {

            binding.apply {
                tvModel.text = myItem.make_and_model
                tvVin.text = myItem.vin

                root.setOnClickListener {
                    itemPressed.invoke(myItem)
                }
            }

        }
    }

    class DiffUtilCallBacks : DiffUtil.ItemCallback<VehicleModelItem>() {
        override fun areItemsTheSame(oldItem: VehicleModelItem, newItem: VehicleModelItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: VehicleModelItem, newItem: VehicleModelItem) =
            oldItem == newItem
    }
}
