package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Category
import com.example.drinkapp.databinding.ItemCategoryBinding
import com.example.drinkapp.utils.listener.OnItemCategoryClickListener

class RecyclerViewCategoryAdapter(private val listener: OnItemCategoryClickListener) :
    RecyclerView.Adapter<RecyclerViewCategoryAdapter.ViewHolder?>() {
    private val listCategory = mutableListOf<Category>()

    fun setData(listCategory : List<Category>) {

        this.listCategory.apply {
            clear()
            addAll(listCategory)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listCategory.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding, listener)
    }

    override fun getItemCount(): Int {
       return listCategory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = listCategory.get(position)
        holder.viewBinding.apply {
            textCategoryName.text = category.name
            buttonUpdate.setOnClickListener{
                listener.onItemCategoryClick(position, category)
            }

        }
    }

    inner class ViewHolder(var viewBinding: ItemCategoryBinding, listener: OnItemCategoryClickListener) :
        RecyclerView.ViewHolder(viewBinding.root) {
//        init {
//            itemView.setOnClickListener {
//                listener.onItemCategoryClick(adapterPosition, listCategory)
//            }
//        }
    }
}