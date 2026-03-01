package com.example.drinkapp.screen.admin.waiting_for_delivery

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface WattingDeliveryContract {
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