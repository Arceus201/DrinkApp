package com.example.drinkapp.screen.admin.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkapp.R
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.databinding.ItemDrinkSizeBinding
import com.example.drinkapp.utils.formatAsNumber
import com.example.drinkapp.utils.listener.OnItemPriceSizeClickListener

class RecyclerViewPriceSizeAdapter(private val itemClickListener: OnItemPriceSizeClickListener) :
    RecyclerView.Adapter<RecyclerViewPriceSizeAdapter.ViewHolder?>() {

    private val listPriceSize = mutableListOf<PriceSize>()
    fun setData(list: List<PriceSize>) {
        this.listPriceSize.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
    fun clearData() {
        listPriceSize.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemDrinkSizeBinding.inflate(inflater, parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listPriceSize.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceSize = listPriceSize.get(position)
        holder.viewBinding.apply {
            textName.text = priceSize.size.name
            textPrice.text = priceSize.price.formatAsNumber()
            if(priceSize.status == 1L) buttonShowHide.setImageResource(R.drawable.ic_current_show)
            else buttonShowHide.setImageResource(R.drawable.ic_current_hide)

            if(priceSize.size.name.equals("O")){
                buttonShowHide.visibility = View.INVISIBLE
            }else{
                buttonShowHide.setOnClickListener{
                    priceSize.status = -1*(priceSize.status)
                    itemClickListener.onItemHideShowPSClick(priceSize)
                }
            }
        }
    }

    inner class ViewHolder(var viewBinding: ItemDrinkSizeBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }
}