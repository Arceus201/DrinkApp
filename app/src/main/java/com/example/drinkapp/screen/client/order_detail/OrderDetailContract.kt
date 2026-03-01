package com.example.drinkapp.screen.client.order_detail

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.utils.base.IBasePresenter

interface OrderDetailContract {
    interface View {
        fun onGetOrderSuccess(order: Order)

        fun onGetAddressStoreSuccess(address: Address)

        fun onGetAddressStoreFail()
        fun onFail(msg: String)
    }

    interface Presenter : IBasePresenter<View> {
        fun getOrder(id: Long)
        fun getAddressStore()
    }
}