package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.databinding.ItemCategoryBinding
import com.example.drinkapp.utils.listener.OnItemSizeClickListener

class RecyclerViewSizeAdapter(private val listener: OnItemSizeClickListener) :
    ListAdapter<Size, RecyclerViewSizeAdapter.ViewHolder>(SizeDiffCallback()) {

    fun setData(listSize: List<Size>) {
        submitList(listSize)
    }

    fun clearData() {
        submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val size = getItem(position)
        holder.viewBinding.apply {
            textCategoryName.text = size.name
            if (size.name.equals("O")) {
                buttonUpdate.visibility = View.INVISIBLE
            } else {
                buttonUpdate.visibility = View.VISIBLE
            }
            buttonUpdate.setOnClickListener {
                listener.onItemSizeClick(position, size)
            }
        }
    }

    inner class ViewHolder(
        var viewBinding: ItemCategoryBinding,
        listener: OnItemSizeClickListener
    ) : RecyclerView.ViewHolder(viewBinding.root)

    private class SizeDiffCallback : DiffUtil.ItemCallback<Size>() {
        override fun areItemsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem == newItem
        }
    }
}