package com.example.drinkapp.screen.client.history

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.BasePresenter

interface HistoryContract {
    interface View{
        fun onGetAllHistoryOrderSuccess(list: List<Order>)
        fun onGetAllHistoryOrderFail()
        fun onFail(msg: String)
    }
    interface Presenter : BasePresenter<View> {
        fun getHistoryOrder(user_id: Long)
    }
}