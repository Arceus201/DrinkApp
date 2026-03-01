package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.cartitem.CartItemDTO
import com.example.drinkapp.data.resource.dto.cartitem.CartItemUpateDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.CartItemApiService
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val apiService: CartItemApiService
) : BaseRepository(), CartItemRepository {

    override suspend fun addCartItem(pricesizeId: Long, userId: Long, number: Long, note: String): Result<CartItem> =
        safeApiCall { apiService.addCartItemCoroutine(CartItemDTO(pricesizeId, userId, number, note)) }
            .map { it.cartitem }

    override suspend fun getAllCartItemByUserID(id: Long): Result<List<CartItem>> =
        safeApiCall { apiService.getAllCartItemByUserIDCoroutine(id) }
            .map { it.cartitems }

    override suspend fun updateCartItemNumber(id: Long, number: Long): Result<CartItem> =
        safeApiCall { apiService.updateCartItemNumberCoroutine(id, number) }
            .map { it.cartitem }

    override suspend fun updateCartItem(id: Long, number: Long, note: String): Result<CartItem> =
        safeApiCall { apiService.updateCartItemCoroutine(id, CartItemUpateDTO(number, note)) }
            .map { it.cartitem }

    override suspend fun checkCartItem(userId: Long, pricesizeId: Long): Result<CartItem> =
        safeApiCall { apiService.checkCartItemCoroutine(userId, pricesizeId) }
            .map { it.cartitem }

    override suspend fun deleteCartItemById(id: Long): Result<String> =
        safeApiCall { apiService.deleteCartItemByIdCoroutine(id) }
            .map { it.message }

    override suspend fun deleteAll(userId: Long): Result<String> =
        safeApiCall { apiService.deleteAllCoroutine(userId) }
            .map { it.message }
}
