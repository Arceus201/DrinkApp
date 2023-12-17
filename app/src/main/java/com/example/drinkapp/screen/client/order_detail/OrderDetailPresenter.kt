package com.example.drinkapp.screen.client.order_detail

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiAddress
import com.example.drinkapp.data.resource.call.CallApiOrder

class OrderDetailPresenter(
    private val view: OrderDetailContract.View,
    private val callApi: CallApiOrder,
    private val callApiAddress: CallApiAddress
) :
    OrderDetailContract.Presenter {
    override fun getOrder(id: Long) {
        callApi.getOrder(
            id,
            object : OnResultListener<Order>{
                override fun onSuccess(list: Order) {
                    view.onGetOrderSuccess(list)
                }

                override fun onFail(message: String) {

                }

            }
        )

    }

    override fun getAddressStore() {
        callApiAddress.getAddressStore(
            object : OnResultListener<Address>{
                override fun onSuccess(list: Address) {
                   view.onGetAddressStoreSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetAddressStoreFail()
                }

            }
        )
    }
}