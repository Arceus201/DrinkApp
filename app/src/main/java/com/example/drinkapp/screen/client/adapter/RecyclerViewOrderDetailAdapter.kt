package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.OrderItem
import com.example.drinkapp.databinding.ItemOrderDetailBinding
import com.example.drinkapp.utils.formatAsNumber

class RecyclerViewOrderDetailAdapter :
    RecyclerView.Adapter<RecyclerViewOrderDetailAdapter.ViewHolder?>(){
    private val listOrderItem = mutableListOf<OrderItem>()

    fun setData(list: List<OrderItem>) {
        this.listOrderItem.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listOrderItem.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listOrderItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = listOrderItem.get(position)
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

    inner class ViewHolder(var viewBinding: ItemOrderDetailBinding):
        RecyclerView.ViewHolder(viewBinding.root){

    }
}