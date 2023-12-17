package com.example.drinkapp.utils.listener

import com.example.drinkapp.data.model.CartItem
import java.text.FieldPosition

interface OnItemCartClickListener {
    fun onItemSubQuantityClick(id:Long, number: Long)
    fun onItemAddQuantityClick(id: Long, number: Long)
    fun onItemDeleteClick(id: Long)
    fun onItemChoseClick(id: Long,isChecked: Boolean)
}