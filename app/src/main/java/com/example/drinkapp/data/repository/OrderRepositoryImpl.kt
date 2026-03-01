package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.repository.base.BaseRepository
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO
import com.example.drinkapp.data.resource.map
import com.example.drinkapp.data.resource.retrofit.OrderApiService
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: OrderApiService
) : BaseRepository(), OrderRepository {

    override suspend fun addOrder(orderDTO: OrderDTO): Result<Order> =
        safeApiCall { apiService.addOrderCoroutine(orderDTO) }
            .map { it.order }

    override suspend fun getOrder(id: Long): Result<Order> =
        safeApiCall { apiService.getOrderCoroutine(id) }
            .map { it.order }

    override suspend fun getOrderByUserId(userId: Long): Result<List<Order>> =
        safeApiCall { apiService.getOrderByUserIdCoroutine(userId) }
            .map { it.order }

    override suspend fun getAllOrderByUserInUserManager(userId: Long): Result<List<Order>> =
        safeApiCall { apiService.getAllOrderByUserInUserManagerCoroutine(userId) }
            .map { it.order }

    override suspend fun getHistoryOrderByUserId(userId: Long): Result<List<Order>> =
        safeApiCall { apiService.getHistoryOrderByUserIdCoroutine(userId) }
            .map { it.order }

    override suspend fun getAllOrderByStatus(status: Long): Result<List<Order>> =
        safeApiCall { apiService.getAllOrderByStatusCoroutine(status) }
            .map { it.order }

    override suspend fun getOrderByShipperId(shipperId: Long): Result<Order> =
        safeApiCall { apiService.getOrderByShipperIdCoroutine(shipperId) }
            .map { it.order }

    override suspend fun updateShippingOrder(orderId: Long, orderShipperConfirmDTO: OrderShipperConfirmDTO): Result<Order> =
        safeApiCall { apiService.updateShippingOrderCoroutine(orderId, orderShipperConfirmDTO) }
            .map { it.order }

    override suspend fun updateOrderStatus(orderId: Long, orderStatusDTO: OrderStatusDTO): Result<Order> =
        safeApiCall { apiService.updateOrderStatusCoroutine(orderId, orderStatusDTO) }
            .map { it.order }
}
