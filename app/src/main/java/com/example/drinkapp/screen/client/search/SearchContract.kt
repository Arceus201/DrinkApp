package com.example.drinkapp.screen.client.search

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.BasePresenter

interface SearchContract {
    interface View{
        fun onGetAllProductSuccess(list: List<Product>)
        fun onFail()
    }
    interface Presenter: BasePresenter<View>{
        fun getAllProduct()
    }
}