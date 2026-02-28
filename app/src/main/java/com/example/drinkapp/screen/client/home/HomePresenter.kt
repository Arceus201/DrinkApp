package com.example.drinkapp.screen.client.home

import com.example.drinkapp.data.model.Product
import com.example.drinkapp.data.resource.OnResultListener
import com.example.drinkapp.data.resource.call.CallApiDrink

class HomePresenter(private var view: HomeContract.View?,
                    private val callApi: CallApiDrink): HomeContract.Presenter {
    
    override fun attachView(view: HomeContract.View) {
        this.view = view
    }
    
    override fun detachView() {
        view = null
    }
    
    override fun getProductHot() {
        callApi.getDrinkHot(
            object : OnResultListener<List<Product>>{
                override fun onSuccess(list: List<Product>) {
                    view?.onGetProductHotSuccess(list)
                }

                override fun onFail(message: String) {
                    view?.onFail()
                }

            }
        )
    }

    override fun onStart() {
    }

    override fun onStop() {
        detachView()
    }
}