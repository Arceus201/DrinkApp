package com.example.drinkapp.screen.client.cart

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.utils.base.BasePresenter

interface CartContract {
    interface View{
        fun onDisplayAllCartSuccess(list: List<CartItem>)
        fun onUpdateCartNumberSuccess(cartItem: CartItem)

        fun onDeleteCartItemByIdSuccess(id: Long)
        fun onDeleteAllSuccess()
        fun onFail(msg: String)
        fun onDisplayAllCartFail()
    }
    interface Presenter : BasePresenter<View> {
        fun getAllCartItemByUserID(user_id: Long)
        fun updateCartItemNumber(id: Long, number: Long)

        fun deleteCartItemById(id: Long)

        fun deleteAll(user_id: Long)

    }
}