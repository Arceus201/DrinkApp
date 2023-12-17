package com.example.drinkapp.screen.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.data.model.Size
import com.example.drinkapp.databinding.ItemCategoryBinding
import com.example.drinkapp.utils.listener.OnItemSizeClickListener

class RecyclerViewSizeAdapter (private val listener: OnItemSizeClickListener) :
    RecyclerView.Adapter<RecyclerViewSizeAdapter.ViewHolder?>() {
    private val listSize = mutableListOf<Size>()

    fun setData(listSize : List<Size>) {

        this.listSize.apply {
            clear()
            addAll(listSize)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listSize.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding, listener)
    }

    override fun getItemCount(): Int {
        return listSize.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var size = listSize.get(position)
        holder.viewBinding.apply {
            textCategoryName.text = size.name
            if(size.name.equals("O")) {
                buttonUpdate.visibility = View.INVISIBLE
            }
            buttonUpdate.setOnClickListener{
                listener.onItemSizeClick(position, size)
            }

        }
    }

    inner class ViewHolder(var viewBinding: ItemCategoryBinding, listener: OnItemSizeClickListener) :
        RecyclerView.ViewHolder(viewBinding.root) {
    }
}