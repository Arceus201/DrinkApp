package com.example.drinkapp.screen.admin.product_add

import android.net.Uri
import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.BasePresenter

interface ProductAddContract {
    interface View {
        fun displayAllCategory(result: List<Pair<Long, String>>)

        fun onCheckInputAddSuccess()
        fun onProductAdded( product: Product)
        fun onFail(errorMessage: String)

        fun addPriceSize(msg: String)
    }

    interface Presenter:BasePresenter<View> {

        fun getAllCategory()

        fun addPriceSize(idProduct: Long, idSize: Long, price: Double)

        fun addProduct(name: String,imageUri: String, price: Double, statusCode: Long,cateId: Long)

        fun checkInputAdd(name: String, imageUri: Uri?, price: String)
    }

}