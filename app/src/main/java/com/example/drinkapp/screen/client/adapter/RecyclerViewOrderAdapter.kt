package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.databinding.ItemOrderBinding
import com.example.drinkapp.utils.listener.OnItemOrderClientClickListener
import com.example.drinkapp.utils.toStatusString

class RecyclerViewOrderAdapter(private val itemClickListener: OnItemOrderClientClickListener) :
    ListAdapter<Order, RecyclerViewOrderAdapter.ViewHolder>(OrderDiffCallback()) {

    fun setData(list: List<Order>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = getItem(position)
        val status = order.order_status

        holder.viewBinding.apply {
            textMa.text = order.id.toString()
            textStatus.text = status.toStatusString()
            root.setOnClickListener {
                itemClickListener.onItemOrderClientClick(order.id)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }
}