package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Category
import com.example.drinkapp.databinding.ItemCategoryBinding
import com.example.drinkapp.utils.listener.OnItemCategoryClickListener

class RecyclerViewCategoryAdapter(private val listener: OnItemCategoryClickListener) :
    ListAdapter<Category, RecyclerViewCategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    fun setData(listCategory: List<Category>) {
        submitList(listCategory)
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
        val category = getItem(position)
        holder.viewBinding.apply {
            textCategoryName.text = category.name
            buttonUpdate.setOnClickListener {
                listener.onItemCategoryClick(position, category)
            }
        }
    }

    inner class ViewHolder(
        var viewBinding: ItemCategoryBinding,
        listener: OnItemCategoryClickListener
    ) : RecyclerView.ViewHolder(viewBinding.root)

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}