package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.OrderItem
import com.example.drinkapp.databinding.ItemOrderDetailBinding
import com.example.drinkapp.utils.formatAsNumber

class RecyclerViewOrderDetailAdapter :
    ListAdapter<OrderItem, RecyclerViewOrderDetailAdapter.ViewHolder>(OrderItemDiffCallback()) {

    fun setData(list: List<OrderItem>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = getItem(position)
        holder.viewBinding.apply {
            textName.setText(orderItem.name)
            textSize.setText(orderItem.size)
            textPrice.setText(orderItem.price.formatAsNumber())
            textNumber.setText(orderItem.quantity.toString())
            Glide.with(buttonImage.context)
                .load(orderItem.image)
                .into(buttonImage)
            textTotalPrice.setText((orderItem.price * orderItem.quantity).formatAsNumber())
        }
    }

    inner class ViewHolder(var viewBinding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class OrderItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
        override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
            return oldItem == newItem
        }
    }
}