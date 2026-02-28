package com.example.drinkapp.data.resource.retrofit

import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO
import com.example.drinkapp.data.resource.response.order.OrderListReponse
import com.example.drinkapp.data.resource.response.order.OrderReponse
import com.example.drinkapp.utils.ApiConstant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApiService {
    @POST(ApiConstant.API_KEY_ORDER_ADD)
    fun addOrder(@Body orderDTO: OrderDTO): Call<OrderReponse>

    @PUT(ApiConstant.API_KEY_ORDER_UPDATE_SHIPPER)
    fun updateShippingOrder(@Path("id") id: Long ,@Body orderShipperConfirmDTO: OrderShipperConfirmDTO ): Call<OrderReponse>
    
    @PUT(ApiConstant.API_KEY_ORDER_UPDATE_STATUS)
    fun updateOrderStatus(@Path("id") id: Long ,@Body orderStatusDTO: OrderStatusDTO ): Call<OrderReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET)
    fun getOrder(@Path("id") id: Long): Call<OrderReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_BY_USER_ID)
    fun getOrderByUserId(@Path("user_id") user_id: Long): Call<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_HISTORY_BY_USER_ID)
    fun getHistoryOrderByUserId(@Path("user_id") user_id: Long): Call<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_ALL_BY_USER_IN_USERMANAGER)
    fun getAllOrderByUserInUserManager(@Path("user_id") user_id: Long): Call<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_ALL_BY_STATUS)
    fun getAllOrderByStatus(@Path("order_status") order_status: Long): Call<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_BY_SHIPPER_ID)
    fun getOrderByShipperId(@Path("id") id: Long): Call<OrderReponse>

    // Coroutine versions
    @POST(ApiConstant.API_KEY_ORDER_ADD)
    suspend fun addOrderCoroutine(@Body orderDTO: OrderDTO): Response<OrderReponse>

    @PUT(ApiConstant.API_KEY_ORDER_UPDATE_SHIPPER)
    suspend fun updateShippingOrderCoroutine(@Path("id") id: Long ,@Body orderShipperConfirmDTO: OrderShipperConfirmDTO ): Response<OrderReponse>
    
    @PUT(ApiConstant.API_KEY_ORDER_UPDATE_STATUS)
    suspend fun updateOrderStatusCoroutine(@Path("id") id: Long ,@Body orderStatusDTO: OrderStatusDTO ): Response<OrderReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET)
    suspend fun getOrderCoroutine(@Path("id") id: Long): Response<OrderReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_BY_USER_ID)
    suspend fun getOrderByUserIdCoroutine(@Path("user_id") user_id: Long): Response<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_HISTORY_BY_USER_ID)
    suspend fun getHistoryOrderByUserIdCoroutine(@Path("user_id") user_id: Long): Response<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_ALL_BY_USER_IN_USERMANAGER)
    suspend fun getAllOrderByUserInUserManagerCoroutine(@Path("user_id") user_id: Long): Response<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_ALL_BY_STATUS)
    suspend fun getAllOrderByStatusCoroutine(@Path("order_status") order_status: Long): Response<OrderListReponse>

    @GET(ApiConstant.API_KEY_ORDER_GET_BY_SHIPPER_ID)
    suspend fun getOrderByShipperIdCoroutine(@Path("id") id: Long): Response<OrderReponse>
}