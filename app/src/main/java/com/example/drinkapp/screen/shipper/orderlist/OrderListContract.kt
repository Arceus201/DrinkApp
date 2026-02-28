package com.example.drinkapp.screen.shipper.orderlist

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.BasePresenter

interface OrderListContract {
    interface View{
        fun onGetListOrderSuccess(list: List<Order>)
        fun onGetOrderByShipperIdSuccess(order:Order)
        fun updateShippingOrderSucess()
        fun onFail(msg: String)
        fun onGetListOrderFail()
        fun onGetOrderByShipperIdFail()
    }
    interface Presenter : BasePresenter<View> {
        fun getListOrder(status: Long)
        fun getOrderByShipperId(shipper_id: Long)
        fun updateShippingOrder(order_id:Long,shipper_id: Long,status: Long)
    }

}