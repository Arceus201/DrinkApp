package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.databinding.ItemDrinkClientBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemDrinkClientClickListener

class RecyclerViewDrinkClientAdapter(private val itemClickListener: OnItemDrinkClientClickListener) :
    ListAdapter<Product, RecyclerViewDrinkClientAdapter.ViewHolder>(ProductDiffCallback()) {

    fun setData(list: List<Product>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkClientBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.viewBinding.apply {
            textName.text = product.name
            textSold.text = product.quantitysold.toString()
            textPrice.text = product.price.formatAsNumber()
            Glide.with(imageDrink.context)
                .load(product.image)
                .into(imageDrink)
            root.setOnClickListener {
                itemClickListener.onItemDrinkClientClick(product.id)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemDrinkClientBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}