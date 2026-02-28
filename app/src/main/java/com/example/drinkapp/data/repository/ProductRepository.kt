package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.Result

interface ProductRepository {
    suspend fun getAllProducts(): Result<List<Product>>
    suspend fun getProductHot(): Result<List<Product>>
    suspend fun getAllProductsClient(): Result<List<Product>>
    suspend fun getProductById(id: Long): Result<Product>
    suspend fun addProduct(name: String, imageUri: String, price: Double, statusCode: Long, cateId: Long): Result<Product>
    suspend fun updateProduct(id: Long, name: String, imageUri: String, price: Double, statusCode: Long, cateId: Long): Result<Product>
    suspend fun deleteProduct(id: Long): Result<String>
}
