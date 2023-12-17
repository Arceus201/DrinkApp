package com.example.drinkapp.screen.client.drinkdetail

import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.BasePresenter

interface DrinkDetailContract {
    interface View{
        fun onGetProductSuccess(product: Product)
        fun onChangeQuantitySuccess(index: Long)
        fun onGetListPriceSizeSuccess(list: List<Pair<Long, String>>)
        fun onCheckCartItemSuccess(cartItem: CartItem)

        fun onAddToCartSuccess(cartItem: CartItem)
        fun onFail(msg: String)
        fun onCheckFail()
    }
    interface Presenter: BasePresenter<View>{
        fun getProductById(id: Long)
        fun changeQuantity(index : Long, action: Long)
        fun getListPriceSize(idProduct: Long)

        fun checkCartItem(user_id: Long,priseSizeId: Long )

        fun addCartItem(pricesize_id: Long, user_id: Long, number: Long, note: String)
        fun updateCartItemNumber(id: Long, number: Long)
        fun updateCartItemUpdate(id: Long, number: Long,note: String)
    }
}