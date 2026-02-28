package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.databinding.ItemDrinkOrderTimeBinding
import com.example.drinkapp.utils.listener.OnItemDrinkOrderClickListener

class RecyclerViewDrinkOrderAdapter(private val itemClick: OnItemDrinkOrderClickListener) :
    ListAdapter<DrinkOrders, RecyclerViewDrinkOrderAdapter.ViewHolder>(DrinkOrdersDiffCallback()) {

    fun setData(list: List<DrinkOrders>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkOrderTimeBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drinkOrders = getItem(position)
        holder.viewBinding.apply {
            textName.text = drinkOrders.name
            Glide.with(image.context)
                .load(drinkOrders.image)
                .into(image)
            textTime.text = drinkOrders.orderTime
            root.setOnClickListener {
                itemClick.onItemClick(drinkOrders.orderId)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemDrinkOrderTimeBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class DrinkOrdersDiffCallback : DiffUtil.ItemCallback<DrinkOrders>() {
        override fun areItemsTheSame(oldItem: DrinkOrders, newItem: DrinkOrders): Boolean {
            return oldItem.orderId == newItem.orderId && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DrinkOrders, newItem: DrinkOrders): Boolean {
            return oldItem == newItem
        }
    }
}