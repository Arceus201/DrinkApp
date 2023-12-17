package com.example.drinkapp.screen.client.confirm_order

import android.provider.ContactsContract
import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.model.Order
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiAddress
import com.example.drinkapp.data.resource.call.CallApiOrder
import com.example.drinkapp.data.resource.call.ExchangeRateAPI
import com.example.drinkapp.data.resource.dto.order.OrderDTO
import com.example.drinkapp.data.resource.dto.payment.PaymentDTO
import java.util.Date

class ConfirmOrderPresenter(
    private val view: ConfirmOrderContract.View,
    private val callApiExchangeRate: ExchangeRateAPI,
    private val callApiOrder: CallApiOrder
) :
    ConfirmOrderContract.Presenter {


    override fun addOrder(
        list: List<CartItem>, address_id: Long, order_time: String, total_price: Double,
        payment_type: Long, payment_status: Long
    ) {
        val paymentDTO = PaymentDTO(payment_type, payment_status)
        val orderDTO = OrderDTO(list, address_id, paymentDTO, order_time, total_price)
        callApiOrder.addOrder(
            orderDTO,
            object : OnResultListener<Order> {
                override fun onSuccess(list: Order) {
                    view.onAddOrderSuccess(list.id)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ORDER_FAIL)
                }

            }
        )
    }

    override fun getExchangeRate() {
        callApiExchangeRate.getExchangeRates(
            object : OnResultListener<Double> {
                override fun onSuccess(list: Double) {
                    view.onGetExChangeRateSuccess(list)
                }

                override fun onFail(message: String) {
                }
            }
        )
    }
    companion object{
        const val MESS_ORDER_FAIL = "đặt hàng không thành công"
    }

}