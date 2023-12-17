package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.RevenueStatistics
import com.example.drinkapp.databinding.ItemStatisticsBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemStatisticClickListener

class RecyclerViewRevenueStatisticAdapter(private val itemClick: OnItemStatisticClickListener) :
    RecyclerView.Adapter<RecyclerViewRevenueStatisticAdapter.ViewHolder?>(){
    private val listStatic = mutableListOf<RevenueStatistics>()

    fun setData(list: List<RevenueStatistics>) {
        this.listStatic.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listStatic.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemStatisticsBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listStatic.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val revenueStatistics = listStatic.get(position)
        holder.viewBinding.apply {
            textName.text = revenueStatistics.name
            textQuantity.text = revenueStatistics.quantity.toString()
            textTotalPrice.text = revenueStatistics.revenue.formatAsNumber()
            root.setOnClickListener {
                itemClick.onItemClick(revenueStatistics.name)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemStatisticsBinding):
        RecyclerView.ViewHolder(viewBinding.root){

    }
}