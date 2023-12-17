package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.databinding.ItemOrderShipperBinding
import com.example.drinkapp.utils.formatAsNumber
//import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemOrderShipperClickListener

class RecylerViewOrderManagerAdapter (private val itemClickListener: OnItemOrderShipperClickListener):
    RecyclerView.Adapter<RecylerViewOrderManagerAdapter.ViewHolder?>(){
    private val listOrder = mutableListOf<Order>()

    fun setData(list: List<Order>) {
        this.listOrder.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listOrder.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderShipperBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = listOrder.get(position)
        holder.viewBinding.apply{
            textMa.text = order.id.toString()
            val payment_status = order.payment.payment_status
            if(payment_status == 0L){
                textPaymentStatus.text = "Chưa thanh toán"
            }else{
                textPaymentStatus.text = "Đã thanh toán"
            }
            textPrice.text = order.total_price.formatAsNumber()

            if(order.order_status == 3L) buttonNhanDon.visibility = View.INVISIBLE
            else{
                buttonNhanDon.setText("Xác nhận")
                buttonNhanDon.visibility = View.VISIBLE
            }
            root.setOnClickListener {
                itemClickListener.onItemOrderShipperClick(order.id)
            }
            buttonNhanDon.setOnClickListener {
                itemClickListener.onItemReceiveOrderClick(order.id)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemOrderShipperBinding):
        RecyclerView.ViewHolder(viewBinding.root){

    }
}