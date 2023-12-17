package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.DrinkOrders
import com.example.drinkapp.databinding.ItemDrinkOrderTimeBinding
import com.example.drinkapp.utils.listener.OnItemDrinkOrderClickListener

class RecyclerViewDrinkOrderAdapter(private val itemClick : OnItemDrinkOrderClickListener) :
    RecyclerView.Adapter<RecyclerViewDrinkOrderAdapter.ViewHolder?>(){
    private val listDinkOrder = mutableListOf<DrinkOrders>()

    fun setData(list: List<DrinkOrders>) {
        this.listDinkOrder.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listDinkOrder.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkOrderTimeBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listDinkOrder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drinkOrders = listDinkOrder.get(position)
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

    inner class ViewHolder(var viewBinding: ItemDrinkOrderTimeBinding):
        RecyclerView.ViewHolder(viewBinding.root){

    }
}