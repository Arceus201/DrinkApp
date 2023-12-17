package com.example.drinkapp.screen.admin.order_manager

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO

class OrderManagerPresenter(private val view: OrderManagerContract.View,
private val callApi: CallApiOrder): OrderManagerContract.Presenter {
    override fun getListOrder(order_status: Long) {
        callApi.getAllOrderByStatus(order_status,
        object : OnResultListener<List<Order>>{
            override fun onSuccess(list: List<Order>) {
                view.onGetListOrderSuccess(list)
            }

            override fun onFail(message: String) {
                view.onGetListOrderFail()
            }

        })
    }

    override fun updateStatusOrder(id: Long, order_status: Long) {
        val orderStatusDTO = OrderStatusDTO(order_status)
        callApi.updateOrderStatus(
            id,
            orderStatusDTO,
            object :OnResultListener<Order>{
                override fun onSuccess(list: Order) {
                    view.onUpadteStatusOrderSuccess()
                }

                override fun onFail(message: String) {
                    view.onFail(MESS)
                }
            }
        )
    }
    companion object{
        const val MESS = "xác nhận đơn hàng không thành công"
    }
}