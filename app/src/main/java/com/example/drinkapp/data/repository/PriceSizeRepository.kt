package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.resource.Result

interface PriceSizeRepository {
    suspend fun getAllPriceSizeByProductID(id: Long): Result<List<PriceSize>>
    suspend fun getAllPriceSizeClientByProductID(id: Long): Result<List<PriceSize>>
    suspend fun addPriceSize(idProduct: Long, idSize: Long, price: Double): Result<List<PriceSize>>
    suspend fun updatePriceSizeDefault(idProduct: Long, idSize: Long, price: Double, status: Long): Result<PriceSize>
    suspend fun updatePriceSize(id: Long, idProduct: Long, idSize: Long, price: Double, status: Long): Result<List<PriceSize>>
}
