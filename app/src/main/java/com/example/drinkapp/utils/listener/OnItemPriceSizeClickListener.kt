package com.example.drinkapp.utils.listener

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product

interface OnItemPriceSizeClickListener {
    fun onItemHideShowPSClick(priceSize: PriceSize)
//    fun onItemEditPSClick(priceSize: PriceSize)
}