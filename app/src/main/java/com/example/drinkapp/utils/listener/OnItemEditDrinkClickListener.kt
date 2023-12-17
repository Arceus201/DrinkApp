package com.example.drinkapp.utils.listener

import com.example.drinkapp.data.model.Product

interface OnItemEditDrinkClickListener {
    fun onItemDelete(id: Long)
    fun onItemEditSizeClick(product: Product)
    fun onItemEditClick(product: Product)
}