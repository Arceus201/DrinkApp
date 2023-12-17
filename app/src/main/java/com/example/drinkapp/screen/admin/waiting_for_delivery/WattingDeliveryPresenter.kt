package com.example.drinkapp.screen.admin.waiting_for_delivery

import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.data.resource.dto.order.OrderStatusDTO


class WattingDeliveryPresenter (
    private val view: WattingDeliveryContract.View,
    private val callApi: CallApiOrder
) : WattingDeliveryContract.Presenter {
    override fun getListOrder(order_status: Long) {
        callApi.getAllOrderByStatus(order_status,
            object : OnResultListener<List<Order>> {
                override fun onSuccess(list: List<Order>) {
                    view.onGetListOrderSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onGetListOrderFail()
                }

            })
    }

    override fun updateStatusOrder(id: Long, order_status: Long) {
        val orderStatusDTO = OrderStatusDTO(order_status)
        callApi.updateOrderStatus(
            id,
            orderStatusDTO,
            object : OnResultListener<Order> {
                override fun onSuccess(list: Order) {
                    view.onUpadteStatusOrderSuccess()
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_CONFIRM_FAIL)
                }

            }
        )
    }
    companion object{
        const val MESSAGE_CONFIRM_FAIL = "xác nhận không thành công"
    }

}