package com.example.drinkapp.screen.client.history

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.screen.client.order.OrderContract

class HistoryPresenter(
    private val view: HistoryContract.View,
    private val callApi: CallApiOrder
) :
    HistoryContract.Presenter {
    override fun getHistoryOrder(user_id: Long) {
        callApi.getHistoryOrderByUserId(
            user_id,
            object : OnResultListener<List<Order>> {
                override fun onSuccess(list: List<Order>) {
                    view.onGetAllHistoryOrderSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAllHistoryOrderFail()
                }

            }
        )
    }
}