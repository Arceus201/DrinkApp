package com.example.drinkapp.screen.admin.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkapp.R
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.databinding.ItemDrinkBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemEditDrinkClickListener


class RecyclerViewDrinkAdapter(private val itemClickListener: OnItemEditDrinkClickListener)
    : RecyclerView.Adapter<RecyclerViewDrinkAdapter.ViewHolder?>() {
    private val listProduct = mutableListOf<Product>()

    fun setData(list: List<Product>) {
        this.listProduct.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        listProduct.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkBinding.inflate(inflater, parent, false)
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

            buttonDelete.setOnClickListener {
                itemClickListener.onItemDelete(product.id)
            }
            buttonEditSize.setOnClickListener {
                itemClickListener.onItemEditSizeClick(product)
            }
            buttonEdit.setOnClickListener {
                itemClickListener.onItemEditClick(product)
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }
}