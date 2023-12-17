package com.example.drinkapp.screen.client.order

import com.example.drinkapp.data.model.Order

interface OrderContract {
    interface View{
        fun onGetAllOrderSuccess(list: List<Order>)
        fun onGetAllOrderFail()
        fun onFail(msg: String)
    }
    interface Presenter{
        fun getOrder(user_id: Long)
    }
}