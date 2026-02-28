package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.Result

interface CartItemRepository {
    suspend fun addCartItem(pricesizeId: Long, userId: Long, number: Long, note: String): Result<CartItem>
    suspend fun getAllCartItemByUserID(id: Long): Result<List<CartItem>>
    suspend fun updateCartItemNumber(id: Long, number: Long): Result<CartItem>
    suspend fun updateCartItem(id: Long, number: Long, note: String): Result<CartItem>
    suspend fun checkCartItem(userId: Long, pricesizeId: Long): Result<CartItem>
    suspend fun deleteCartItemById(id: Long): Result<String>
    suspend fun deleteAll(userId: Long): Result<String>
}
