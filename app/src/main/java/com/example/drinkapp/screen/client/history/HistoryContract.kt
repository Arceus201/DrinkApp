package com.example.drinkapp.screen.client.history

import com.example.drinkapp.data.model.Order

interface HistoryContract {
    interface View{
        fun onGetAllHistoryOrderSuccess(list: List<Order>)
        fun onGetAllHistoryOrderFail()
        fun onFail(msg: String)
    }
    interface Presenter{
        fun getHistoryOrder(user_id: Long)
    }
}