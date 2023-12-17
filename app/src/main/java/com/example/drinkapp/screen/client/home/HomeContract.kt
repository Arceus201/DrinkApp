package com.example.drinkapp.screen.client.home

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.utils.base.BasePresenter

interface HomeContract {
    interface View{
        fun onGetProductHotSuccess(list: List<Product>)
        fun onFail()
    }
    interface Presenter: BasePresenter<View>{
        fun getProductHot()
    }
}