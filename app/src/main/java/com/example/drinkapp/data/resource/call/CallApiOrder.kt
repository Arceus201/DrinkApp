package com.example.drinkapp.data.resource.call

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.order.OrderShipperConfirmDTO
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO
import com.example.drinkapp.data.resource.response.address.AddressReponse
import com.example.drinkapp.data.resource.response.order.OrderListReponse
import com.example.drinkapp.data.resource.response.order.OrderReponse
import com.example.drinkapp.data.resource.retrofit.OrderApiService
import com.example.drinkapp.utils.Constant
import com.example.drinkapp.utils.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallApiOrder {
    private val orderApiService: OrderApiService by lazy {
        RetrofitHelper.getInstance().create(OrderApiService::class.java)
    }

    fun addOrder(orderDTO: OrderDTO, listen: OnResultListener<Order>){
        val call = orderApiService.addOrder(orderDTO)
        callApi(call,listen)
    }
    fun getOrder(id: Long, listen: OnResultListener<Order>){
        val call = orderApiService.getOrder(id)
        callApi(call,listen)
    }
    fun getOrderByUserId(user_id: Long, listen: OnResultListener<List<Order>>){
        val call = orderApiService.getOrderByUserId(user_id)
        callApiList(call, listen)
    }

    fun getAllOrderByUserInUserManager(user_id: Long, listen: OnResultListener<List<Order>>){
        val call = orderApiService.getAllOrderByUserInUserManager(user_id)
        callApiList(call, listen)
    }
    fun getHistoryOrderByUserId(user_id: Long, listen: OnResultListener<List<Order>>){
        val call = orderApiService.getHistoryOrderByUserId(user_id)
        callApiList(call, listen)
    }
    fun getAllOrderByStatus(status: Long, listen: OnResultListener<List<Order>>){
        val call = orderApiService.getAllOrderByStatus(status)
        callApiList(call,listen)
    }
    fun getOrderByShipperId(shipper_id: Long, listen: OnResultListener<Order>){
        val call = orderApiService.getOrderByShipperId(shipper_id)
        callApi(call,listen)
    }
    fun updateShippingOrder( order_id: Long, orderShipperConfirmDTO: OrderShipperConfirmDTO,listen: OnResultListener<Order>){
        val call = orderApiService.updateShippingOrder(order_id, orderShipperConfirmDTO)
        callApi(call,listen)
    }
    fun updateOrderStatus(order_id: Long, orderStatusDTO: OrderStatusDTO, listen: OnResultListener<Order>){
        val call = orderApiService.updateOrderStatus(order_id, orderStatusDTO)
        callApi(call,listen)
    }




    private fun callApi(call: Call<OrderReponse>, listen: OnResultListener<Order>) {
        call.enqueue(object : Callback<OrderReponse> {
            override fun onResponse(
                call: Call<OrderReponse>,
                response: Response<OrderReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.order)
                    } else {
                        listen.onFail("Order not found")
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<OrderReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    private fun callApiList(call: Call<OrderListReponse>, listen: OnResultListener<List<Order>>) {
        call.enqueue(object : Callback<OrderListReponse> {
            override fun onResponse(
                call: Call<OrderListReponse>,
                response: Response<OrderListReponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        listen.onSuccess(result.order)
                    } else {
                        listen.onFail(Constant.MESS_CALL_API_NOT_FOUND)
                    }
                } else {
                    listen.onFail(Constant.MESS_CALL_API_FAIL)
                }
            }

            override fun onFailure(call: Call<OrderListReponse>, t: Throwable) {
                val errorResponse = t.message ?: "Network error"
                listen.onFail(errorResponse)
            }
        })
    }

    companion object {
        private var instance: CallApiOrder? = null
        fun getInstance() = synchronized(this) {
            instance ?: CallApiOrder().also { instance = it }
        }
    }
}