package com.example.drinkapp.screen.admin.delivering

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO

class DeliveringPresenter(
    private val view: DeliveringContract.View,
    private val callApi: CallApiOrder
) : DeliveringContract.Presenter {
    override fun getListOrder(order_status: Long) {
        callApi.getAllOrderByStatus(order_status,
            object : OnResultListener<List<Order>> {
                override fun onSuccess(list: List<Order>) {
                    view.onGetListOrderSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetListOrderFail()
                }

            })
    }
}