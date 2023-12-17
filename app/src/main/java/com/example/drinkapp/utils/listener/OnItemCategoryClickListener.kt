package com.example.drinkapp.utils.listener

import com.example.drinkapp.data.model.Category

interface OnItemCategoryClickListener {
    fun onItemCategoryClick(position: Int, category: Category)
}