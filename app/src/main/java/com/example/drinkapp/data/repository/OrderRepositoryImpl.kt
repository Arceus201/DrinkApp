package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO
import com.example.drinkapp.data.resource.retrofit.OrderApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepositoryImpl(
    private val apiService: OrderApiService
) : OrderRepository {

    override suspend fun addOrder(orderDTO: OrderDTO): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.addOrderCoroutine(orderDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.order)
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

    override suspend fun getOrder(id: Long): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getOrderCoroutine(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.order)
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

    override suspend fun getOrderByUserId(userId: Long): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getOrderByUserIdCoroutine(userId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.order.isNotEmpty()) {
                    Result.Success(body.order)
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

    override suspend fun getAllOrderByUserInUserManager(userId: Long): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllOrderByUserInUserManagerCoroutine(userId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.order.isNotEmpty()) {
                    Result.Success(body.order)
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

    override suspend fun getHistoryOrderByUserId(userId: Long): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getHistoryOrderByUserIdCoroutine(userId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.order.isNotEmpty()) {
                    Result.Success(body.order)
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

    override suspend fun getAllOrderByStatus(status: Long): Result<List<Order>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllOrderByStatusCoroutine(status)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.order.isNotEmpty()) {
                    Result.Success(body.order)
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

    override suspend fun getOrderByShipperId(shipperId: Long): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getOrderByShipperIdCoroutine(shipperId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.order)
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

    override suspend fun updateShippingOrder(orderId: Long, orderShipperConfirmDTO: OrderShipperConfirmDTO): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateShippingOrderCoroutine(orderId, orderShipperConfirmDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.order)
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

    override suspend fun updateOrderStatus(orderId: Long, orderStatusDTO: OrderStatusDTO): Result<Order> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.updateOrderStatusCoroutine(orderId, orderStatusDTO)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body.order)
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
