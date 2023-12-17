package com.example.drinkapp.screen.admin.product_add

import android.net.Uri
import com.example.drinkapp.data.model.Category
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiCategory
import com.example.drinkapp.data.resource.call.CallApiDrink
import com.example.drinkapp.data.resource.call.CallApiPriceSize


class ProductAddPresenter(
    private val view: ProductAddContract.View,
    private val callApiDrink: CallApiDrink,
    private val callApiCate: CallApiCategory,
    private val callApiPriceSize: CallApiPriceSize,
) :
    ProductAddContract.Presenter {

    override fun getAllCategory() {
        callApiCate.getAllCategory(
            object : OnResultListener<List<Category>> {
                override fun onSuccess(list: List<Category>) {
                    if (list != null && list.isNotEmpty()) {
                        val data: List<Pair<Long, String>> = list
                            .filter { it.id != null && it.name != null }
                            .map { it.id!! to it.name!! }
                        view.displayAllCategory(data)
                    }
                }

                override fun onFail(message: String) {

                }
            }
        )
    }


    override fun addProduct(
        name: String,
        imageUri: String,
        price: Double,
        statusCode: Long,
        cateId: Long
    ) {
        callApiDrink.addProduct(name, imageUri, price, statusCode, cateId,
            object : OnResultListener<Product> {
                override fun onSuccess(product: Product) {
                    view.onProductAdded( product)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ADD_PRODUCT_FAIL)
                }
            }
        )
    }

    override fun checkInputAdd(
        name: String,
        imageUri: Uri?,
        price: String
    ) {
        if(name.isNullOrEmpty() or (imageUri == null) or price.isNullOrEmpty()){
            view.onFail(MESS_CEHCK_INPUT_ADD_FAIL)
        }else{
            view.onCheckInputAddSuccess()
        }
    }

    override fun addPriceSize(idProduct: Long, idSize: Long, price: Double) {
        callApiPriceSize.addPriceSize(idProduct, idSize, price,
            object : OnResultListener<List<PriceSize>> {
                override fun onSuccess(list: List<PriceSize>) {
                    view.addPriceSize(MESS_ADD_PRODUCT_FAIL)
                }

                override fun onFail(message: String) {
                    view.onFail(MESS_ADD_PRODUCT_SUCSESS)
                }

            })
    }


    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        // TODO("Not yet implemented")
    }
    companion object{
        const val MESS_ADD_PRODUCT_SUCSESS = "thêm sản phẩm thành công"
        const val MESS_ADD_PRODUCT_FAIL = "thêm sản phẩm không thành công"
        const val MESS_CEHCK_INPUT_ADD_FAIL = "Bạn cần nhập đầy đủ thông tin trước khi nhấn add"
    }

}