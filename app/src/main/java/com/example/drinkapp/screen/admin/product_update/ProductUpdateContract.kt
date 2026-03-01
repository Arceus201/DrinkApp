package com.example.drinkapp.screen.admin.product_update

import com.example.drinkapp.utils.base.IBasePresenter

interface ProductUpdateContract {
    interface View{
        fun displayAllCategory(result: List<Pair<Long, String>>)
        fun onCheckInputUpdateSuccess(uri: String)
        fun onUpdateSuccess()

        fun  onUpdatePriiceSizeSuccess(msg: String)
        fun onFail(msg: String)
    }
    interface Presenter: IBasePresenter<View>{
        fun getAllCategory()
        fun updateProduct(id: Long,name: String,imageUri: String, price: Double, statusCode: Long,cateId: Long)

        fun updatePriceSizeDefault( idProduct: Long, idSize: Long, price: Double,status: Long)
        fun checkInputUpdate(name: String,imageUri: String, price: String)
    }
}