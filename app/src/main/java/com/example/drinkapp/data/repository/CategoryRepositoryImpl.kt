package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.category.CategoryDTO
import com.example.drinkapp.data.resource.retrofit.CategoryApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepositoryImpl(
    private val apiService: CategoryApiService
) : CategoryRepository {

    override suspend fun getAllCategories(): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllCoroutine()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.categories.isNotEmpty()) {
                    Result.Success(body.categories)
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

    override suspend fun addCategory(name: String): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val categoryDTO = CategoryDTO(name)
            val response = apiService.addCategoryCoroutine(categoryDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.categories.isNotEmpty()) {
                    Result.Success(body.categories)
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

    override suspend fun updateCategory(id: Long?, category: Category): Result<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateCategoryCoroutine(id, category)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.categories.isNotEmpty()) {
                    Result.Success(body.categories)
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
}
