package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.cartitem.CartItemDTO
import com.example.drinkapp.data.resource.dto.cartitem.CartItemUpateDTO
import com.example.drinkapp.data.resource.retrofit.CartItemApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartItemRepositoryImpl(
    private val apiService: CartItemApiService
) : CartItemRepository {

    override suspend fun addCartItem(pricesizeId: Long, userId: Long, number: Long, note: String): Result<CartItem> = withContext(Dispatchers.IO) {
        try {
            val cartItemDTO = CartItemDTO(pricesizeId, userId, number, note)
            val response = apiService.addCartItemCoroutine(cartItemDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.cartitem)
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

    override suspend fun getAllCartItemByUserID(id: Long): Result<List<CartItem>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllCartItemByUserIDCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.cartitems.isNotEmpty()) {
                    Result.Success(body.cartitems)
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

    override suspend fun updateCartItemNumber(id: Long, number: Long): Result<CartItem> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateCartItemNumberCoroutine(id, number)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.cartitem)
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

    override suspend fun updateCartItem(id: Long, number: Long, note: String): Result<CartItem> = withContext(Dispatchers.IO) {
        try {
            val cartItemUpdateDTO = CartItemUpateDTO(number, note)
            val response = apiService.updateCartItemCoroutine(id, cartItemUpdateDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.cartitem)
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

    override suspend fun checkCartItem(userId: Long, pricesizeId: Long): Result<CartItem> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.checkCartItemCoroutine(userId, pricesizeId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.cartitem)
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

    override suspend fun deleteCartItemById(id: Long): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteCartItemByIdCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.message)
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

    override suspend fun deleteAll(userId: Long): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteAllCoroutine(userId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.message)
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
