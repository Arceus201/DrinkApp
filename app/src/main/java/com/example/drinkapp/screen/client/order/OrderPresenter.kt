package com.example.drinkapp.screen.client.order

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder

class OrderPresenter(
    private val view: OrderContract.View,
    private val callApi: CallApiOrder
) :
    OrderContract.Presenter {
    override fun getOrder(user_id: Long) {
        callApi.getOrderByUserId(
            user_id,
            object : OnResultListener<List<Order>>{
                override fun onSuccess(list: List<Order>) {
                    view.onGetAllOrderSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAllOrderFail()
                }

            }
        )
    }
}