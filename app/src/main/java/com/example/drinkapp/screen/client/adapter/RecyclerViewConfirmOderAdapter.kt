package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.databinding.ItemOrderDetailBinding
import com.example.drinkapp.utils.formatAsNumber

class RecyclerViewConfirmOderAdapter:
    RecyclerView.Adapter<RecyclerViewConfirmOderAdapter.ViewHolder?>(){
    private val listCartItem = mutableListOf<CartItem>()

    fun setData(list: List<CartItem>) {
        this.listCartItem.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listCartItem.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemOrderDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listCartItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = listCartItem.get(position)
        holder.viewBinding.apply {
            textName.setText(cartItem.priceSize.product.name)
            textSize.setText(cartItem.priceSize.size.name)
            textPrice.setText(cartItem.priceSize.price.formatAsNumber())
            textNumber.setText(cartItem.number.toString())
            Glide.with(buttonImage.context)
                .load(cartItem.priceSize.product.image)
                .into(buttonImage)
            textTotalPrice.setText((cartItem.priceSize.price * cartItem.number).formatAsNumber())
        }
    }

    inner class ViewHolder(var viewBinding: ItemOrderDetailBinding ):
        RecyclerView.ViewHolder(viewBinding.root){

    }
}