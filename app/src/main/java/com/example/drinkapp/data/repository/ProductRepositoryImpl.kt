package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.product.ProductDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.ProductApiService
import com.example.drinkapp.utils.Constant
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ProductApiService
) : BaseRepository(), ProductRepository {

    override suspend fun getAllProducts(): Result<List<Product>> =
        safeApiCall { apiService.getAllProductsCoroutine() }
            .map { it.products }

    override suspend fun getProductHot(): Result<List<Product>> =
        safeApiCall { apiService.getProductHotCoroutine() }
            .map { it.products }

    override suspend fun getAllProductsClient(): Result<List<Product>> =
        safeApiCall { apiService.getAllProductsClientCoroutine() }
            .map { it.products }

    override suspend fun getProductById(id: Long?): Result<Product> =
        safeApiCall { apiService.getProductByIdCoroutine(id ?: 0) }
            .map { it.product }

    override suspend fun addProduct(product: Product): Result<Product> {
        val productDTO = ProductDTO(
            product.name,
            product.image,
            product.price,
            product.status,
            product.category.id
        )
        return safeApiCall { apiService.addProductCoroutine(productDTO) }
            .map { it.product }
    }

    override suspend fun updateProduct(id: Long?, product: Product): Result<Product> {
        val productDTO = ProductDTO(
            product.name,
            product.image,
            product.price,
            product.status,
            product.category.id
        )
        return safeApiCall { apiService.updateProductCoroutine(id, productDTO) }
            .map { it.product }
    }

    override suspend fun deleteProduct(id: Long?): Result<Boolean> =
        safeApiCall { apiService.deleteProductByIdCoroutine(id ?: 0) }
            .map { true }
}
