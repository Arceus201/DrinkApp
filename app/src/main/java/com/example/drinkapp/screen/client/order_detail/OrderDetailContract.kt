package com.example.drinkapp.screen.client.order_detail

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.Order

interface OrderDetailContract {
    interface View {
        fun onGetOrderSuccess(order: Order)

        fun onGetAddressStoreSuccess(address: Address)

        fun onGetAddressStoreFail()
        fun onFail(msg: String)
    }

    interface Presenter {
        fun getOrder(id: Long)
        fun getAddressStore()
    }
}