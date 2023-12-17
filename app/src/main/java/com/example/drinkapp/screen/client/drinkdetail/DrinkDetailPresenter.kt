package com.example.drinkapp.screen.client.drinkdetail

import com.example.drinkapp.R
import com.example.drinkapp.data.model.CartItem
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCartItem
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize
import com.example.drinkapp.utils.formatAsNumber

class DrinkDetailPresenter(
    private val view: DrinkDetailContract.View,
    private val callApiDrink: CallApiDrink,
    private val callApiPriceSize: CallApiPriceSize,
    private val callApiCartItem: CallApiCartItem
) : DrinkDetailContract.Presenter {
    override fun getProductById(id: Long) {
        callApiDrink.getProductById(
            id,
            object : OnResultListener<Product>{
                override fun onSuccess(product: Product) {
                    view.onGetProductSuccess(product)
                }

                override fun onFail(message: String) {

                }

            }
        )
    }

    override fun changeQuantity(index: Long, action: Long) {
        if(index == 1L && action == -1L) return view.onFail(MESS_UPĐATE_CART_ITEM_NUMBER_ERRROR)
        else return view.onChangeQuantitySuccess(index+action)
    }

    override fun getListPriceSize(idProduct: Long) {
        callApiPriceSize.getAllPriceSizeClientByProductID(
            idProduct,
            object : OnResultListener<List<PriceSize>>{
                override fun onSuccess(list: List<PriceSize>) {
                    val data: List<Pair<Long, String>> = list
                        .filter { it.id != null && it.size.name != null }
                        .map { it.id!! to  it.size.name + " Giá " + it.price.formatAsNumber() + " đ"}
                    view.onGetListPriceSizeSuccess(data)
                }

                override fun onFail(message: String) {

                }

            }
        )
    }

    override fun checkCartItem(user_id: Long, priseSizeId: Long) {
        callApiCartItem.checkCartItem(
            user_id, priseSizeId,
            object : OnResultListener<CartItem>{
                override fun onSuccess(list: CartItem) {
                    view.onCheckCartItemSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onCheckFail()
                }

            }
        )
    }

    override fun addCartItem(pricesize_id: Long, user_id: Long, number: Long, note: String) {
        callApiCartItem.addCartItem(
            pricesize_id,user_id,number,note,
            object : OnResultListener<CartItem>{
                override fun onSuccess(list: CartItem) {
                    view.onAddToCartSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ADD_TO_CART_FAIL)
                }
            }
        )
    }

    override fun updateCartItemNumber(id: Long, number: Long) {
        callApiCartItem.updateCartItemNumber(
            id,number,
            object: OnResultListener<CartItem>{
                override fun onSuccess(list: CartItem) {
                    view.onAddToCartSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ADD_TO_CART_FAIL)
                }
            }
        )
    }

    override fun updateCartItemUpdate(id: Long, number: Long, note: String) {
        callApiCartItem.updateCartItem(
            id,number,note,
            object: OnResultListener<CartItem>{
                override fun onSuccess(list: CartItem) {
                    view.onAddToCartSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ADD_TO_CART_FAIL)
                }
            }
        )
    }

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        // TODO("Not yet implemented")
    }

    companion object{
        const val MESS_UPĐATE_CART_ITEM_NUMBER_ERRROR = "số lượng sản phẩm không hợp lệ"
        const val MESS_ADD_TO_CART_FAIL = "thêm sản phẩm vào giỏ hàng lỗi"
    }

}