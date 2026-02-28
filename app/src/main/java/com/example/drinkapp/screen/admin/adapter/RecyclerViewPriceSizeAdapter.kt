package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.databinding.ItemDrinkSizeBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemPriceSizeClickListener

class RecyclerViewPriceSizeAdapter(private val itemClickListener: OnItemPriceSizeClickListener) :
    ListAdapter<PriceSize, RecyclerViewPriceSizeAdapter.ViewHolder>(PriceSizeDiffCallback()) {

    fun setData(list: List<PriceSize>) {
        submitList(list)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkSizeBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceSize = getItem(position)
        holder.viewBinding.apply {
            textName.text = priceSize.size.name
            textPrice.text = priceSize.price.formatAsNumber()
            if (priceSize.status == 1L) {
                buttonShowHide.setImageResource(R.drawable.ic_current_show)
            } else {
                buttonShowHide.setImageResource(R.drawable.ic_current_hide)
            }

            if (priceSize.size.name.equals("O")) {
                buttonShowHide.visibility = View.INVISIBLE
            } else {
                buttonShowHide.visibility = View.VISIBLE
                buttonShowHide.setOnClickListener {
                    priceSize.status = -1 * (priceSize.status)
                    itemClickListener.onItemHideShowPSClick(priceSize)
                }
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemDrinkSizeBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    private class PriceSizeDiffCallback : DiffUtil.ItemCallback<PriceSize>() {
        override fun areItemsTheSame(oldItem: PriceSize, newItem: PriceSize): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PriceSize, newItem: PriceSize): Boolean {
            return oldItem == newItem
        }
    }
}