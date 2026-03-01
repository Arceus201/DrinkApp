package com.example.drinkapp.screen.client.search

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.IBasePresenter

interface SearchContract {
    interface View{
        fun onGetAllProductSuccess(list: List<Product>)
        fun onFail()
    }
    interface Presenter: IBasePresenter<View>{
        fun getAllProduct()
    }
}