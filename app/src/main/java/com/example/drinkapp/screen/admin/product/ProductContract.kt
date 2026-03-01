package com.example.drinkapp.screen.admin.product

import com.example.drinkapp.data.model.PriceSize
import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.IBasePresenter

interface ProductContract {
    interface View{
        fun showAllDrinkSuccess(list: List<Product>)
        fun  onUpdateSuccess(msg: String)
        fun onDeleteProductSuccess(msg: String)
        fun showAllDrinkFail()
        fun onFail(msg: String)
    }
    interface Presenter: IBasePresenter<View>{
        fun getAllDrink()
        fun updateProduct(id: Long,name: String,imageUri: String, price: Double, statusCode: Long,cateId: Long)
        fun deleteProduct(id: Long)
    }
}