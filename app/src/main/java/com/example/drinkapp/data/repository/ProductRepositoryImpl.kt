package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.product.ProductDTO
import com.example.drinkapp.data.resource.retrofit.ProductApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val apiService: ProductApiService
) : ProductRepository {

    override suspend fun getAllProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllProductsCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.products)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun getProductHot(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductHotCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.products)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun getAllProductsClient(): Result<List<Product>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllProductsClientCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.products)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun getProductById(id: Long): Result<Product> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getProductByIdCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.product)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun addProduct(name: String, imageUri: String, price: Double, statusCode: Long, cateId: Long): Result<Product> = withContext(Dispatchers.IO) {
        try {
            val productDTO = ProductDTO(name, imageUri, price, statusCode, cateId)
            val response = apiService.addProductCoroutine(productDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.product)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun updateProduct(id: Long, name: String, imageUri: String, price: Double, statusCode: Long, cateId: Long): Result<Product> = withContext(Dispatchers.IO) {
        try {
            val productDTO = ProductDTO(name, imageUri, price, statusCode, cateId)
            val response = apiService.updateProductCoroutine(id, productDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.product)
                } else {
                    Result.Error(Exception(Constant.MESS_CALL_API_NOT_FOUND))
                }
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }

    override suspend fun deleteProduct(id: Long): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteProductByIdCoroutine(id)
            if (response.isSuccessful) {
                Result.Success(Constant.MESS_CALL_API_SUCCESS)
            } else {
                val errorMessage = ErrorHandler.getErrorMessage(response)
                Result.HttpError(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            val errorMessage = ErrorHandler.getNetworkErrorMessage(e)
            Result.Error(e, errorMessage)
        }
    }
}
