package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.databinding.ItemCartBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemCartClickListener

class RecyclerViewCartAdapter(private val itemClickListener: OnItemCartClickListener) :
    ListAdapter<CartItem, RecyclerViewCartAdapter.ViewHolder>(CartItemDiffCallback()) {

    fun setData(list: List<CartItem>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCartBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = getItem(position)
        holder.viewBinding.apply {
            textName.setText(cartItem.priceSize.product.name)
            textSize.setText(cartItem.priceSize.size.name)
            textPrice.setText(cartItem.priceSize.price.formatAsNumber())
            textNumber.setText(cartItem.number.toString())
            Glide.with(imageButton.context)
                .load(cartItem.priceSize.product.image)
                .into(imageButton)
            buttonSub.setOnClickListener {
                itemClickListener.onItemSubQuantityClick(cartItem.id, cartItem.number)
            }
            buttonAdd.setOnClickListener {
                itemClickListener.onItemAddQuantityClick(cartItem.id, cartItem.number)
            }
            buttonDelete.setOnClickListener {
                itemClickListener.onItemDeleteClick(cartItem.id)
            }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                itemClickListener.onItemChoseClick(cartItem.id, isChecked)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemCartBinding) :
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