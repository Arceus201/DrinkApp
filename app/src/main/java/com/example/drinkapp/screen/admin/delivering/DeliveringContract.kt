package com.example.drinkapp.screen.admin.delivering

import com.example.drinkapp.data.model.Order

interface DeliveringContract {
    interface View{
        fun onGetListOrderSuccess(list: List<Order>)
        fun onFail(msg: String)
        fun onGetListOrderFail()
    }
    interface Presenter{
        fun getListOrder(order_status: Long)
    }
}