package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.databinding.ItemOrderBinding
import com.example.drinkapp.utils.listener.OnItemOrderClientClickListener
import com.example.drinkapp.utils.toStatusString

class RecyclerViewOrderAdapter(private val itemClickListener: OnItemOrderClientClickListener) :
    RecyclerView.Adapter<RecyclerViewOrderAdapter.ViewHolder?>() {
    private val listOrder = mutableListOf<Order>()

    fun setData(list: List<Order>){
        this.listOrder.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData(){
        listOrder.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = listOrder.get(position)
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
        RecyclerView.ViewHolder(viewBinding.root) {

    }

}