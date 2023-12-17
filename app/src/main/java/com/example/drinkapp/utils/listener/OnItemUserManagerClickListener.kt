package com.example.drinkapp.utils.listener

interface OnItemUserManagerClickListener {
    fun onItemClick(id: Long)
    fun onItemStatusClick(id: Long, current_status: Long)
}