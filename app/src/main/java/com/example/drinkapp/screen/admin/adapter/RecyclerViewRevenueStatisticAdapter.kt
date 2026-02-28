package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.databinding.ItemStatisticsBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemStatisticClickListener

class RecyclerViewRevenueStatisticAdapter(private val itemClick: OnItemStatisticClickListener) :
    ListAdapter<RevenueStatistics, RecyclerViewRevenueStatisticAdapter.ViewHolder>(RevenueStatisticsDiffCallback()) {

    fun setData(list: List<RevenueStatistics>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemStatisticsBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val revenueStatistics = getItem(position)
        holder.viewBinding.apply {
            textName.text = revenueStatistics.name
            textQuantity.text = revenueStatistics.quantity.toString()
            textTotalPrice.text = revenueStatistics.revenue.formatAsNumber()
            root.setOnClickListener {
                itemClick.onItemClick(revenueStatistics.name)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemStatisticsBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class RevenueStatisticsDiffCallback : DiffUtil.ItemCallback<RevenueStatistics>() {
        override fun areItemsTheSame(oldItem: RevenueStatistics, newItem: RevenueStatistics): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: RevenueStatistics, newItem: RevenueStatistics): Boolean {
            return oldItem == newItem
        }
    }
}