package com.example.drinkapp.screen.client.cart

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCartItem

class CartPresenter(
    private val view: CartContract.View,
    private val callApi: CallApiCartItem
) :
    CartContract.Presenter {
    override fun getAllCartItemByUserID(user_id: Long) {
        callApi.getAllCartItemByUserID(
            user_id,
            object : OnResultListener<List<CartItem>>{
                override fun onSuccess(list: List<CartItem>) {
                    view.onDisplayAllCartSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onDisplayAllCartFail()
                }

            }
        )
    }

    override fun updateCartItemNumber(id: Long, number: Long) {
        callApi.updateCartItemNumber(
            id,number,
            object : OnResultListener<CartItem>{
                override fun onSuccess(list: CartItem) {
                    view.onUpdateCartNumberSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_UPĐATE_CART_ITEM_NUMBER_ERRROR)
                }

            }
        )
    }

    override fun deleteCartItemById(id: Long) {
        callApi.deleteCartItemById(
            id,
            object: OnResultListener<String>{
                override fun onSuccess(list: String) {
                    view.onDeleteCartItemByIdSuccess(id)
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_DELETE_FAIL)
                }

            }
        )
    }

    override fun deleteAll(user_id: Long) {
        callApi.deleteAll(
            user_id,
            object :OnResultListener<String>{
                override fun onSuccess(list: String) {
                    view.onDeleteAllSuccess()
                }

                override fun onFail(message: String) {
                    view.onFail(MESSAGE_DELETE_ALL_FAIL)
                }

            }
        )
    }
    companion object{
        const val MESSAGE_DELETE_ALL_FAIL = "xóa tất cả sản phẩm lỗi"
        const val MESSAGE_DELETE_FAIL = "xóa ản phẩm lỗi"
        const val MESS_UPĐATE_CART_ITEM_NUMBER_ERRROR = "cập nhật số lượng sản phẩm lỗi"
    }

}