package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.databinding.ItemOrderDetailBinding
import com.example.drinkapp.utils.formatAsNumber

class RecyclerViewConfirmOderAdapter :
    ListAdapter<CartItem, RecyclerViewConfirmOderAdapter.ViewHolder>(CartItemDiffCallback()) {

    fun setData(list: List<CartItem>) {
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
        val cartItem = getItem(position)
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

    inner class ViewHolder(var viewBinding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}