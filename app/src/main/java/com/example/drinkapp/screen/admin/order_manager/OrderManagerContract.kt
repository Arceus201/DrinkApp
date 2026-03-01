package com.example.drinkapp.screen.admin.order_manager

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface OrderManagerContract {
    interface View{
        fun onGetListOrderSuccess(list: List<Order>)
        fun onUpadteStatusOrderSuccess()
        fun onFail(msg: String)
        fun onGetListOrderFail()
    }
    interface Presenter : IBasePresenter<View> {
        fun getListOrder(order_status: Long)
        fun updateStatusOrder(id: Long,order_status: Long)
    }
}