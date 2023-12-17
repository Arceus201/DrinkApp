package com.example.drinkapp.screen.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.databinding.ItemDrinkClientBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemDrinkClientClickListener

class RecyclerViewDrinkClientAdapter(private val itemClickListener: OnItemDrinkClientClickListener) :
    RecyclerView.Adapter<RecyclerViewDrinkClientAdapter.ViewHolder?>() {
    private val listProduct = mutableListOf<Product>()

    fun setData(list: List<Product>) {
        this.listProduct.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listProduct.clear()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkClientBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProduct.get(position)
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

    inner class ViewHolder (var viewBinding: ItemDrinkClientBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }
}