package com.example.drinkapp.screen.client.home

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiDrink

class HomePresenter(private val view: HomeContract.View,
                    private val callApi: CallApiDrink): HomeContract.Presenter {
    override fun getProductHot() {
        callApi.getDrinkHot(
            object : OnResultListener<List<Product>>{
                override fun onSuccess(list: List<Product>) {
                    view.onGetProductHotSuccess(list)
                }

                override fun onFail(message: String) {
                    view.onFail()
                }

            }
        )
    }

    override fun onStart() {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        //TODO("Not yet implemented")
    }
}