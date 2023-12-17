package com.example.drinkapp.utils.listener

import com.example.drinkapp.data.model.Size

interface OnItemSizeClickListener {
    fun onItemSizeClick(position: Int, size: Size)
}