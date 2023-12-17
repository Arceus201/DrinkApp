package com.example.drinkapp.screen.client.confirm_order

import com.example.drinkapp.data.model.Address
import com.example.drinkapp.data.model.CartItem
import java.util.Date

interface ConfirmOrderContract {
    interface View{
        fun onAddOrderSuccess(id: Long)
        fun onGetExChangeRateSuccess(VND: Double)
        fun onFail(msg: String)

    }
    interface Presenter{

        fun addOrder( list: List<CartItem>, address_id: Long, order_time: String, total_price: Double,
                      payment_type: Long, payment_status: Long)
        fun getExchangeRate()
    }
}