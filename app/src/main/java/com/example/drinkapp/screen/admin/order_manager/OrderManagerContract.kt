package com.example.drinkapp.screen.admin.order_manager

import com.example.drinkapp.data.model.Order

interface OrderManagerContract {
    interface View{
        fun onGetListOrderSuccess(list: List<Order>)
        fun onUpadteStatusOrderSuccess()
        fun onFail(msg: String)
        fun onGetListOrderFail()
    }
    interface Presenter{
        fun getListOrder(order_status: Long)
        fun updateStatusOrder(id: Long,order_status: Long)
    }
}