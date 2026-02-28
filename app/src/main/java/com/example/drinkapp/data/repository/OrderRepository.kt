package com.example.drinkapp.data.repository

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.Result
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO

interface OrderRepository {
    suspend fun addOrder(orderDTO: OrderDTO): Result<Order>
    suspend fun getOrder(id: Long): Result<Order>
    suspend fun getOrderByUserId(userId: Long): Result<List<Order>>
    suspend fun getAllOrderByUserInUserManager(userId: Long): Result<List<Order>>
    suspend fun getHistoryOrderByUserId(userId: Long): Result<List<Order>>
    suspend fun getAllOrderByStatus(status: Long): Result<List<Order>>
    suspend fun getOrderByShipperId(shipperId: Long): Result<Order>
    suspend fun updateShippingOrder(orderId: Long, orderShipperConfirmDTO: OrderShipperConfirmDTO): Result<Order>
    suspend fun updateOrderStatus(orderId: Long, orderStatusDTO: OrderStatusDTO): Result<Order>
}
